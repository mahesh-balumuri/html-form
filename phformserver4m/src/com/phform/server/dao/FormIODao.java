package com.phform.server.dao;

import java.util.List;
import java.util.Map;

import com.phform.server.framework.base.dao.BaseDao;
import com.phform.server.framework.base.model.Pagination;

public class FormIODao
{
    private BaseDao baseDao;
    
    public List getForm(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getForm", paramMap);
    }
    
    public Pagination getForm(Map paramMap, Pagination pagination)
    {
        return baseDao.findPageByCombinedHsql("getForm", paramMap, pagination);
    }
    
    public List getFormInstance(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getFormInstance", paramMap);
    }
    
    public List getCheckBox(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getCheckBox", paramMap);
    }
    
    public List getText(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getText", paramMap);
    }
    
    public List getTextArea(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getTextArea", paramMap);
    }
    
    public List getLatestInstanceCheckBox(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getLatestInstanceCheckBox",
                paramMap);
    }
    
    public List getLatestInstanceText(Map paramMap)
    {
        return baseDao
                .findListByCombinedHsql("getLatestInstanceText", paramMap);
    }
    
    public List getLatestInstanceTextArea(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getLatestInstanceTextArea",
                paramMap);
    }
    
    public List getFormRole(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getFormRole", paramMap);
    }
    
    public Pagination getFormRole(Map paramMap, Pagination pagination)
    {
        return baseDao.findPageByCombinedHsql("getFormRole", paramMap,
                pagination);
    }
    
    public List getFormRoleRelation(Map paramMap)
    {
        return baseDao.findListByCombinedHsql("getFormRoleRelation", paramMap);
    }
    
    public BaseDao getBaseDao()
    {
        return baseDao;
    }
    
    public void setBaseDao(BaseDao baseDao)
    {
        this.baseDao = baseDao;
    }
}
