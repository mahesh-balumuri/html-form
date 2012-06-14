package com.phform.server;

public class Constants
{
    public static final int FORM_MEM_TYPE_TEXT = 0x0001;
    
    public static final int FORM_MEM_TYPE_TEXTAREA = 0x0003;
    
    public static final int FORM_MEM_TYPE_CHECKBOX = 0x0005;
    
    public static final String ELEMENT_READ = "1";
    
    public static final String ELEMENT_WRITE = "2";
    
    public static final String ELEMENT_READ_WRITE = "3";
    
    public static final String CHECKBOX_TRUE = "1";
    
    public static final String CHECKBOX_FALSE = "0";
    
    public static final int FORM_ELEMENT_VALUE_MAXLENGTH = 1000;
    
    public static final Object synObj = new Object();
    
    // Server return code
    public static final int FORM_RET_CODE_FAILED = 0x0000;
    
    public static final int FORM_RET_CODE_SUCCESS = 0x0001;
    
    public static final int FORM_RET_CODE_TEMPLATE_FILE_LOSE = 0x0002;
    
    public static final int FORM_RET_CODE_TEMPLATE_RECORD_LOSE = 0x0003;
    
    public static final int FORM_RET_CODE_INSTANCE_LOSE = 0x0004;
    
    public static final int FORM_RET_CODE_DB_ERROR = 0x0005;
    
    public static final int FORM_RET_CODE_VALUE_TOOLONG = 0x0006;
    
    public static final int FORM_RET_CODE_VALUE_ENCODING_ERROR = 0x0007;
}
