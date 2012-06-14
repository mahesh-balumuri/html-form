package com.phform.server.service;

import java.util.List;
import java.util.Map;

import com.phform.server.dao.FormIODao;
import com.phform.server.framework.base.model.Pagination;
import com.phform.server.framework.base.service.BaseService;

public class FormIOService extends BaseService
{
    private FormIODao formIODao;
    
    public FormIODao getFormIODao()
    {
        return formIODao;
    }
    
    public void setFormIODao(FormIODao formIODao)
    {
        this.formIODao = formIODao;
    }
    
    public List getForm(Map paramMap)
    {
        return formIODao.getForm(paramMap);
    }
    
    public Pagination getForm(Map paramMap, Pagination pagination)
    {
        return formIODao.getForm(paramMap, pagination);
    }
    
    public List getFormInstance(Map paramMap)
    {
        return formIODao.getFormInstance(paramMap);
    }
    
    public List getCheckBox(Map paramMap)
    {
        return formIODao.getCheckBox(paramMap);
    }
    
    public List getText(Map paramMap)
    {
        return formIODao.getText(paramMap);
    }
    
    public List getTextArea(Map paramMap)
    {
        return formIODao.getTextArea(paramMap);
    }
    
    public List getLatestInstanceCheckBox(Map paramMap)
    {
        return formIODao.getLatestInstanceCheckBox(paramMap);
    }
    
    public List getLatestInstanceText(Map paramMap)
    {
        return formIODao.getLatestInstanceText(paramMap);
    }
    
    public List getLatestInstanceTextArea(Map paramMap)
    {
        return formIODao.getLatestInstanceTextArea(paramMap);
    }
    
    public List getFormRole(Map paramMap)
    {
        return formIODao.getFormRole(paramMap);
    }
    
    public Pagination getFormRole(Map paramMap, Pagination pagination)
    {
        return formIODao.getFormRole(paramMap, pagination);
    }
    
    public List getFormRoleRelation(Map paramMap)
    {
        return formIODao.getFormRoleRelation(paramMap);
    }
}
