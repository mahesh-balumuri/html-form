package com.phform.server.entity;

// default package

/**
 * FormRole generated by MyEclipse Persistence Tools
 */

public class FormRole implements java.io.Serializable
{
    
    // Fields
    
    private String id;
    
    private String roleName;
    
    // Property accessors
    
    public String getId()
    {
        return this.id == null ? "" : id.trim();
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getRoleName()
    {
        return this.roleName == null ? "" : roleName.trim();
    }
    
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
}