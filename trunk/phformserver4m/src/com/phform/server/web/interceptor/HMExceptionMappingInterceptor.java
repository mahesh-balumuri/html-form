package com.phform.server.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.phform.server.framework.base.exception.BusinessException;
import com.phform.server.framework.base.exception.ValidationException;
import com.phform.server.framework.base.exception.vo.MessageVO;
import com.phform.server.framework.base.web.BaseAction;
import com.phform.server.framework.util.FLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HMExceptionMappingInterceptor extends AbstractInterceptor
{
    protected Log categoryLogger;
    
    protected boolean logEnabled = true;
    
    protected String logCategory;
    
    protected String logLevel;
    
    public boolean isLogEnabled()
    {
        return logEnabled;
    }
    
    public void setLogEnabled(boolean logEnabled)
    {
        this.logEnabled = logEnabled;
    }
    
    public String getLogCategory()
    {
        return logCategory;
    }
    
    public void setLogCategory(String logCatgory)
    {
        this.logCategory = logCatgory;
    }
    
    public String getLogLevel()
    {
        return logLevel;
    }
    
    public void setLogLevel(String logLevel)
    {
        this.logLevel = logLevel;
    }
    
    public String intercept(ActionInvocation invocation) throws Exception
    {
        
        String result;
        
        try
        {
            result = invocation.invoke();
        }
        catch (Exception e)
        {
            if (isLogEnabled())
            {
                handleLogging(e);
            }
            List exceptionMappings =
                invocation.getProxy().getConfig().getExceptionMappings();
            String mappedResult =
                this.findResultFromExceptions(exceptionMappings, e);
            
            if (mappedResult != null)
            {
                result = mappedResult;
                publishException(invocation, new ExceptionHolder(e));
                BaseAction ba = (BaseAction) invocation.getAction();
                if (e instanceof BusinessException)
                {
                    ValidationException[] ve =
                        ((BusinessException) e).getExceptions();
                    List errorsMessages = new ArrayList();
                    for (ValidationException aVe : ve)
                    {
                        MessageVO mv = aVe.getMessageVO();
                        errorsMessages.add(ba.getText(mv.getErrorId(),
                                (String[]) mv.getArguments()));
                    }
                    ba.getSession().setAttribute("errorsMessages",
                            errorsMessages);
                }
                else if (e != null)
                {
                    ba.addActionError(ba.getText("errors.system.information"));
                }
                
            }
            else
            {
                throw e;
            }
        }
        
        return result;
    }
    
    /**
     * Handles the logging of the exception.
     * 
     * @param e
     *            the exception to log.
     */
    protected void handleLogging(Exception e)
    {
        if (logCategory != null)
        {
            if (categoryLogger == null)
            {
                // init category logger
                categoryLogger = LogFactory.getLog(logCategory);
            }
            doLog(categoryLogger, e);
        }
        else
        {
            FLog.debug(e);
        }
    }
    
    /**
     * Performs the actual logging.
     * 
     * @param logger
     *            the provided logger to use.
     * @param e
     *            the exception to log.
     */
    protected void doLog(Log logger, Exception e)
    {
        if (logLevel == null)
        {
            logger.debug(e.getMessage(), e);
            return;
        }
        
        if ("trace".equalsIgnoreCase(logLevel))
        {
            logger.trace(e.getMessage(), e);
        }
        else if ("debug".equalsIgnoreCase(logLevel))
        {
            logger.debug(e.getMessage(), e);
        }
        else if ("info".equalsIgnoreCase(logLevel))
        {
            logger.info(e.getMessage(), e);
        }
        else if ("warn".equalsIgnoreCase(logLevel))
        {
            logger.warn(e.getMessage(), e);
        }
        else if ("error".equalsIgnoreCase(logLevel))
        {
            logger.error(e.getMessage(), e);
        }
        else if ("fatal".equalsIgnoreCase(logLevel))
        {
            logger.fatal(e.getMessage(), e);
        }
        else
        {
            throw new IllegalArgumentException("LogLevel [" + logLevel
                    + "] is not supported");
        }
    }
    
    private String findResultFromExceptions(List exceptionMappings, Throwable t)
    {
        String result = null;
        
        // Check for specific exception mappings.
        if (exceptionMappings != null)
        {
            int deepest = Integer.MAX_VALUE;
            for (Iterator iter = exceptionMappings.iterator(); iter.hasNext();)
            {
                ExceptionMappingConfig exceptionMappingConfig =
                    (ExceptionMappingConfig) iter.next();
                int depth =
                    getDepth(exceptionMappingConfig.getExceptionClassName(), t);
                if (depth >= 0 && depth < deepest)
                {
                    deepest = depth;
                    result = exceptionMappingConfig.getResult();
                }
            }
        }
        
        return result;
    }
    
    /**
     * Return the depth to the superclass matching. 0 means ex matches exactly.
     * Returns -1 if there's no match. Otherwise, returns depth. Lowest depth
     * wins.
     * 
     * @param exceptionMapping
     *            the mapping classname
     * @param t
     *            the cause
     * @return the depth, if not found -1 is returned.
     */
    public int getDepth(String exceptionMapping, Throwable t)
    {
        return getDepth(exceptionMapping, t.getClass(), 0);
    }
    
    private int getDepth(String exceptionMapping, Class exceptionClass,
            int depth)
    {
        if (exceptionClass.getName().indexOf(exceptionMapping) != -1)
        {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class))
        {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(),
                depth + 1);
    }
    
    /**
     * Default implementation to handle ExceptionHolder publishing. Pushes given
     * ExceptionHolder on the stack. Subclasses may override this to customize
     * publishing.
     * 
     * @param invocation
     *            The invocation to publish Exception for.
     * @param exceptionHolder
     *            The exceptionHolder wrapping the Exception to publish.
     */
    protected void publishException(ActionInvocation invocation,
            ExceptionHolder exceptionHolder)
    {
        invocation.getStack().push(exceptionHolder);
    }
}
