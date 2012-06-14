package com.phform.server.framework.base.exception.vo;

import java.io.Serializable;

public class MessageVO implements Serializable
{
    static final long serialVersionUID = 200611121742L;
    
    private String errorId;
    
    private String errorType;
    
    private Object[] arguments;
    
    private Boolean[] argsi18n;
    
    /**
     * @return Returns the argsi18n.
     */
    public Boolean[] getArgsi18n()
    {
        return argsi18n;
    }
    
    /**
     * @param argsi18n
     *            The argsi18n to set.
     */
    public void setArgsi18n(Boolean[] argsi18n)
    {
        this.argsi18n = argsi18n;
    }
    
    /**
     * @return Returns the arguments.
     */
    public Object[] getArguments()
    {
        return arguments;
    }
    
    /**
     * @param arguments
     *            The arguments to set.
     */
    public void setArguments(Object[] arguments)
    {
        this.arguments = arguments;
    }
    
    /**
     * @return Returns the errorId.
     */
    public String getErrorId()
    {
        return errorId;
    }
    
    /**
     * @param errorId
     *            The errorId to set.
     */
    public void setErrorId(String errorId)
    {
        this.errorId = errorId;
    }
    
    /**
     * 
     * @return Reutns the error type
     */
    public String getErrorType()
    {
        return errorType;
    }
    
    /**
     * @param errorType
     *            the error type to set
     */
    public void setErrorType(String errorType)
    {
        this.errorType = errorType;
    }
}
