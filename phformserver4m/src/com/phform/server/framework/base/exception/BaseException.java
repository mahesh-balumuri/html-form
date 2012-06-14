package com.phform.server.framework.base.exception;

import com.phform.server.framework.base.exception.vo.MessageVO;

/**
 * 
 * This class is the framework base RuntimeException class, wraps commom
 * exception's constructs and methods.
 * 
 * You can use it to wrap your global exception with an Error ID, Exception
 * components will auto handle your exception and you don't need to catch.
 */
public abstract class BaseException extends RuntimeException
{
    private MessageVO messageVO = new MessageVO();
    
    /**
     * Default constructor
     */
    public BaseException()
    {
        super();
    }
    
    /**
     * Constructor
     * 
     * @param cause
     *            Throwable
     */
    public BaseException(Throwable cause)
    {
        // super(cause);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     */
    public BaseException(String errorId, String errorType)
    {
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID Error Type
     * @param cause
     *            Throwable
     */
    public BaseException(String errorId, String errorType, Throwable cause)
    {
        // super(cause);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param args
     *            Arguments
     */
    public BaseException(String errorId, String errorType, Object[] args)
    {
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        initeArgumentsi18n();
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param args
     *            Arguments
     * @param argsi18n
     *            Arguments i18n array
     */
    public BaseException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n)
    {
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        this.messageVO.setArgsi18n(argsi18n);
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param args
     *            Arguments
     * @param cause
     *            Throwable
     */
    public BaseException(String errorId, String errorType, Object[] args,
            Throwable cause)
    {
        // super(cause);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        initeArgumentsi18n();
    }
    
    /**
     * Constructor
     * 
     * @param errorId
     *            Error ID
     * @param args
     *            Argument array
     * @param cause
     *            Throwable
     * @param argsi18n
     *            Arguments i18n array
     */
    public BaseException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, Throwable cause)
    {
        // super(cause);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        this.messageVO.setArgsi18n(argsi18n);
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
    public BaseException(String errorId, String errorType, Object[] args,
            String message)
    {
        super(message);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        initeArgumentsi18n();
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
    public BaseException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, String message)
    {
        super(message);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        this.messageVO.setArgsi18n(argsi18n);
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
    public BaseException(String errorId, String errorType, Object[] args,
            String message, Throwable cause)
    {
        // super(message, cause);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        initeArgumentsi18n();
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
    public BaseException(String errorId, String errorType, Object[] args,
            Boolean[] argsi18n, String message, Throwable cause)
    {
        // super(message, cause);
        this.messageVO.setErrorId(errorId);
        this.messageVO.setErrorType(errorType);
        this.messageVO.setArguments(args);
        this.messageVO.setArgsi18n(argsi18n);
    }
    
    /**
     * Default errorType="error"
     * 
     * @param errorId
     */
    public BaseException(String errorId)
    {
        this.messageVO.setErrorId(errorId);
    }
    
    /**
     * @return Returns the errorId.
     */
    public String getErrorId()
    {
        return this.messageVO.getErrorId();
    }
    
    /**
     * @return Returns the errorType
     */
    public String getErrorType()
    {
        return this.messageVO.getErrorType();
    }
    
    /**
     * @return Returns the parameters.
     */
    public Object[] getArguments()
    {
        return this.messageVO.getArguments();
    }
    
    /**
     * @return Returns the argsi18n.
     */
    public Boolean[] getArgsi18n()
    {
        return this.messageVO.getArgsi18n();
    }
    
    private void initeArgumentsi18n()
    {
        if (getArguments() != null)
        {
            int argSize = getArguments().length;
            
            Boolean[] argsi18n = new Boolean[argSize];
            for (int i = 0; i < argSize; i++)
            {
                argsi18n[i] = Boolean.TRUE;
            }
            
            this.messageVO.setArgsi18n(argsi18n);
        }
    }
    
    public abstract String toString();
    
    /**
     * @return Returns the messageVO.
     */
    public MessageVO getMessageVO()
    {
        return messageVO;
    }
}
