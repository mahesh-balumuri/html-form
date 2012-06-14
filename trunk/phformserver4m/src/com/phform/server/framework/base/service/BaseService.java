package com.phform.server.framework.base.service;

import org.springframework.context.MessageSource;

import com.phform.server.framework.base.dao.BaseDao;

import java.io.Serializable;
import java.util.Locale;

public class BaseService
{
    private BaseDao baseDao;
    
    private MessageSource messageSource;
    
    public void setBaseDao(BaseDao baseDao)
    {
        this.baseDao = baseDao;
    }
    
    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }
    
    public String getMessage(String key)
    {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
    
    public Object getObject(Class clazz, Serializable id)
    {
        return baseDao.getObject(clazz, id);
    }
    
    public void saveObject(Object o)
    {
        baseDao.saveObject(o);
    }
    
    public void removeObject(Class clazz, Serializable id)
    {
        baseDao.removeObject(clazz, id);
    }
    
}
