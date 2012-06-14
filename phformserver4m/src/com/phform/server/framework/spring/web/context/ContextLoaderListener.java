package com.phform.server.framework.spring.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.context.ApplicationContext;

import com.phform.server.framework.spring.context.ApplicationContextUtil;

public class ContextLoaderListener extends
        org.springframework.web.context.ContextLoaderListener
{
    public void contextInitialized(ServletContextEvent event)
    {
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        ApplicationContext ctx =
            WebApplicationContextUtils
                    .getRequiredWebApplicationContext(context);
        ApplicationContextUtil.setApplicationContext(ctx);
        
    }
    
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        if (this.getContextLoader() != null)
        {
            this.getContextLoader().closeWebApplicationContext(
                    servletContextEvent.getServletContext());
        }
    }
}
