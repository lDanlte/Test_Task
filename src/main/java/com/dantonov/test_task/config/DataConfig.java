package com.dantonov.test_task.config;

import com.dantonov.test_task.entity.Category;
import com.dantonov.test_task.entity.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */

@Configuration
@ComponentScan(basePackages = { "com.dantonov.test_task.dao" })
public class DataConfig {

    
    
    @Bean
    public Map<Integer, List<Driver>> policyStorage() {
        
        return new HashMap<>();
    }
    
    
    @Bean
    public Map<Integer, Driver> driverStorage() {
        
        return new HashMap<>();
    }
    
    @Bean
    public Map<Integer, Category> categoryStorage() {
        
        return new HashMap<>();
    }
}
