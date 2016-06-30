package com.dantonov.test_task.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;


/**
 *
 * @author Antonov Denis (den007230@gmail.com)
 */
@WebFilter(filterName = "BrokenApplicationFilter", urlPatterns = {"/*"})
public class BrokenApplicationFilter implements Filter {

    private static volatile boolean activated;

    public static void activate() {
        activated = true;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (activated) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Application broken");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void destroy() {
    }
}
