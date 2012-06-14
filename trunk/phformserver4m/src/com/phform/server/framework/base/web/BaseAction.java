package com.phform.server.framework.base.web;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class BaseAction extends ActionSupport
{
    
    public HttpServletRequest getRequest()
    {
        return ServletActionContext.getRequest();
    }
    
    public HttpServletResponse getResponse()
    {
        return ServletActionContext.getResponse();
    }
    
    public HttpSession getSession()
    {
        return this.getRequest().getSession();
    }
    
    public ServletContext getServletContext()
    {
        return ServletActionContext.getServletContext();
    }
    
    // Messages save to session for redirect
    public void addMessage(String msg)
    {
        List messages =
            (List) getRequest().getSession().getAttribute("messages");
        if (messages == null)
        {
            messages = new ArrayList();
        }
        messages.add(msg);
        getRequest().getSession().setAttribute("messages", messages);
    }
}
