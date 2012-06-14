package com.phform.server.framework.result;

public class Result
{
    private boolean successful;
    
    // -1:undefined result
    private String retCode;
    
    private String message;
    
    private String back;
    
    public String getBack()
    {
        return back;
    }
    
    public void setBack(String back)
    {
        this.back = back;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public Result()
    {
        this.successful = true;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public Result(boolean successful, String retCode, String message)
    {
        this.successful = successful;
        this.retCode = retCode;
        this.message = message;
    }
    
    public String getRetCode()
    {
        return retCode;
    }
    
    public void setRetCode(String retCode)
    {
        this.retCode = retCode;
    }
    
    public boolean isSuccessful()
    {
        return successful;
    }
    
    public void setSuccessful(boolean successful)
    {
        this.successful = successful;
    }
}
