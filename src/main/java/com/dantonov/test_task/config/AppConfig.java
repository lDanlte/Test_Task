package com.dantonov.test_task.config;

import com.dantonov.test_task.beanpostprocessor.TransactionBeanPostProcessor;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */

@Configuration
@ComponentScan(basePackages = { "com.dantonov.test_task.service"})
public class AppConfig {

    
    @Bean
    public BeanPostProcessor transactionBeanPostProcessor() {
        return new TransactionBeanPostProcessor();
    }
}
