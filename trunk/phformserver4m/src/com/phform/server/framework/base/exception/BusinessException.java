package com.phform.server.framework.base.exception;

import java.util.List;
import java.util.ArrayList;

import com.phform.server.framework.base.exception.vo.MessageVO;

/**
 * This exception definition only for Business validation. Encapsulate multiple
 * ValidationExceptions. Exception component will auto forward to your input
 * with ValidationExceptions.
 * 
 */
public class BusinessException extends BaseException
{
    
    static final long serialVersionUID = 20061109150001L;
    
    private List list;
    
    /**
     * Default constructor
     */
    public BusinessException()
    {
        list = new ArrayList();
    }
    
    /**
     * Add
     * 
     * @param ve
     *            ValidationException
     */
    public void add(ValidationException ve)
    {
        list.add(ve);
    }
    
    /**
     * Add
     * 
     * @param errorId
     *            Error ID
     * @param errorType
     *            Error Type
     * @param message
     *            String
     * @param args
     *            Arguments
     */
    public void add(String errorId, String errorType, Object[] args,
            String message)
    {
        add(new ValidationException(errorId, errorType, args, message));
    }
    
    /**
     * Add
     * 
     * @param errorId
     *            Error ID
     */
    public void add(String errorId, String errorType)
    {
        add(new ValidationException(errorId, errorType));
    }
    
    /**
     * Defualt errorType = "error"
     * 
     * @param errorId
     */
    public void add(String errorId)
    {
        add(new ValidationException(errorId));
    }
    
    /**
     * Count
     * 
     * @return the number of Validation
     */
    public int count()
    {
        return list.size();
    }
    
    /**
     * HasException
     * 
     * @return Whether has ValidationException
     */
    public boolean hasException()
    {
        return list.size() > 0;
    }
    
    /**
     * Get Exceptions
     * 
     * @return ValidationExceptions
     */
    public ValidationException[] getExceptions()
    {
        ValidationException[] ves = new ValidationException[list.size()];
        for (int i = 0; i < list.size(); i++)
        {
            ves[i] = (ValidationException) list.get(i);
        }
        
        return ves;
    }
    
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("[BussinessException]:\n");
        
        ValidationException[] ves = getExceptions();
        for (int i = 0; ves != null && i < ves.length; i++)
        {
            buff.append(ves[i].toString() + "\n");
        }
        return buff.toString();
    }
    
    public MessageVO[] getMessageVOs()
    {
        ValidationException[] vexs = getExceptions();
        MessageVO[] messageVOs = new MessageVO[vexs.length];
        for (int i = 0; i < vexs.length; i++)
        {
            messageVOs[i] = vexs[i].getMessageVO();
        }
        return messageVOs;
    }
    
}
