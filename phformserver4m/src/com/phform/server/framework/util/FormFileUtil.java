package com.phform.server.framework.util;

import java.util.UUID;

import org.apache.struts2.ServletActionContext;

public class FormFileUtil
{
    public static String getAbsolutePath(String relatePath)
    {
        return ServletActionContext.getServletContext().getRealPath(relatePath);
    }
    
    public static String generateUUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    public static String diagnoseBrowser(String navigatorUserAgent)
    {
        String browser = "msie";
        if (null != navigatorUserAgent && !"".equals(navigatorUserAgent.trim()))
        {
            navigatorUserAgent = navigatorUserAgent.trim();
            // first chrome/safari
            if (navigatorUserAgent.toLowerCase()
                    .indexOf("WebKit".toLowerCase()) != -1)
            {
                browser = "webkit";
            }
            // opera
            else if (navigatorUserAgent.toLowerCase().indexOf(
                    "Presto".toLowerCase()) != -1)
            {
                browser = "presto";
            }
            // firfox
            else if (navigatorUserAgent.toLowerCase().indexOf(
                    "Gecko".toLowerCase()) != -1)
            {
                browser = "gecko";
            }
            // ie
            else if (navigatorUserAgent.toLowerCase().indexOf(
                    "MSIE".toLowerCase()) != -1)
            {
                browser = "msie";
            }
        }
        return browser;
    }
}
