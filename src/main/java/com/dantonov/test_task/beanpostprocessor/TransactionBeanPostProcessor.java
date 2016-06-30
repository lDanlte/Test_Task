package com.dantonov.test_task.beanpostprocessor;

import com.dantonov.test_task.annotation.Transaction;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * Бин пост процессор отвечающий за синхронизацию общих ресурсов
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */
public class TransactionBeanPostProcessor implements BeanPostProcessor {
    
    private static final int MAX_AVAILABLE = 1000;
    private static final int READ_ONLY_PERMITS = 1;
    private static final int WRITE_PERMITS = 1000;
    
    //id бинов, которые подвергнутся проксированию
    private static final Set<String> beanNames = new HashSet<>();
    
    //отдельный семафон для каждого типа ресурсов
    private static final Map<String, Semaphore> synch = new HashMap<>();
    
    //Для каждого потока показывает, был ли вызван acquire, исключает вероятность дедлока
    private static final Map<Long, Boolean> rememberLock = new HashMap<>();

    private static final Advisor advisor;
    
    
    static {
        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(Transaction.class);
        
        advisor = new DefaultPointcutAdvisor(pointcut, 
                (MethodInterceptor) (MethodInvocation mi) -> {
                    
                    long threadId = Thread.currentThread().getId();
                    
                    Boolean isLocked = rememberLock.get(threadId);
                    
                    if (isLocked != null && isLocked) {
                        return mi.proceed();
                    }
                    
                    Object result;
                    
                    Transaction tr = mi.getMethod().getDeclaredAnnotation(Transaction.class);
                    Semaphore semaphore = synch.get(tr.resource());
                    rememberLock.put(threadId, Boolean.TRUE);
                    
                    semaphore.acquire(tr.readOnly() ? READ_ONLY_PERMITS : WRITE_PERMITS);
                    
                    result = mi.proceed();
                    
                    semaphore.release(tr.readOnly() ? READ_ONLY_PERMITS : WRITE_PERMITS);
                    
                    rememberLock.put(threadId, Boolean.FALSE);
                    
                    return result;
        });
    }
    
    
    @Override
    public Object postProcessBeforeInitialization(Object o, String string) throws BeansException {
                
        for (Method method : o.getClass().getMethods()) {

                Transaction tr =  method.getAnnotation(Transaction.class);
                if (tr == null) {
                    continue;
                }
                beanNames.add(string);
                String resource = tr.resource();
                if (!synch.containsKey(resource)) {
                    synch.put(resource, new Semaphore(MAX_AVAILABLE, true));
                }
            
        }
        
        
        return o;
    }

    
    
    @Override
    public Object postProcessAfterInitialization(Object o, String string) throws BeansException {
        
        if (beanNames.contains(string)) {

            ProxyFactory proxyFactory = new ProxyFactory();

            proxyFactory.setOptimize(false);
            proxyFactory.addAdvisor(advisor);
            proxyFactory.setTarget(o);

            return proxyFactory.getProxy();
        }
        return o;
    }

}
