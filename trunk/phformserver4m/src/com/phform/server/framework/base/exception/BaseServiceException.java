package com.phform.server.framework.base.exception;

public class BaseServiceException extends RuntimeException
{
    private boolean isI18nArgs;
    
    private String[] args;
    
    public BaseServiceException()
    {
        super();
    }
    
    public BaseServiceException(String message)
    {
        super(message);
    }
    
    public BaseServiceException(String message, String[] args)
    {
        super(message);
        this.args = args;
        this.isI18nArgs = true;
    }
    
    public BaseServiceException(String message, String[] args,
            boolean isI18nArgs)
    {
        super(message);
        this.args = args;
        this.isI18nArgs = isI18nArgs;
    }
    
    public BaseServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public String[] getArgs()
    {
        return args;
    }
    
    public boolean isI18nArgs()
    {
        return isI18nArgs;
    }
}
