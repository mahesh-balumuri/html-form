package com.phform.server.web.action.form;

import java.util.HashMap;
import java.util.Map;

import com.phform.server.entity.Form;
import com.phform.server.framework.util.FLog;

public class FormMemory
{
    private Map formMap;
    
    private static FormMemory instance;
    
    public static FormMemory getInstance()
    {
        if (null == instance)
        {
            synchronized (FormMemory.class)
            {
                if (null == instance)
                {
                    instance = new FormMemory();
                }
            }
        }
        return instance;
    }
    
    private FormMemory()
    {
        formMap = new HashMap();
    }
    
    public synchronized void addForm(Form f)
    {
        if (FLog.isDebug())
        {
            FLog.debug("Enter function addForm():" + f);
        }
        
        if (formMap.containsKey(f.getFormId()))
        {
            if (FLog.isError())
            {
                FLog.error("FormId duplicated ERROR.formId=" + f.getFormId());
            }
            return;
        }
        
        FormInMem inMem = new FormInMem(f);
        if (!"".equals(inMem.getFormHtml()))
        {
            formMap.put(f.getFormId(), inMem);
            if (FLog.isDebug())
            {
                FLog.debug("Put FormInMem successful.");
            }
        }
        else
        {
            if (FLog.isError())
            {
                FLog.error("Put FormInMem failed.Maybe file not exist.");
            }
        }
        
        if (FLog.isDebug())
        {
            FLog.debug("Exit function addForm()");
        }
    }
    
    public synchronized void addForm(FormInMem f)
    {
        if (FLog.isDebug())
        {
            FLog.debug("Enter function addFormInMem()");
        }
        
        if (formMap.containsKey(f.getFormId()))
        {
            if (FLog.isError())
            {
                FLog.error("FormId duplicated ERROR.formId=" + f.getFormId());
            }
            return;
        }
        
        formMap.put(f.getFormId(), f);
        
        if (FLog.isDebug())
        {
            FLog.debug("Put FormInMem successful.");
        }
        
        if (FLog.isDebug())
        {
            FLog.debug("Exit function addForm()");
        }
    }
    
    public synchronized void removeForm(Form f)
    {
        if (FLog.isDebug())
        {
            FLog.debug("Enter function removeForm():" + f);
        }
        
        if (!formMap.containsKey(f.getFormId()))
        {
            if (FLog.isInfo())
            {
                FLog.info("Do not contain form 2 remove.formId="
                        + f.getFormId());
            }
            return;
        }
        
        formMap.remove(f.getFormId());
        if (FLog.isDebug())
        {
            FLog.debug("Remove FormInMem successful.");
        }
        
        if (FLog.isDebug())
        {
            FLog.debug("Exit function removeForm()");
        }
    }
    
    public synchronized void modifyForm(Form f)
    {
        if (FLog.isDebug())
        {
            FLog.debug("Enter function modifyForm():" + f);
        }
        
        if (!formMap.containsKey(f.getFormId()))
        {
            if (FLog.isInfo())
            {
                FLog.info("Do not contain form 2 modify.formId="
                        + f.getFormId());
            }
        }
        else
        {
            formMap.remove(f.getFormId());
        }
        
        FormInMem inMem = new FormInMem(f);
        if (!"".equals(inMem.getFormHtml()))
        {
            formMap.put(f.getFormId(), inMem);
            if (FLog.isDebug())
            {
                FLog.debug("Modify FormInMem successful.");
            }
        }
        else
        {
            if (FLog.isError())
            {
                FLog.error("Modify FormInMem successful.");
            }
        }
        
        if (FLog.isDebug())
        {
            FLog.debug("Exit function modifyForm()");
        }
    }
    
    public synchronized FormInMem queryForm(Form f)
    {
        if (FLog.isDebug())
        {
            FLog.debug("Enter function queryForm():" + f);
        }
        
        if (!formMap.containsKey(f.getFormId()))
        {
            return null;
        }
        
        if (FLog.isDebug())
        {
            FLog.debug("Exit function queryForm()");
        }
        return (FormInMem) formMap.get(f.getFormId());
    }
}
