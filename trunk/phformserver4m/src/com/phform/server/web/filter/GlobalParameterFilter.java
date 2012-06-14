package com.phform.server.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public class GlobalParameterFilter implements Filter
{
    public void init(FilterConfig filterConfig) throws ServletException
    {
        
    }
    
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        try
        {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            
            filterChain.doFilter(servletRequest, servletResponse);
        }
        finally
        {
        }
    }
    
    public void destroy()
    {
        
    }
}
