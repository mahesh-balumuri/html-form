package com.phform.server.framework.base.exception;

public class SystemException extends BaseException
{
    /**
     * 
     */
    public SystemException()
    {
        super();
        
    }
    
    /**
     * @param errorId
     */
    public SystemException(String errorId, String errorType)
    {
        super(errorId, errorType);
        
    }
    
    /**
     * @param errorId
     * @param args
     */
    public SystemException(String errorId, String errorType, Object[] args)
    {
        super(errorId, errorType, args);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param argsi18n
     */
    public SystemException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n)
    {
        super(errorId, errorType, args, argsi18n);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param argsi18n
     * @param message
     */
    public SystemException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, String message)
    {
        super(errorId, errorType, args, argsi18n, message);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param argsi18n
     * @param message
     * @param cause
     */
    public SystemException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, String message, Throwable cause)
    {
        super(errorId, errorType, args, argsi18n, message, cause);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param argsi18n
     * @param cause
     */
    public SystemException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, Throwable cause)
    {
        super(errorId, errorType, args, argsi18n, cause);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param message
     */
    public SystemException(String errorId, String errorType, Object[] args,
            String message)
    {
        super(errorId, errorType, args, message);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param message
     * @param cause
     */
    public SystemException(String errorId, String errorType, Object[] args,
            String message, Throwable cause)
    {
        super(errorId, errorType, args, message, cause);
        
    }
    
    /**
     * @param errorId
     * @param args
     * @param cause
     */
    public SystemException(String errorId, String errorType, Object[] args,
            Throwable cause)
    {
        super(errorId, errorType, args, cause);
        
    }
    
    /**
     * @param errorId
     * @param cause
     */
    public SystemException(String errorId, String errorType, Throwable cause)
    {
        super(errorId, errorType, cause);
        
    }
    
    /**
     * @param cause
     */
    public SystemException(Throwable cause)
    {
        super(cause);
        
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        
        buff.append("[SystemException]:\n").append(
                "errorId=" + getErrorId() + ",").append(
                "message=" + getMessage());
        
        return buff.toString();
    }
}
