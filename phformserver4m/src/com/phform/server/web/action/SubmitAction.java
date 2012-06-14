package com.phform.server.web.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.phform.server.framework.base.web.BaseAction;
import com.phform.server.framework.result.Result;

public class SubmitAction extends BaseAction
{
    private String[] iframeValue;
    
    private InputStream formoutStream;
    
    private String formId;
    
    private String instanceId;
    
    private String roleId;
    
    private String version;
    
    private String navigatorUserAgent;
    
    public String testSubmit()
    {
        Result result = new Result();
        HttpURLConnection conn = null;
        String request = "";
        if (null != iframeValue && iframeValue.length > 0)
        {
            for (int i = 0; i < iframeValue.length; i++)
            {
                if (null != iframeValue[i] && !"".equals(iframeValue[i].trim()))
                {
                    request += "iframeValue=" + iframeValue[i].trim() + "&";
                }
            }
            if (request.endsWith("&"))
            {
                request = request.substring(0, request.length() - 1);
            }
        }
        try
        {
            URL url =
                new URL(
                        "http://localhost:8080/phformserver4m/formio/formInAjax.action");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(request
                    .length()));
            conn.setDoInput(true);
            conn.connect();
            
            OutputStreamWriter out =
                new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(request);
            out.flush();
            out.close();
            
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null)
            {
                sb.append(str.trim());
            }
            reader.close();
            System.out.println(sb.toString());
            result.setMessage(sb.toString());
            int code = conn.getResponseCode();
            if (code != 200)
            {
                System.out.println("ERROR===" + code);
            }
            else
            {
                System.out.println(conn.getResponseMessage());
                System.out.println("Success!");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setMessage(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result.setSuccessful(false);
            result.setMessage(e.getMessage());
        }
        finally
        {
            if (null != conn)
            {
                conn.disconnect();
            }
        }
        getRequest().setAttribute("result", result);
        return SUCCESS;
    }
    
    public String testFormOut()
    {
        HttpURLConnection conn = null;
        String request = "";
        request +=
            "formId=" + getFormId() + "&instanceId=" + getInstanceId()
                    + "&version=" + getVersion() + "&roleId=" + getRoleId()
                    + "&navigatorUserAgent=" + getNavigatorUserAgent();
        try
        {
            URL url =
                new URL(
                        "http://localhost:8080/phformserver4m/formio/formOut.action");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(request
                    .length()));
            conn.setDoInput(true);
            conn.connect();
            
            OutputStreamWriter out =
                new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(request);
            out.flush();
            out.close();
            
            formoutStream = conn.getInputStream();
            int code = conn.getResponseCode();
            if (code != 200)
            {
                System.out.println("ERROR===" + code);
            }
            else
            {
                System.out.println(conn.getResponseMessage());
                System.out.println("Success!");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != conn)
            {
                conn.disconnect();
            }
        }
        return SUCCESS;
    }
    
    public String testFormPrint()
    {
        HttpURLConnection conn = null;
        String request = "";
        if (null != iframeValue && iframeValue.length > 0)
        {
            for (int i = 0; i < iframeValue.length; i++)
            {
                if (null != iframeValue[i] && !"".equals(iframeValue[i].trim()))
                {
                    request += "iframeValue=" + iframeValue[i].trim() + "&";
                }
            }
            if (request.endsWith("&"))
            {
                request = request.substring(0, request.length() - 1);
            }
        }
        try
        {
            URL url =
                new URL(
                        "http://localhost:8080/phformserver4m/formio/formPrintAjax.action");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(request
                    .length()));
            conn.setDoInput(true);
            conn.connect();
            
            OutputStreamWriter out =
                new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(request);
            out.flush();
            out.close();
            
            formoutStream = conn.getInputStream();
            int code = conn.getResponseCode();
            if (code != 200)
            {
                System.out.println("ERROR===" + code);
            }
            else
            {
                System.out.println(conn.getResponseMessage());
                System.out.println("Success!");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != conn)
            {
                conn.disconnect();
            }
        }
        return SUCCESS;
    }
    
    public String[] getIframeValue()
    {
        return iframeValue;
    }
    
    public void setIframeValue(String[] iframeValue)
    {
        this.iframeValue = iframeValue;
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
        return version == null ? "" : version.trim();
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public InputStream getFormoutStream()
    {
        return formoutStream;
    }
    
    public void setFormoutStream(InputStream formoutStream)
    {
        this.formoutStream = formoutStream;
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
