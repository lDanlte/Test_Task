package com.dantonov.test_task.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Аннотоация, которой помечается метод для синхронизации доступа к ресурсам
 * 
 * @author Antonov Denis (den007230@gmail.com)
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {

    //Название ресурса
    String resource();
    
    //Указывает, какие операции будет производить метод над ресурсами
    boolean readOnly() default true;
    
}
