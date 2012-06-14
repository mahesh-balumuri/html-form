package com.phform.server.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SecurityAuthorizationFilter implements Filter
{
    public void init(FilterConfig filterConfig) throws ServletException
    {
        
    }
    
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException
    {
        
        try
        {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response =
                (HttpServletResponse) servletResponse;
//            boolean isLoginAction =
//                request.getRequestURI().indexOf("/login/") != -1;
//            boolean isUserFun =
//                request.getRequestURI().indexOf("/admin/") != -1;
//            boolean flag = true;
//            Long loginUserId =
//                (Long) request.getSession().getAttribute(LOGIN_USER_ID);
//            if (loginUserId != null)
//            {
//                GlobalParametersUtil.setLoginUserId(loginUserId);
//            }
            // management platform
//            if (!isLoginAction && isUserFun)
//            {
//                if (loginUserId == null)
//                {
//                    response.sendRedirect(request.getContextPath()
//                            + "/login.jsp");
//                }
//                else
//                {
//                }
//                if (!flag)
//                {
//                    response
//                            .sendRedirect(request.getContextPath() + "/403.jsp");
//                }
//            }
            filterChain.doFilter(request, response);
        }
        finally
        {
        }
    }
    
    public void destroy()
    {
        
    }
}
