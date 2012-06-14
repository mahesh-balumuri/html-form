package com.phform.server.web.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

import com.phform.server.entity.Form;
import com.phform.server.entity.FormInstance;
import com.phform.server.entity.FormRole;
import com.phform.server.framework.base.dao.BaseDao;
import com.phform.server.framework.base.model.Pagination;
import com.phform.server.framework.base.web.BaseAction;
import com.phform.server.framework.result.Result;
import com.phform.server.framework.util.FLog;
import com.phform.server.framework.util.FormFileUtil;
import com.phform.server.service.FormIOService;
import com.phform.server.web.action.form.FormInMem;
import com.phform.server.web.action.form.FormMemory;

public class FormProAction extends BaseAction
{
    private FormIOService formIOService;
    
    private BaseDao baseDao;
    
    private Form form;
    
    private File formFile;
    
    private String formFileContentType;
    
    private String formFileFileName;
    
    //
    private FormRole formRole;
    
    private static Object obj = new Object();
    
    public String addFormTemplate()
    {
        Result result = new Result();
        if (null == form)
        {
            form = new Form();
        }
        if (null != form && "".equals(form.getId())
                && !"".equals(getFormFileFileName()) && null != formFile)
        {
            synchronized (obj)
            {
                String fileName = getFormFileFileName();
                if (fileName.indexOf(".") != -1)
                {
                    fileName = fileName.substring(0, fileName.indexOf("."));
                }
                if (!"".equals(form.getFormId()))
                {
                    fileName = form.getFormId();
                }
                File file =
                    new File(FormFileUtil.getAbsolutePath("/template/"
                            + fileName + ".html"));
                if (file.exists())
                {
                    result.setSuccessful(false);
                    result.setMessage("Template Id exist!");
                }
                else
                {
                    try
                    {
                        FileUtils.copyFile(formFile, file);
                        form.setFormId(fileName);
                        form.setFormPath("/template/" + fileName + ".html");
                        
                        FormInMem fInMem = new FormInMem(form);
                        form.setFormName(fInMem.getFormName());
                        formIOService.saveObject(form);
                        FormMemory.getInstance().addForm(fInMem);
                    }
                    catch (IOException e)
                    {
                        result.setSuccessful(false);
                        result.setMessage("File upload failed!");
                        if (FLog.isError())
                        {
                            FLog.error(e);
                        }
                    }
                    catch (Exception e)
                    {
                        result.setSuccessful(false);
                        result.setMessage("Template record exist!");
                        if (FLog.isError())
                        {
                            FLog.error(e);
                        }
                    }
                }
            }
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    // only the template can modify
    public String modifyFormTemplate()
    {
        Result result = new Result();
        if (null != form && !"".equals(form.getId()) && null != formFile
                && !"".equals(getFormFileFileName()))
        {
            synchronized (obj)
            {
                form = (Form) formIOService.getObject(Form.class, form.getId());
                if (null != form)
                {
                    File file =
                        new File(FormFileUtil.getAbsolutePath(form
                                .getFormPath()));
                    
                    try
                    {
                        FileUtils.copyFile(formFile, file);
                        FormMemory.getInstance().modifyForm(form);
                    }
                    catch (IOException e)
                    {
                        result.setSuccessful(false);
                        result.setMessage("File upload failed!");
                        if (FLog.isError())
                        {
                            FLog.error(e);
                        }
                    }
                }
            }
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public String deleteFormTemplate()
    {
        Result result = new Result();
        if (null != form && !"".equals(form.getId()))
        {
            form = (Form) formIOService.getObject(Form.class, form.getId());
            if (null != form)
            {
                File file =
                    new File(FormFileUtil.getAbsolutePath(form.getFormPath()));
                try
                {
                    FileUtils.forceDelete(file);
                }
                catch (IOException e)
                {
                    if (FLog.isError())
                    {
                        FLog.error(e);
                    }
                }
                Session se = null;
                Connection conn = null;
                PreparedStatement ps = null;
                
                try
                {
                    Map paramMap = new HashMap();
                    paramMap.put("formId", form.getId());
                    List instanceList = formIOService.getFormInstance(paramMap);
                    se = baseDao.getHibernateSession();
                    conn = se.connection();
                    if (null != instanceList && !instanceList.isEmpty())
                    {
                        ps =
                            conn
                                    .prepareStatement("delete from PH_FORM_CHECKBOX where instanceId=?");
                        for (int i = 0; i < instanceList.size(); i++)
                        {
                            ps.setString(1,
                                    ((FormInstance) instanceList.get(i))
                                            .getId());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                        if (null != ps)
                        {
                            try
                            {
                                ps.close();
                            }
                            catch (Exception e)
                            {
                            }
                            ps = null;
                        }
                        ps =
                            conn
                                    .prepareStatement("delete from PH_FORM_TEXT where instanceId=?");
                        for (int i = 0; i < instanceList.size(); i++)
                        {
                            ps.setString(1,
                                    ((FormInstance) instanceList.get(i))
                                            .getId());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                        if (null != ps)
                        {
                            try
                            {
                                ps.close();
                            }
                            catch (Exception e)
                            {
                            }
                            ps = null;
                        }
                        ps =
                            conn
                                    .prepareStatement("delete from PH_FORM_TEXTAREA where instanceId=?");
                        for (int i = 0; i < instanceList.size(); i++)
                        {
                            ps.setString(1,
                                    ((FormInstance) instanceList.get(i))
                                            .getId());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                        if (null != ps)
                        {
                            try
                            {
                                ps.close();
                            }
                            catch (Exception e)
                            {
                            }
                            ps = null;
                        }
                        ps =
                            conn
                                    .prepareStatement("delete from PH_FORM_INSTANCE where formId=?");
                        ps.setString(1, form.getId());
                        ps.executeUpdate();
                        if (null != ps)
                        {
                            try
                            {
                                ps.close();
                            }
                            catch (Exception e)
                            {
                            }
                            ps = null;
                        }
                    }
                    
                    ps =
                        conn
                                .prepareStatement("delete from PH_FORM_ROLERELATION where formId=?");
                    ps.setString(1, form.getId());
                    ps.executeUpdate();
                    if (null != ps)
                    {
                        try
                        {
                            ps.close();
                        }
                        catch (Exception e)
                        {
                        }
                        ps = null;
                    }
                    
                    ps =
                        conn
                                .prepareStatement("delete from PH_FORM where id=?");
                    ps.setString(1, form.getId());
                    ps.executeUpdate();
                    if (null != ps)
                    {
                        try
                        {
                            ps.close();
                        }
                        catch (Exception e)
                        {
                        }
                        ps = null;
                    }
                }
                catch (SQLException e)
                {
                    result.setSuccessful(false);
                    result.setMessage("DB Error!");
                    if (FLog.isError())
                    {
                        FLog.error(e);
                    }
                }
                finally
                {
                    if (null != ps)
                    {
                        try
                        {
                            ps.close();
                        }
                        catch (SQLException e)
                        {
                        }
                    }
                    if (null != se)
                    {
                        try
                        {
                            se.close();
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
                FormMemory.getInstance().removeForm(form);
            }
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public String queryTemplateList()
    {
        Map paramMap = new HashMap();
        if (null != form)
        {
            if (!"".equals(form.getFormId()))
            {
                paramMap.put("formId", form.getFormId());
            }
            if (!"".equals(form.getFormName()))
            {
                paramMap.put("formName", "%" + form.getFormName() + "%");
            }
        }
        HttpServletRequest request = getRequest();
        Pagination pagination = new Pagination(request, "pagination");
        pagination = formIOService.getForm(paramMap, pagination);
        request.setAttribute("pagination", pagination);
        return SUCCESS;
    }
    
    public String queryTemplateListStep2()
    {
        if (null != formRole && !"".equals(formRole.getId()))
        {
            Object role =
                formIOService.getObject(FormRole.class, formRole.getId());
            if (null != role)
            {
                formRole = (FormRole) role;
            }
        }
        queryTemplateList();
        return SUCCESS;
    }
    
    public String queryFormTemplate()
    {
        if (null != form && !"".equals(form.getId()))
        {
            form = (Form) formIOService.getObject(Form.class, form.getId());
        }
        return SUCCESS;
    }
    
    public BaseDao getBaseDao()
    {
        return baseDao;
    }
    
    public void setBaseDao(BaseDao baseDao)
    {
        this.baseDao = baseDao;
    }
    
    public FormIOService getFormIOService()
    {
        return formIOService;
    }
    
    public void setFormIOService(FormIOService formIOService)
    {
        this.formIOService = formIOService;
    }
    
    public Form getForm()
    {
        return form;
    }
    
    public void setForm(Form form)
    {
        this.form = form;
    }
    
    public File getFormFile()
    {
        return formFile;
    }
    
    public void setFormFile(File formFile)
    {
        this.formFile = formFile;
    }
    
    public String getFormFileContentType()
    {
        return formFileContentType == null ? "" : formFileContentType.trim();
    }
    
    public void setFormFileContentType(String formFileContentType)
    {
        this.formFileContentType = formFileContentType;
    }
    
    public String getFormFileFileName()
    {
        return formFileFileName == null ? "" : formFileFileName.trim();
    }
    
    public void setFormFileFileName(String formFileFileName)
    {
        this.formFileFileName = formFileFileName;
    }
    
    public FormRole getFormRole()
    {
        return formRole;
    }
    
    public void setFormRole(FormRole formRole)
    {
        this.formRole = formRole;
    }
}
