package com.phform.server.entity;

// default package

/**
 * FormRoleRelation generated by MyEclipse Persistence Tools
 */

public class FormRoleRelation implements java.io.Serializable
{
    
    // Fields
    
    private String id;
    
    private String formId;
    
    private String roleId;
    
    private String elementId;
    
    private String permission;
    
    // Property accessors
    
    public String getElementId()
    {
        return elementId == null ? "" : elementId.trim();
    }
    
    public void setElementId(String elementId)
    {
        this.elementId = elementId;
    }
    
    public String getPermission()
    {
        return permission == null ? "" : permission.trim();
    }
    
    public void setPermission(String permission)
    {
        this.permission = permission;
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getFormId()
    {
        return this.formId == null ? "" : formId.trim();
    }
    
    public void setFormId(String formId)
    {
        this.formId = formId;
    }
    
    public String getRoleId()
    {
        return this.roleId == null ? "" : roleId.trim();
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
}