package com.dantonov.test_task.config;

import java.util.EnumSet;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { DataConfig.class, AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
    }
    
    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        WebApplicationContext rootAppContext = this.createRootApplicationContext();
        if(rootAppContext != null) {
            servletContext.addListener(new ContextListener(rootAppContext));
        } else {
            this.logger.debug("No ContextLoaderListener registered, as createRootApplicationContext() did not return an application context");
        }
    }

}
