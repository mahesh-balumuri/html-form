package com.phform.server.web.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.phform.server.Constants;
import com.phform.server.entity.Form;
import com.phform.server.entity.FormRole;
import com.phform.server.entity.FormRoleRelation;
import com.phform.server.framework.base.dao.BaseDao;
import com.phform.server.framework.base.model.Pagination;
import com.phform.server.framework.base.web.BaseAction;
import com.phform.server.framework.result.Result;
import com.phform.server.framework.util.FLog;
import com.phform.server.framework.util.FormFileUtil;
import com.phform.server.service.FormIOService;
import com.phform.server.web.action.form.FormInMem;
import com.phform.server.web.action.form.FormMemory;

public class RoleAction extends BaseAction
{
    private BaseDao baseDao;
    
    private FormIOService formIOService;
    
    private FormRole formRole;
    
    private Form form;
    
    private String[] elementIds;
    
    public String addRole()
    {
        Result result = new Result();
        if (null != formRole && "".equals(formRole.getId())
                && !"".equals(formRole.getRoleName()))
        {
            formIOService.saveObject(formRole);
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public String modifyRole()
    {
        Result result = new Result();
        if (null != formRole && !"".equals(formRole.getId())
                && !"".equals(formRole.getRoleName()))
        {
            formIOService.saveObject(formRole);
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public String queryRoleList()
    {
        Map paramMap = new HashMap();
        if (null != formRole)
        {
            if (!"".equals(formRole.getRoleName()))
            {
                paramMap.put("roleName", "%" + formRole.getRoleName() + "%");
            }
        }
        HttpServletRequest request = getRequest();
        Pagination pagination = new Pagination(request, "pagination");
        pagination = formIOService.getFormRole(paramMap, pagination);
        request.setAttribute("pagination", pagination);
        
        return SUCCESS;
    }
    
    public String queryRole()
    {
        if (null != formRole && !"".equals(formRole.getId()))
        {
            formRole =
                (FormRole) formIOService.getObject(FormRole.class, formRole
                        .getId());
        }
        return SUCCESS;
    }
    
    public String deleteRole()
    {
        Result result = new Result();
        if (null != formRole && !"".equals(formRole.getId()))
        {
            Session se = null;
            Connection conn = null;
            PreparedStatement ps = null;
            try
            {
                se = baseDao.getHibernateSession();
                conn = se.connection();
                ps =
                    conn
                            .prepareStatement("delete from PH_FORM_ROLERELATION where roleId=?");
                ps.setString(1, formRole.getId());
                ps.executeUpdate();
                
                formIOService.removeObject(FormRole.class, formRole.getId());
            }
            catch (SQLException e)
            {
                result.setSuccessful(false);
                result.setMessage("DB error!");
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
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public String getRoleRelationList()
    {
        if (null != formRole && null != form && !"".equals(formRole.getId())
                && !"".equals(form.getId()))
        {
            Object obj = formIOService.getObject(Form.class, form.getId());
            Object obj2 =
                formIOService.getObject(FormRole.class, formRole.getId());
            if (null != obj && null != obj2)
            {
                form = (Form) obj;
                formRole = (FormRole) obj2;
                Map paramMap = new HashMap();
                paramMap.put("roleId", formRole.getId());
                paramMap.put("formId", form.getId());
                List list = formIOService.getFormRoleRelation(paramMap);
                FormInMem fim = FormMemory.getInstance().queryForm(form);
                if (null == fim)
                {
                    FormMemory.getInstance().addForm(form);
                    fim = FormMemory.getInstance().queryForm(form);
                }
                if (null != fim)
                {
                    Map newMap = new HashMap(fim.getFormElementType());
                    if (null != list && !list.isEmpty())
                    {
                        for (int i = 0; i < list.size(); i++)
                        {
                            FormRoleRelation relation =
                                (FormRoleRelation) list.get(i);
                            if (newMap.containsKey(relation.getElementId())
                                    && relation.getPermission().equals(
                                            Constants.ELEMENT_READ))
                            {
                                newMap.put(relation.getElementId(), true);
                            }
                        }
                    }
                    getRequest().setAttribute("newMap", newMap);
                }
            }
        }
        return SUCCESS;
    }
    
    public String addRoleRelation()
    {
        Result result = new Result();
        if (null != formRole && null != form && !"".equals(formRole.getId())
                && !"".equals(form.getId()))
        {
            Object obj = formIOService.getObject(Form.class, form.getId());
            Object obj2 =
                formIOService.getObject(FormRole.class, formRole.getId());
            if (null != obj && null != obj2)
            {
                Session se = null;
                Connection conn = null;
                PreparedStatement ps = null;
                try
                {
                    se = baseDao.getHibernateSession();
                    conn = se.connection();
                    ps =
                        conn
                                .prepareStatement("delete from PH_FORM_ROLERELATION where roleId=? and formId=?");
                    ps.setString(1, formRole.getId());
                    ps.setString(2, form.getId());
                    ps.executeUpdate();
                    
                    if (null != elementIds && elementIds.length > 0)
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
                            ps = null;
                        }
                        ps =
                            conn
                                    .prepareStatement("insert into PH_FORM_ROLERELATION(id,formId,roleId,elementId,permission) values(?,?,?,?,?)");
                        boolean isBatch = false;
                        for (int i = 0; i < elementIds.length; i++)
                        {
                            if (elementIds[i] != null
                                    && !"".equals(elementIds[i].toString()
                                            .trim()))
                            {
                                ps.setString(1, FormFileUtil.generateUUID());
                                ps.setString(2, form.getId());
                                ps.setString(3, formRole.getId());
                                ps
                                        .setString(4, elementIds[i].toString()
                                                .trim());
                                ps.setString(5, Constants.ELEMENT_READ);
                                ps.addBatch();
                                isBatch = true;
                            }
                        }
                        if (isBatch)
                        {
                            ps.executeBatch();
                        }
                    }
                }
                catch (SQLException e)
                {
                    result.setSuccessful(false);
                    result.setMessage("DB error!");
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
            }
            
        }
        getRequest().setAttribute("result", result);
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
    
    public FormRole getFormRole()
    {
        return formRole;
    }
    
    public void setFormRole(FormRole formRole)
    {
        this.formRole = formRole;
    }
    
    public Form getForm()
    {
        return form;
    }
    
    public void setForm(Form form)
    {
        this.form = form;
    }
    
    public String[] getElementIds()
    {
        return elementIds;
    }
    
    public void setElementIds(String[] elementIds)
    {
        this.elementIds = elementIds;
    }
}
