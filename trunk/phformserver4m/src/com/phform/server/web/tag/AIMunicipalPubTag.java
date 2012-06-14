package com.phform.server.web.tag;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AIMunicipalPubTag extends TagSupport
{
    protected String name;
    
    protected String act;
    
    public String getAct()
    {
        return act == null ? "" : act.trim();
    }
    
    public void setAct(String act)
    {
        this.act = act;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    public int doStartTag() throws JspException
    {
        String show = "";
        if ("L".equalsIgnoreCase(this.getAct()))
        {
            show = this.listAppCase();
        }
        else if ("S".equalsIgnoreCase(this.getAct()))
        {
            show = this.listAppStatistics();
        }
        else if ("R".equalsIgnoreCase(this.getAct()))
        {
            show = this.listAppCaseResult();
        }
        try
        {
            pageContext.getOut().print(show);
            return super.doStartTag();
        }
        catch (Exception e)
        {
            throw new JspException(e);
        }
    }
    
    private String listAppCase()
    {
        String listApp = "";
        
        return listApp;
    }
    
    private String listAppStatistics()
    {
        String statistics = "";
        return "";
    }
    
    private String listAppCaseResult()
    {
        String listApp = "";
        return listApp;
    }
}
