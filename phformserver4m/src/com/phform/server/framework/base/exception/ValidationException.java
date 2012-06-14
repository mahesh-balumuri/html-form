package com.phform.server.framework.base.exception;

public class ValidationException extends BaseException
{
    
    static final long serialVersionUID = 20061109150002L;
    
    /**
     * Default constructor
     */
    public ValidationException()
    {
        super();
    }
    
    /**
     * Constructor
     * 
     * @param cause
     *            Throwable
     */
    public ValidationException(Throwable cause)
    {
        super(cause);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     * @param errorType
     *            Error ID
     */
    public ValidationException(String errorId, String errorType)
    {
        super(errorId, errorType);
    }
    
    /**
     * Default errorType = "error"
     * 
     * @param errorId
     */
    public ValidationException(String errorId)
    {
        super(errorId, "error");
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID *
     * @param errortype
     *            Error Type
     * @param cause
     *            Throwable
     */
    public ValidationException(String errorId, String errorType, Throwable cause)
    {
        super(errorId, errorType, cause);
        
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID *
     * @param errortype
     *            Error Type
     * @param args
     *            Arguments
     */
    public ValidationException(String errorId, String errorType, Object[] args)
    {
        super(errorId, errorType, args);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param args
     *            Arguments
     * @param argsi18n
     *            Arguments i18n array
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n)
    {
        super(errorId, errorType, args, argsi18n);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param args
     *            Arguments
     * @param cause
     *            Throwable
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            Throwable cause)
    {
        super(errorId, errorType, args, cause);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param args
     *            Argument array
     * @param cause
     *            Throwable
     * @param argsi18n
     *            Arguments i18n array
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, Throwable cause)
    {
        super(errorId, errorType, args, argsi18n, cause);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param message
     *            Message
     * @param args
     *            Arguments
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            String message)
    {
        super(errorId, errorType, args, message);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param message
     *            Message
     * @param args
     *            Arguments
     * 
     * @param argsi18n
     *            Arguments i18n array
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, String message)
    {
        super(errorId, errorType, args, argsi18n, message);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param message
     *            Message
     * @param args
     *            Arguments
     * @param cause
     *            Throwable
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            String message, Throwable cause)
    {
        super(errorId, errorType, args, message, cause);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param args
     *            Message
     * @param argsi18n
     *            Arguments i18n array
     * @param message
     *            Message
     * @param cause
     *            Throwable
     */
    public ValidationException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, String message, Throwable cause)
    {
        super(errorId, errorType, args, argsi18n, message, cause);
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        
        buff.append("[ValidationException]:").append(
                "errorId=" + getErrorId() + ",").append(
                "message=" + getMessage());
        
        return buff.toString();
    }
}