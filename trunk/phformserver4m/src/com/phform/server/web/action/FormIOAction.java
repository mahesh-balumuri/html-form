package com.phform.server.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.ParserDelegator;

import org.hibernate.Session;

import com.phform.server.Constants;
import com.phform.server.entity.Form;
import com.phform.server.entity.FormCheckbox;
import com.phform.server.entity.FormInstance;
import com.phform.server.entity.FormRoleRelation;
import com.phform.server.entity.FormText;
import com.phform.server.entity.FormTextArea;
import com.phform.server.framework.base.dao.BaseDao;
import com.phform.server.framework.base.web.BaseAction;
import com.phform.server.framework.result.Result;
import com.phform.server.framework.util.FLog;
import com.phform.server.framework.util.FormFileUtil;
import com.phform.server.service.FormIOService;
import com.phform.server.web.action.form.FormInMem;
import com.phform.server.web.action.form.FormMemory;

public class FormIOAction extends BaseAction
{
    private FormIOService formIOService;
    
    private BaseDao baseDao;
    
    private InputStream formoutStream;
    
    private String[] iframeValue;
    
    private String navigatorUserAgent;
    
    private String formId;
    
    private String instanceId;
    
    private String roleId;
    
    private String version;
    
    public String formIn()
    {
        Result result = new Result();
        int retcode = Constants.FORM_RET_CODE_SUCCESS;
        Session se = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List finalRet = new ArrayList();
        try
        {
            List iframeValues = getIframeValues();
            if (!iframeValues.isEmpty())
            {
                List valuesList = new ArrayList();
                for (int j = 0; j < iframeValues.size(); j++)
                {
                    String singleValue = (String) iframeValues.get(j);
                    String[] values = singleValue.split("#");
                    
                    if (null != values && values.length > 0)
                    {
                        FormInData data = new FormInData();
                        for (int i = 0; i < values.length; i++)
                        {
                            if (values[i] != null)
                            {
                                String[] tmp = values[i].split("@");
                                if (null != tmp && tmp.length > 0)
                                {
                                    String key = "";
                                    String value = "";
                                    try
                                    {
                                        key =
                                            URLDecoder.decode(tmp[0].trim(),
                                                    "UTF-8");
                                        value =
                                            URLDecoder.decode(tmp[1].trim(),
                                                    "UTF-8");
                                    }
                                    catch (Exception e)
                                    {
                                    }
                                    if (!"".equals(key) && !"".equals(value))
                                    {
                                        if ("formId".equals(key))
                                        {
                                            data.setFormId(value);
                                        }
                                        else if ("instanceId".equals(key))
                                        {
                                            data.setInstanceId(value);
                                        }
                                        else if ("roleId".equals(key))
                                        {
                                            data.setRoleId(value);
                                        }
                                        else
                                        {
                                            if (value.length() > Constants.FORM_ELEMENT_VALUE_MAXLENGTH)
                                            {
                                                retcode =
                                                    Constants.FORM_RET_CODE_VALUE_TOOLONG;
                                                result.setSuccessful(false);
                                                result
                                                        .setMessage("FORM_RET_CODE_VALUE_TOOLONG");
                                                throw new Exception(
                                                        "FORM_RET_CODE_VALUE_TOOLONG");
                                            }
                                            data.getValueMap().put(key, value);
                                        }
                                    }
                                }
                            }
                        }
                        if (!("".equals(data.getFormId()) && "".equals(data
                                .getInstanceId())))
                        {
                            valuesList.add(data);
                        }
                    }
                }
                
                if (!valuesList.isEmpty())
                {
                    for (int j = 0; j < valuesList.size(); j++)
                    {
                        FormInData data = (FormInData) valuesList.get(j);
                        
                        // get html first
                        // instance exist
                        if (!"".equals(data.getInstanceId()))
                        {
                            Object obj =
                                formIOService.getObject(FormInstance.class,
                                        data.getInstanceId());
                            if (null != obj)
                            {
                                FormInstance fi = (FormInstance) obj;
                                Form f =
                                    (Form) formIOService.getObject(Form.class,
                                            fi.getFormId());
                                data.setFormRecordId(f.getId());
                                data.setFormId(f.getFormId());
                                FormInMem fim =
                                    FormMemory.getInstance().queryForm(f);
                                if (null == fim)
                                {
                                    FormMemory.getInstance().addForm(f);
                                    fim = FormMemory.getInstance().queryForm(f);
                                }
                                if (null != fim)
                                {
                                    Map typeMap = fim.getFormElementType();
                                    Iterator it = typeMap.entrySet().iterator();
                                    while (it.hasNext())
                                    {
                                        Entry entry = (Entry) it.next();
                                        switch (Integer.parseInt(entry
                                                .getValue().toString()))
                                        {
                                        case Constants.FORM_MEM_TYPE_CHECKBOX:
                                            if (data.getValueMap().containsKey(
                                                    entry.getKey().toString()))
                                            {
                                                if (data
                                                        .getValueMap()
                                                        .get(
                                                                entry
                                                                        .getKey()
                                                                        .toString())
                                                        .toString()
                                                        .equals(
                                                                Constants.CHECKBOX_TRUE))
                                                {
                                                    data
                                                            .getBoxValue()
                                                            .put(
                                                                    entry
                                                                            .getKey()
                                                                            .toString(),
                                                                    Constants.CHECKBOX_TRUE);
                                                }
                                            }
                                            break;
                                        case Constants.FORM_MEM_TYPE_TEXT:
                                            if (data.getValueMap().containsKey(
                                                    entry.getKey().toString()))
                                            {
                                                data
                                                        .getTextValue()
                                                        .put(
                                                                entry
                                                                        .getKey()
                                                                        .toString(),
                                                                data
                                                                        .getValueMap()
                                                                        .get(
                                                                                entry
                                                                                        .getKey()
                                                                                        .toString()));
                                            }
                                            break;
                                        case Constants.FORM_MEM_TYPE_TEXTAREA:
                                            if (data.getValueMap().containsKey(
                                                    entry.getKey().toString()))
                                            {
                                                data
                                                        .getAreaValue()
                                                        .put(
                                                                entry
                                                                        .getKey()
                                                                        .toString(),
                                                                data
                                                                        .getValueMap()
                                                                        .get(
                                                                                entry
                                                                                        .getKey()
                                                                                        .toString()));
                                            }
                                            break;
                                        default:
                                        }
                                    }
                                }
                                else
                                {
                                    retcode =
                                        Constants.FORM_RET_CODE_TEMPLATE_FILE_LOSE;
                                    result.setSuccessful(false);
                                    result
                                            .setMessage("FORM_RET_CODE_TEMPLATE_FILE_LOSE");
                                    throw new Exception(
                                            "FORM_RET_CODE_TEMPLATE_FILE_LOSE");
                                }
                            }
                            else
                            {
                                retcode = Constants.FORM_RET_CODE_INSTANCE_LOSE;
                                result.setSuccessful(false);
                                result
                                        .setMessage("FORM_RET_CODE_INSTANCE_LOSE");
                                throw new Exception(
                                        "FORM_RET_CODE_INSTANCE_LOSE");
                            }
                        }
                        // instance not exist
                        else if (!"".equals(data.getFormId()))
                        {
                            Form f = null;
                            Map paramMap = new HashMap();
                            paramMap.put("formId", data.getFormId());
                            List formList = formIOService.getForm(paramMap);
                            if (null != formList && !formList.isEmpty())
                            {
                                f = (Form) formList.get(0);
                                data.setFormRecordId(f.getId());
                            }
                            FormInMem fim = null;
                            if (null != f)
                            {
                                fim = FormMemory.getInstance().queryForm(f);
                            }
                            if (null == fim && null != f)
                            {
                                FormMemory.getInstance().addForm(f);
                                fim = FormMemory.getInstance().queryForm(f);
                            }
                            if (null != fim)
                            {
                                Map typeMap = fim.getFormElementType();
                                Iterator it = typeMap.entrySet().iterator();
                                while (it.hasNext())
                                {
                                    Entry entry = (Entry) it.next();
                                    switch (Integer.parseInt(entry.getValue()
                                            .toString()))
                                    {
                                    case Constants.FORM_MEM_TYPE_CHECKBOX:
                                        if (data.getValueMap().containsKey(
                                                entry.getKey().toString()))
                                        {
                                            if (data
                                                    .getValueMap()
                                                    .get(
                                                            entry.getKey()
                                                                    .toString())
                                                    .toString()
                                                    .equals(
                                                            Constants.CHECKBOX_TRUE))
                                            {
                                                data
                                                        .getBoxValue()
                                                        .put(
                                                                entry
                                                                        .getKey()
                                                                        .toString(),
                                                                Constants.CHECKBOX_TRUE);
                                            }
                                        }
                                        break;
                                    case Constants.FORM_MEM_TYPE_TEXT:
                                        if (data.getValueMap().containsKey(
                                                entry.getKey().toString()))
                                        {
                                            data
                                                    .getTextValue()
                                                    .put(
                                                            entry.getKey()
                                                                    .toString(),
                                                            data
                                                                    .getValueMap()
                                                                    .get(
                                                                            entry
                                                                                    .getKey()
                                                                                    .toString()));
                                        }
                                        break;
                                    case Constants.FORM_MEM_TYPE_TEXTAREA:
                                        if (data.getValueMap().containsKey(
                                                entry.getKey().toString()))
                                        {
                                            data
                                                    .getAreaValue()
                                                    .put(
                                                            entry.getKey()
                                                                    .toString(),
                                                            data
                                                                    .getValueMap()
                                                                    .get(
                                                                            entry
                                                                                    .getKey()
                                                                                    .toString()));
                                        }
                                        break;
                                    default:
                                    }
                                }
                            }
                            else
                            {
                                retcode =
                                    Constants.FORM_RET_CODE_TEMPLATE_FILE_LOSE;
                                result.setSuccessful(false);
                                result
                                        .setMessage("FORM_RET_CODE_TEMPLATE_FILE_LOSE");
                                throw new Exception(
                                        "FORM_RET_CODE_TEMPLATE_FILE_LOSE");
                            }
                        }
                        else
                        {
                            retcode = Constants.FORM_RET_CODE_FAILED;
                            result.setSuccessful(false);
                            result.setMessage("FORM_RET_CODE_FAILED");
                            throw new Exception("FORM_RET_CODE_FAILED");
                        }
                        /*
                         * if (!"".equals(getRoleId()) && !"".equals(formIdTmp)) {
                         * Map paramMap = new HashMap(); paramMap.put("formId",
                         * formIdTmp); paramMap.put("roleId", getRoleId()); List
                         * permissions =
                         * formIOService.getFormRoleRelation(paramMap); if (null !=
                         * permissions && !permissions.isEmpty()) { for (int i =
                         * 0; i < permissions.size(); i++) { FormRoleRelation
                         * relation = (FormRoleRelation) permissions.get(i); if
                         * (permissionMap.containsKey(relation .getElementId())) {
                         * String per =
                         * permissionMap.get(relation.getElementId())
                         * .toString().trim(); if
                         * (per.equals(Constants.ELEMENT_READ)) {
                         * permissionMap.put(relation.getElementId(),
                         * relation.getPermission()); } } else {
                         * permissionMap.put(relation.getElementId(),
                         * relation.getPermission()); } } } }
                         */
                    }
                    
                    synchronized (Constants.synObj)
                    {
                        se = baseDao.getHibernateSession();
                        conn = se.connection();
                        for (int j = 0; j < valuesList.size(); j++)
                        {
                            FormInData data = (FormInData) valuesList.get(j);
                            FormInResult inResult = new FormInResult();
                            int lastVersion = -1;
                            if (!"".equals(data.getInstanceId()))
                            {
                                ps =
                                    conn
                                            .prepareStatement("select t.version from PH_FORM_INSTANCE t where t.id=?");
                                ps.setString(1, data.getInstanceId());
                                rs = ps.executeQuery();
                                if (rs.next())
                                {
                                    lastVersion = rs.getInt("version");
                                }
                                if (null != rs)
                                {
                                    try
                                    {
                                        rs.close();
                                    }
                                    catch (Exception e)
                                    {
                                    }
                                    rs = null;
                                }
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
                            lastVersion++;
                            inResult.setVersion(lastVersion);
                            
                            if (!"".equals(data.getInstanceId()))
                            {
                                ps =
                                    conn
                                            .prepareStatement("update PH_FORM_INSTANCE t set t.version=? where t.id=?");
                                ps.setInt(1, lastVersion);
                                ps.setString(2, data.getInstanceId());
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
                            else if (!"".equals(data.getFormId())
                                    && !"".equals(data.getFormRecordId()))
                            {
                                data.setInstanceId(FormFileUtil.generateUUID());
                                ps =
                                    conn
                                            .prepareStatement("insert into PH_FORM_INSTANCE(id,formid,version) values(?,?,?)");
                                ps.setString(1, data.getInstanceId());
                                ps.setString(2, data.getFormRecordId());
                                ps.setInt(3, lastVersion);
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
                            if (!"".equals(data.getInstanceId()))
                            {
                                boolean batchFlag = false;
                                if (!data.getBoxValue().isEmpty())
                                {
                                    ps =
                                        conn
                                                .prepareStatement("insert into PH_FORM_CHECKBOX(id,instanceId,checkboxId,version,checkboxValue) values(?,?,?,?,?)");
                                    Iterator it =
                                        data.getBoxValue().entrySet()
                                                .iterator();
                                    while (it.hasNext())
                                    {
                                        Entry entry = (Entry) it.next();
                                        if (data.getPermissionMap()
                                                .containsKey(
                                                        entry.getKey()
                                                                .toString()))
                                        {
                                            String per =
                                                data.getPermissionMap().get(
                                                        entry.getKey()
                                                                .toString())
                                                        .toString();
                                            if (!per
                                                    .equals(Constants.ELEMENT_READ))
                                            {
                                                ps.setString(1, FormFileUtil
                                                        .generateUUID());
                                                ps.setString(2, data
                                                        .getInstanceId());
                                                ps.setString(3, entry.getKey()
                                                        .toString());
                                                ps.setInt(4, lastVersion);
                                                ps
                                                        .setString(
                                                                5,
                                                                Constants.CHECKBOX_TRUE);
                                                ps.addBatch();
                                                batchFlag = true;
                                            }
                                        }
                                        else
                                        {
                                            ps.setString(1, FormFileUtil
                                                    .generateUUID());
                                            ps.setString(2, data
                                                    .getInstanceId());
                                            ps.setString(3, entry.getKey()
                                                    .toString());
                                            ps.setInt(4, lastVersion);
                                            ps.setString(5,
                                                    Constants.CHECKBOX_TRUE);
                                            ps.addBatch();
                                            batchFlag = true;
                                        }
                                    }
                                    if (batchFlag)
                                    {
                                        ps.executeBatch();
                                        batchFlag = false;
                                    }
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
                                if (!data.getTextValue().isEmpty())
                                {
                                    ps =
                                        conn
                                                .prepareStatement("insert into PH_FORM_TEXT(id,instanceId,textId,version,textValue) values(?,?,?,?,?)");
                                    Iterator it =
                                        data.getTextValue().entrySet()
                                                .iterator();
                                    while (it.hasNext())
                                    {
                                        Entry entry = (Entry) it.next();
                                        if (data.getPermissionMap()
                                                .containsKey(
                                                        entry.getKey()
                                                                .toString()))
                                        {
                                            String per =
                                                data.getPermissionMap().get(
                                                        entry.getKey()
                                                                .toString())
                                                        .toString();
                                            if (!per
                                                    .equals(Constants.ELEMENT_READ))
                                            {
                                                ps.setString(1, FormFileUtil
                                                        .generateUUID());
                                                ps.setString(2, data
                                                        .getInstanceId());
                                                ps.setString(3, entry.getKey()
                                                        .toString());
                                                ps.setInt(4, lastVersion);
                                                ps.setString(5, entry
                                                        .getValue().toString());
                                                ps.addBatch();
                                                batchFlag = true;
                                            }
                                        }
                                        else
                                        {
                                            ps.setString(1, FormFileUtil
                                                    .generateUUID());
                                            ps.setString(2, data
                                                    .getInstanceId());
                                            ps.setString(3, entry.getKey()
                                                    .toString());
                                            ps.setInt(4, lastVersion);
                                            ps.setString(5, entry.getValue()
                                                    .toString());
                                            ps.addBatch();
                                            batchFlag = true;
                                        }
                                    }
                                    if (batchFlag)
                                    {
                                        ps.executeBatch();
                                        batchFlag = false;
                                    }
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
                                if (!data.getAreaValue().isEmpty())
                                {
                                    ps =
                                        conn
                                                .prepareStatement("insert into PH_FORM_TEXTAREA(id,instanceId,textareaId,version,textareaValue) values(?,?,?,?,?)");
                                    Iterator it =
                                        data.getAreaValue().entrySet()
                                                .iterator();
                                    while (it.hasNext())
                                    {
                                        Entry entry = (Entry) it.next();
                                        if (data.getPermissionMap()
                                                .containsKey(
                                                        entry.getKey()
                                                                .toString()))
                                        {
                                            String per =
                                                data.getPermissionMap().get(
                                                        entry.getKey()
                                                                .toString())
                                                        .toString();
                                            if (!per
                                                    .equals(Constants.ELEMENT_READ))
                                            {
                                                ps.setString(1, FormFileUtil
                                                        .generateUUID());
                                                ps.setString(2, data
                                                        .getInstanceId());
                                                ps.setString(3, entry.getKey()
                                                        .toString());
                                                ps.setInt(4, lastVersion);
                                                ps.setString(5, entry
                                                        .getValue().toString());
                                                ps.addBatch();
                                                batchFlag = true;
                                            }
                                        }
                                        else
                                        {
                                            ps.setString(1, FormFileUtil
                                                    .generateUUID());
                                            ps.setString(2, data
                                                    .getInstanceId());
                                            ps.setString(3, entry.getKey()
                                                    .toString());
                                            ps.setInt(4, lastVersion);
                                            ps.setString(5, entry.getValue()
                                                    .toString());
                                            ps.addBatch();
                                            batchFlag = true;
                                        }
                                    }
                                    if (batchFlag)
                                    {
                                        ps.executeBatch();
                                        batchFlag = false;
                                    }
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
                            }
                            inResult.setFormId(data.getFormId());
                            inResult.setFormRecordId(data.getFormRecordId());
                            inResult.setInstanceId(data.getInstanceId());
                            finalRet.add(inResult);
                        }
                    }
                }
            }
        }
        catch (UnsupportedEncodingException e)
        {
            retcode = Constants.FORM_RET_CODE_VALUE_ENCODING_ERROR;
            result.setSuccessful(false);
            result.setMessage("FORM_RET_CODE_VALUE_ENCODING_ERROR");
            if (FLog.isError())
            {
                FLog.error(e);
            }
        }
        catch (SQLException e)
        {
            retcode = Constants.FORM_RET_CODE_DB_ERROR;
            result.setSuccessful(false);
            result.setMessage("FORM_RET_CODE_DB_ERROR");
            if (FLog.isError())
            {
                FLog.error(e);
            }
        }
        catch (Exception e)
        {
            if (e.toString().indexOf("FORM_RET_CODE") == -1)
            {
                retcode = Constants.FORM_RET_CODE_FAILED;
                result.setSuccessful(false);
                result.setRetCode("FORM_RET_CODE_FAILED");
            }
            if (FLog.isError())
            {
                FLog.error(e);
            }
        }
        finally
        {
            if (null != rs)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                }
            }
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
        
        HttpServletResponse response = getResponse();
        String codeStr = retcode + "";
        if (retcode == Constants.FORM_RET_CODE_SUCCESS)
        {
            for (int i = 0; i < finalRet.size(); i++)
            {
                FormInResult inResult = (FormInResult) finalRet.get(i);
                codeStr +=
                    "#" + inResult.getFormId() + "," + inResult.getInstanceId()
                            + "," + inResult.getVersion();
            }
        }
        // resultCode#formId,instanceId,version#formId,instanceId,version
        if (FLog.isDebug())
        {
            FLog.debug("finalResultCode=" + codeStr);
        }
        try
        {
            response.getWriter().print(codeStr);
        }
        catch (IOException e)
        {
            if (FLog.isError())
            {
                FLog.error(e);
            }
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public void formInAjax()
    {
        formIn();
    }
    
    public String formOut()
    {
        String outHtml = "";
        Map valueMap = null;
        Map permissionMap = null;
        String formIdTmp = "";
        if (!"".equals(getInstanceId()))
        {
            Object obj =
                formIOService.getObject(FormInstance.class, getInstanceId());
            if (null != obj)
            {
                FormInstance fi = (FormInstance) obj;
                Form f =
                    (Form) formIOService.getObject(Form.class, fi.getFormId());
                formIdTmp = f.getId();
                FormInMem fim = FormMemory.getInstance().queryForm(f);
                if (null == fim)
                {
                    FormMemory.getInstance().addForm(f);
                    fim = FormMemory.getInstance().queryForm(f);
                }
                if (null != fim)
                {
                    outHtml = fim.getFormHtml();
                    Map paramMap = new HashMap();
                    paramMap.put("instanceId", getInstanceId());
                    List cbList = null;
                    List tList = null;
                    List taList = null;
                    if (!"".equals(getVersion()))
                    {
                        paramMap.put("version", new Long(getVersion()));
                        cbList = formIOService.getCheckBox(paramMap);
                        tList = formIOService.getText(paramMap);
                        taList = formIOService.getTextArea(paramMap);
                    }
                    else
                    {
                        cbList =
                            formIOService.getLatestInstanceCheckBox(paramMap);
                        tList = formIOService.getLatestInstanceText(paramMap);
                        taList =
                            formIOService.getLatestInstanceTextArea(paramMap);
                    }
                    
                    if ((null != cbList && !cbList.isEmpty())
                            || (null != tList && !tList.isEmpty())
                            || (null != taList && !taList.isEmpty()))
                    {
                        valueMap = new HashMap();
                        if (null != cbList && !cbList.isEmpty())
                        {
                            for (int i = 0; i < cbList.size(); i++)
                            {
                                FormCheckbox box = (FormCheckbox) cbList.get(i);
                                valueMap.put(box.getCheckboxId(), box
                                        .getCheckboxValue());
                            }
                        }
                        if (null != tList && !tList.isEmpty())
                        {
                            for (int i = 0; i < tList.size(); i++)
                            {
                                FormText text = (FormText) tList.get(i);
                                valueMap.put(text.getTextId(), text
                                        .getTextValue());
                            }
                        }
                        if (null != taList && !taList.isEmpty())
                        {
                            for (int i = 0; i < taList.size(); i++)
                            {
                                FormTextArea textarea =
                                    (FormTextArea) taList.get(i);
                                valueMap.put(textarea.getTextareaId(), textarea
                                        .getTextareaValue());
                            }
                        }
                    }
                }
            }
        }
        else if (!"".equals(getFormId()))
        {
            Form f = null;
            Map paramMap = new HashMap();
            paramMap.put("formId", getFormId());
            List formList = formIOService.getForm(paramMap);
            if (null != formList && !formList.isEmpty())
            {
                f = (Form) formList.get(0);
                formIdTmp = f.getId();
            }
            FormInMem fim = null;
            if (null != f)
            {
                fim = FormMemory.getInstance().queryForm(f);
            }
            if (null == fim && null != f)
            {
                FormMemory.getInstance().addForm(f);
                fim = FormMemory.getInstance().queryForm(f);
            }
            if (null != fim)
            {
                outHtml = fim.getFormHtml();
            }
        }
        
        if (!"".equals(getRoleId()) && !"".equals(formIdTmp))
        {
            Map paramMap = new HashMap();
            paramMap.put("formId", formIdTmp);
            paramMap.put("roleId", getRoleId());
            List permissions = formIOService.getFormRoleRelation(paramMap);
            if (null != permissions && !permissions.isEmpty())
            {
                permissionMap = new HashMap();
                for (int i = 0; i < permissions.size(); i++)
                {
                    FormRoleRelation relation =
                        (FormRoleRelation) permissions.get(i);
                    if (permissionMap.containsKey(relation.getElementId()))
                    {
                        String per =
                            permissionMap.get(relation.getElementId())
                                    .toString().trim();
                        if (per.equals(Constants.ELEMENT_READ))
                        {
                            permissionMap.put(relation.getElementId(), relation
                                    .getPermission());
                        }
                    }
                    else
                    {
                        permissionMap.put(relation.getElementId(), relation
                                .getPermission());
                    }
                }
            }
        }
        
        Reader reader = null;
        Callback call =
            new Callback(outHtml, valueMap, permissionMap, getFormId(),
                    getInstanceId(), getRoleId(), getNavigatorUserAgent());
        try
        {
            reader = new StringReader(outHtml);
            new ParserDelegator().parse(reader, call, true);
        }
        catch (IOException e1)
        {
            if (FLog.isError())
            {
                FLog.error(e1);
            }
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                }
            }
        }
        
        try
        {
            formoutStream =
                new ByteArrayInputStream(call.getRetStr().getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
        }
        setFormoutStream(formoutStream);
        
        return SUCCESS;
    }
    
    public String formPrint()
    {
        List iframeValues = getIframeValues();
        List valuesList = new ArrayList();
        if (!iframeValues.isEmpty())
        {
            for (int j = 0; j < iframeValues.size(); j++)
            {
                String singleValue = (String) iframeValues.get(j);
                String[] values = singleValue.split("#");
                
                if (null != values && values.length > 0)
                {
                    FormInData data = new FormInData();
                    for (int i = 0; i < values.length; i++)
                    {
                        if (values[i] != null)
                        {
                            String[] tmp = values[i].split("@");
                            if (null != tmp && tmp.length > 0)
                            {
                                String key = "";
                                String value = "";
                                try
                                {
                                    key =
                                        URLDecoder.decode(tmp[0].trim(),
                                                "UTF-8");
                                    value =
                                        URLDecoder.decode(tmp[1].trim(),
                                                "UTF-8");
                                }
                                catch (Exception e)
                                {
                                }
                                if (!"".equals(key) && !"".equals(value))
                                {
                                    if ("formId".equals(key))
                                    {
                                        data.setFormId(value);
                                    }
                                    else if ("instanceId".equals(key))
                                    {
                                        data.setInstanceId(value);
                                    }
                                    else if ("roleId".equals(key))
                                    {
                                        data.setRoleId(value);
                                    }
                                    else
                                    {
                                        data.getValueMap().put(key, value);
                                    }
                                }
                            }
                        }
                    }
                    if (!("".equals(data.getFormId()) && "".equals(data
                            .getInstanceId())))
                    {
                        valuesList.add(data);
                    }
                }
            }
            
            if (!valuesList.isEmpty())
            {
                String preStr = "";
                String postStr = "";
                String divStr = "";
                for (int j = 0; j < valuesList.size(); j++)
                {
                    FormInData data = (FormInData) valuesList.get(j);
                    
                    String outHtml = "";
                    Map valueMap = null;
                    Map permissionMap = null;
                    if (!"".equals(data.getInstanceId()))
                    {
                        Object obj =
                            formIOService.getObject(FormInstance.class, data
                                    .getInstanceId());
                        if (null != obj)
                        {
                            FormInstance fi = (FormInstance) obj;
                            Form f =
                                (Form) formIOService.getObject(Form.class, fi
                                        .getFormId());
                            FormInMem fim =
                                FormMemory.getInstance().queryForm(f);
                            if (null == fim)
                            {
                                FormMemory.getInstance().addForm(f);
                                fim = FormMemory.getInstance().queryForm(f);
                            }
                            if (null != fim)
                            {
                                outHtml = fim.getFormHtml();
                            }
                        }
                    }
                    else if (!"".equals(data.getFormId()))
                    {
                        Form f = null;
                        Map paramMap = new HashMap();
                        paramMap.put("formId", data.getFormId());
                        List formList = formIOService.getForm(paramMap);
                        if (null != formList && !formList.isEmpty())
                        {
                            f = (Form) formList.get(0);
                        }
                        FormInMem fim = null;
                        if (null != f)
                        {
                            fim = FormMemory.getInstance().queryForm(f);
                        }
                        if (null == fim && null != f)
                        {
                            FormMemory.getInstance().addForm(f);
                            fim = FormMemory.getInstance().queryForm(f);
                        }
                        if (null != fim)
                        {
                            outHtml = fim.getFormHtml();
                        }
                    }
                    
                    Reader reader = null;
                    PrintCallback call =
                        new PrintCallback(outHtml, data.getValueMap(), data
                                .getPermissionMap(), data.getFormId(), data
                                .getInstanceId(), data.getRoleId(), j);
                    try
                    {
                        reader = new StringReader(outHtml);
                        new ParserDelegator().parse(reader, call, true);
                    }
                    catch (IOException e1)
                    {
                        if (FLog.isError())
                        {
                            FLog.error(e1);
                        }
                    }
                    finally
                    {
                        if (null != reader)
                        {
                            try
                            {
                                reader.close();
                            }
                            catch (IOException e1)
                            {
                            }
                        }
                    }
                    divStr += call.getDivStr();
                    if ("".equals(preStr))
                    {
                        preStr += call.getPrefixStr();
                    }
                    if ("".equals(postStr))
                    {
                        postStr += call.getPostStr();
                    }
                }
                try
                {
                    formoutStream =
                        new ByteArrayInputStream((preStr + divStr + postStr)
                                .getBytes("UTF-8"));
                }
                catch (UnsupportedEncodingException e)
                {
                }
                setFormoutStream(formoutStream);
            }
        }
        
        return SUCCESS;
    }
    
    public void formPrintAjax()
    {
        List iframeValues = getIframeValues();
        List valuesList = new ArrayList();
        if (!iframeValues.isEmpty())
        {
            for (int j = 0; j < iframeValues.size(); j++)
            {
                String singleValue = (String) iframeValues.get(j);
                String[] values = singleValue.split("#");
                
                if (null != values && values.length > 0)
                {
                    FormInData data = new FormInData();
                    for (int i = 0; i < values.length; i++)
                    {
                        if (values[i] != null)
                        {
                            String[] tmp = values[i].split("@");
                            if (null != tmp && tmp.length > 0)
                            {
                                String key = "";
                                String value = "";
                                try
                                {
                                    key =
                                        URLDecoder.decode(tmp[0].trim(),
                                                "UTF-8");
                                    value =
                                        URLDecoder.decode(tmp[1].trim(),
                                                "UTF-8");
                                }
                                catch (Exception e)
                                {
                                }
                                if (!"".equals(key) && !"".equals(value))
                                {
                                    if ("formId".equals(key))
                                    {
                                        data.setFormId(value);
                                    }
                                    else if ("instanceId".equals(key))
                                    {
                                        data.setInstanceId(value);
                                    }
                                    else if ("roleId".equals(key))
                                    {
                                        data.setRoleId(value);
                                    }
                                    else
                                    {
                                        data.getValueMap().put(key, value);
                                    }
                                }
                            }
                        }
                    }
                    if (!("".equals(data.getFormId()) && "".equals(data
                            .getInstanceId())))
                    {
                        valuesList.add(data);
                    }
                }
            }
            
            if (!valuesList.isEmpty())
            {
                String preStr = "";
                String postStr = "";
                String divStr = "";
                for (int j = 0; j < valuesList.size(); j++)
                {
                    FormInData data = (FormInData) valuesList.get(j);
                    
                    String outHtml = "";
                    Map valueMap = null;
                    Map permissionMap = null;
                    if (!"".equals(data.getInstanceId()))
                    {
                        Object obj =
                            formIOService.getObject(FormInstance.class, data
                                    .getInstanceId());
                        if (null != obj)
                        {
                            FormInstance fi = (FormInstance) obj;
                            Form f =
                                (Form) formIOService.getObject(Form.class, fi
                                        .getFormId());
                            FormInMem fim =
                                FormMemory.getInstance().queryForm(f);
                            if (null == fim)
                            {
                                FormMemory.getInstance().addForm(f);
                                fim = FormMemory.getInstance().queryForm(f);
                            }
                            if (null != fim)
                            {
                                outHtml = fim.getFormHtml();
                            }
                        }
                    }
                    else if (!"".equals(data.getFormId()))
                    {
                        Form f = null;
                        Map paramMap = new HashMap();
                        paramMap.put("formId", data.getFormId());
                        List formList = formIOService.getForm(paramMap);
                        if (null != formList && !formList.isEmpty())
                        {
                            f = (Form) formList.get(0);
                        }
                        FormInMem fim = null;
                        if (null != f)
                        {
                            fim = FormMemory.getInstance().queryForm(f);
                        }
                        if (null == fim && null != f)
                        {
                            FormMemory.getInstance().addForm(f);
                            fim = FormMemory.getInstance().queryForm(f);
                        }
                        if (null != fim)
                        {
                            outHtml = fim.getFormHtml();
                        }
                    }
                    
                    Reader reader = null;
                    PrintCallback call =
                        new PrintCallback(outHtml, data.getValueMap(), data
                                .getPermissionMap(), data.getFormId(), data
                                .getInstanceId(), data.getRoleId(), j);
                    try
                    {
                        reader = new StringReader(outHtml);
                        new ParserDelegator().parse(reader, call, true);
                    }
                    catch (IOException e1)
                    {
                        if (FLog.isError())
                        {
                            FLog.error(e1);
                        }
                    }
                    finally
                    {
                        if (null != reader)
                        {
                            try
                            {
                                reader.close();
                            }
                            catch (IOException e1)
                            {
                            }
                        }
                    }
                    divStr += call.getDivStr();
                    if ("".equals(preStr))
                    {
                        preStr += call.getPrefixStr();
                    }
                    if ("".equals(postStr))
                    {
                        postStr += call.getPostStr();
                    }
                }
                HttpServletResponse response = getResponse();
                try
                {
                    response.getWriter().print(preStr + divStr + postStr);
                }
                catch (IOException e)
                {
                }
            }
        }
    }
    
    public String formRoleOut()
    {
        String outHtml = "";
        if (!"".equals(getFormId()))
        {
            Form f = new Form();
            f.setFormId(getFormId());
            FormInMem fim = FormMemory.getInstance().queryForm(f);
            if (null == fim)
            {
                Map paramMap = new HashMap();
                paramMap.put("formId", getFormId());
                List formList = formIOService.getForm(paramMap);
                if (null != formList && !formList.isEmpty())
                {
                    f = (Form) formList.get(0);
                    FormMemory.getInstance().addForm(f);
                    fim = FormMemory.getInstance().queryForm(f);
                }
            }
            if (null != fim)
            {
                outHtml = fim.getFormHtml();
            }
        }
        Reader reader = null;
        ElementCallback call = new ElementCallback(outHtml);
        try
        {
            reader = new StringReader(outHtml);
            new ParserDelegator().parse(reader, call, true);
        }
        catch (IOException e1)
        {
            if (FLog.isError())
            {
                FLog.error(e1);
            }
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                }
            }
        }
        try
        {
            formoutStream =
                new ByteArrayInputStream(call.getRetStr().getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
        }
        setFormoutStream(formoutStream);
        return SUCCESS;
    }
    
    public String formDownload()
    {
        String outHtml = "";
        if (!"".equals(getFormId()))
        {
            Form f = new Form();
            f.setFormId(getFormId());
            FormInMem fim = FormMemory.getInstance().queryForm(f);
            if (null == fim)
            {
                Map paramMap = new HashMap();
                paramMap.put("formId", getFormId());
                List formList = formIOService.getForm(paramMap);
                if (null != formList && !formList.isEmpty())
                {
                    f = (Form) formList.get(0);
                    FormMemory.getInstance().addForm(f);
                    fim = FormMemory.getInstance().queryForm(f);
                }
            }
            if (null != fim)
            {
                outHtml = fim.getFormHtml();
            }
        }
        try
        {
            formoutStream = new ByteArrayInputStream(outHtml.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
        }
        setFormoutStream(formoutStream);
        return SUCCESS;
    }
    
    public InputStream getFormoutStream()
    {
        return formoutStream;
    }
    
    public void setFormoutStream(InputStream formoutStream)
    {
        this.formoutStream = formoutStream;
    }
    
    public String[] getIframeValue()
    {
        return iframeValue;
    }
    
    public void setIframeValue(String[] iframeValue)
    {
        this.iframeValue = iframeValue;
    }
    
    public FormIOService getFormIOService()
    {
        return formIOService;
    }
    
    public void setFormIOService(FormIOService formIOService)
    {
        this.formIOService = formIOService;
    }
    
    public String getFormId()
    {
        return formId == null ? "" : formId.trim();
    }
    
    public void setFormId(String formId)
    {
        this.formId = formId;
    }
    
    public String getInstanceId()
    {
        return instanceId == null ? "" : instanceId.trim();
    }
    
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }
    
    public String getRoleId()
    {
        return roleId == null ? "" : roleId.trim();
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
    
    public String getVersion()
    {
        try
        {
            return version == null ? ""
                    : (Integer.parseInt(version.trim()) + "");
        }
        catch (Exception e)
        {
        }
        return "";
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public BaseDao getBaseDao()
    {
        return baseDao;
    }
    
    public void setBaseDao(BaseDao baseDao)
    {
        this.baseDao = baseDao;
    }
    
    public List getIframeValues()
    {
        List iframeValues = new ArrayList();
        if (null != iframeValue && iframeValue.length > 0)
        {
            for (int i = 0; i < iframeValue.length; i++)
            {
                if (null != iframeValue[i] && !"".equals(iframeValue[i].trim()))
                {
                    iframeValues.add(iframeValue[i].trim().replace(",", "%"));
                }
            }
        }
        return iframeValues;
    }

    public String getNavigatorUserAgent()
    {
        return navigatorUserAgent == null ? "" : navigatorUserAgent.trim();
    }

    public void setNavigatorUserAgent(String navigatorUserAgent)
    {
        this.navigatorUserAgent = navigatorUserAgent;
    }
}

class FormInResult
{
    private String formRecordId;
    
    private String instanceId;
    
    private String formId;
    
    private int version;
    
    public String getFormId()
    {
        return formId == null ? "" : formId.trim();
    }
    
    public void setFormId(String formId)
    {
        this.formId = formId;
    }
    
    public String getInstanceId()
    {
        return instanceId == null ? "" : instanceId.trim();
    }
    
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }
    
    public int getVersion()
    {
        return version;
    }
    
    public void setVersion(int version)
    {
        this.version = version;
    }
    
    public String getFormRecordId()
    {
        return formRecordId == null ? "" : formRecordId.trim();
    }
    
    public void setFormRecordId(String formRecordId)
    {
        this.formRecordId = formRecordId;
    }
}

class FormInData
{
    private String formRecordId;
    
    private String instanceId;
    
    private String formId;
    
    private String roleId;
    
    private Map valueMap = new HashMap();
    
    private Map boxValue = new HashMap();
    
    private Map textValue = new HashMap();
    
    private Map areaValue = new HashMap();
    
    private Map permissionMap = new HashMap();
    
    public String getFormId()
    {
        return formId == null ? "" : formId.trim();
    }
    
    public void setFormId(String formId)
    {
        this.formId = formId;
    }
    
    public String getInstanceId()
    {
        return instanceId == null ? "" : instanceId.trim();
    }
    
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }
    
    public String getRoleId()
    {
        return roleId == null ? "" : roleId.trim();
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
    
    public Map getValueMap()
    {
        return valueMap;
    }
    
    public void setValueMap(Map valueMap)
    {
        this.valueMap = valueMap;
    }
    
    public Map getAreaValue()
    {
        return areaValue;
    }
    
    public void setAreaValue(Map areaValue)
    {
        this.areaValue = areaValue;
    }
    
    public Map getBoxValue()
    {
        return boxValue;
    }
    
    public void setBoxValue(Map boxValue)
    {
        this.boxValue = boxValue;
    }
    
    public Map getPermissionMap()
    {
        return permissionMap;
    }
    
    public void setPermissionMap(Map permissionMap)
    {
        this.permissionMap = permissionMap;
    }
    
    public Map getTextValue()
    {
        return textValue;
    }
    
    public void setTextValue(Map textValue)
    {
        this.textValue = textValue;
    }
    
    public String getFormRecordId()
    {
        return formRecordId == null ? "" : formRecordId.trim();
    }
    
    public void setFormRecordId(String formRecordId)
    {
        this.formRecordId = formRecordId;
    }
}