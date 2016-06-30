package com.dantonov.test_task.config;

import com.dantonov.test_task.content.DataContent;
import com.dantonov.test_task.filter.BrokenApplicationFilter;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public class ContextListener extends ContextLoaderListener {
    
    
    
    public ContextListener() {  }
    
    public ContextListener(WebApplicationContext webAppContext) {
        super(webAppContext);
    }

    
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        try {
            super.contextInitialized(event);
            
            ApplicationContext context = this.loadParentContext(event.getServletContext());

            WebApplicationContext webContext = getCurrentWebApplicationContext();
             
            DataContent.fillCategories(webContext.getBean("categoryStorage", Map.class));
            
            DataContent.fillDrivers(webContext.getBean("driverStorage", Map.class), 
                                    webContext.getBean("categoryStorage", Map.class));
            
            DataContent.fillPolicy(webContext.getBean("policyStorage", Map.class), 
                                   webContext.getBean("driverStorage", Map.class));
    
        } catch (Throwable ex) {
            handleInitializationError(ex, event.getServletContext());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        
        super.contextDestroyed(event);
    }
    
    
    
    private void handleInitializationError(Throwable t, ServletContext servletContext) {


        try {
            closeWebApplicationContext(servletContext);
        } finally {
            BrokenApplicationFilter.activate();
        }
    }
    
    

}
