package com.phform.server.web.action.form;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.ParserDelegator;

import com.phform.server.entity.Form;
import com.phform.server.framework.util.FLog;
import com.phform.server.framework.util.FormFileUtil;

public class FormInMem
{
    private String formHtml;
    
    private String formId;
    
    private String formName;
    
    private Map formElementType;
    
    public FormInMem(FormInMem f)
    {
        this.formHtml = f.getFormHtml();
        this.formId = f.getFormId();
        this.formName = f.getFormName();
        try
        {
            this.formElementType = new HashMap(f.getFormElementType());
        }
        catch (Exception e)
        {
            this.formElementType = new HashMap();
        }
    }
    
    public FormInMem(Form f)
    {
        this.formId = f.getFormId();
        this.formName = f.getFormName();
        this.formElementType = new HashMap();
        Reader reader = null;
        char[] c = new char[4096];
        List ret = new ArrayList();
        List retSum = new ArrayList();
        int sum = 0;
        try
        {
            reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(
                        FormFileUtil.getAbsolutePath(f.getFormPath())), Charset
                        .forName("UTF-8")));
            int k = -1;
            while (-1 != (k = reader.read(c)))
            {
                ret.add(c);
                retSum.add(k);
                sum += k;
                c = new char[4096];
            }
        }
        catch (FileNotFoundException e1)
        {
            if (FLog.isError())
            {
                FLog.error(e1);
            }
        }
        catch (IOException e2)
        {
            if (FLog.isError())
            {
                FLog.error(e2);
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
        c = new char[sum];
        int k = 0;
        for (int i = 0; i < ret.size(); i++)
        {
            char[] tmp = (char[]) ret.get(i);
            int tmpLength = Integer.parseInt(retSum.get(i).toString());
            for (int j = 0; j < tmpLength; j++)
            {
                c[k++] = tmp[j];
            }
        }
        
        ret.clear();
        retSum.clear();
        this.formHtml = new String(c);
        
        MemCallback callback = new MemCallback(this.formHtml, formElementType);
        try
        {
            reader = new StringReader(this.formHtml);
            new ParserDelegator().parse(reader, callback, true);
            if ("".equals(getFormName()))
            {
                this.formName = callback.getHtmlTitle();
            }
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
        callback = null;
    }
    
    public FormInMem(String formHtml, String formId, String formName)
    {
        this.formHtml = formHtml;
        this.formId = formId;
        this.formName = formName;
        this.formElementType = new HashMap();
        MemCallback callback = new MemCallback(this.formHtml, formElementType);
        Reader reader = null;
        try
        {
            reader = new StringReader(this.formHtml);
            new ParserDelegator().parse(reader, callback, true);
            if ("".equals(getFormName()))
            {
                this.formName = callback.getHtmlTitle();
            }
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
        callback = null;
    }
    
    public Map getFormElementType()
    {
        return formElementType;
    }
    
    public void setFormElementType(Map formElementType)
    {
        this.formElementType = formElementType;
    }
    
    public String getFormHtml()
    {
        return formHtml == null ? "" : formHtml.trim();
    }
    
    public void setFormHtml(String formHtml)
    {
        this.formHtml = formHtml;
    }
    
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
    
}
