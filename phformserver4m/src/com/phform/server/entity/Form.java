package com.phform.server.entity;

public class Form implements java.io.Serializable
{
    private String id;
    
    private String formId;
    
    private String formName;
    
    private String formPath;
    
    public String getFormId()
    {
        return formId == null ? "" : formId.trim();
    }
    
    public void setFormId(String formId)
    {
        this.formId = formId;
    }
    
    public String getFormName()
    {
        return formName == null ? "" : formName.trim();
    }
    
    public void setFormName(String formName)
    {
        this.formName = formName;
    }
    
    public String getFormPath()
    {
        return formPath == null ? "" : formPath.trim();
    }
    
    public void setFormPath(String formPath)
    {
        this.formPath = formPath;
    }
    
    public String getId()
    {
        return id == null ? "" : id.trim();
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String toString()
    {
        return "Form [formId=" + getFormId() + "][formName=" + getFormName()
                + "][formPath=" + getFormPath() + "]";
    }
}
