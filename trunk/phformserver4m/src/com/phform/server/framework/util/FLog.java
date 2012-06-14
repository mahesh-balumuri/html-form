package com.phform.server.framework.util;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FLog
{
    private static String FQCN = FLog.class.getName();
    
    private static Logger mylog = Logger.getLogger("A1");
    
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.Category#debug(java.lang.Object)
     */
    public static void debug(Object message)
    {
        mylog.log(FQCN, Level.DEBUG, message, null);
    }
    
    public static void debug(Exception e)
    {
        if (null != e)
        {
            mylog.log(FQCN, Level.DEBUG, null, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.Category#error(java.lang.Object)
     */
    public static void error(Object message)
    {
        mylog.log(FQCN, Level.ERROR, message, null);
    }
    
    public static void error(Exception e)
    {
        if (null != e)
        {
            mylog.log(FQCN, Level.ERROR, null, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.Category#fatal(java.lang.Object)
     */
    public static void fatal(Object message)
    {
        mylog.log(FQCN, Level.FATAL, message, null);
    }
    
    public static void fatal(Exception e)
    {
        if (null != e)
        {
            mylog.log(FQCN, Level.FATAL, null, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.Category#info(java.lang.Object)
     */
    public static void info(Object message)
    {
        mylog.log(FQCN, Level.INFO, message, null);
    }
    
    public static void info(Exception e)
    {
        if (null != e)
        {
            mylog.log(FQCN, Level.INFO, null, e);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.Category#warn(java.lang.Object)
     */
    public static void warn(Object message)
    {
        mylog.log(FQCN, Level.WARN, message, null);
    }
    
    public static void warn(Exception e)
    {
        if (null != e)
        {
            mylog.log(FQCN, Level.WARN, null, e);
        }
    }
    
    public static boolean isDebug()
    {
        return mylog.isEnabledFor(Level.DEBUG);
    }
    
    public static boolean isInfo()
    {
        return mylog.isEnabledFor(Level.INFO);
    }
    
    public static boolean isWarn()
    {
        return mylog.isEnabledFor(Level.WARN);
    }
    
    public static boolean isError()
    {
        return mylog.isEnabledFor(Level.ERROR);
    }
    
    public static boolean isFatal()
    {
        return mylog.isEnabledFor(Level.FATAL);
    }
    
}
