package com.phform.server.framework.base.exception;

import org.hibernate.StaleObjectStateException;
import org.springframework.dao.*;

import javax.servlet.http.HttpServletRequest;

public class BaseExceptionHandler
{
    // private final static String ACTION_ERROR_KEY = Globals.ERROR_KEY;
    private final static String MESSEAGE_ERROR = "MESSAGE_TYPE";
    
    public static String handle(Exception e)
    {
        if (e instanceof RuntimeException)
        {
            if (e instanceof DataAccessException)
            {
                if (e instanceof ConcurrencyFailureException)
                {
                    if (e instanceof OptimisticLockingFailureException)
                    {
                        return "framework.exception.OptimisticLockingFailure";
                    }
                    else if (e instanceof PessimisticLockingFailureException)
                    {
                        if (e instanceof CannotAcquireLockException)
                        {
                            return "framework.exception.CannotAcquireLock";
                        }
                        else if (e instanceof CannotSerializeTransactionException)
                        {
                            return "framework.exception.CannotSerializeTransaction";
                        }
                        else if (e instanceof DeadlockLoserDataAccessException)
                        {
                            return "framework.exception.DeadlockLoserDataAccess";
                        }
                        return "framework.exception.PessimisticLockingFailure";
                    }
                    return "framework.exception.ConcurrencyFailure";
                }
                else if (e instanceof DataRetrievalFailureException)
                {
                    if (e instanceof IncorrectResultSizeDataAccessException)
                    {
                        return "framework.exception.IncorrectResultSizeDataAccess";
                    }
                    return "framework.exception.DataRetrievalFailure";
                }
                else if (e instanceof InvalidDataAccessResourceUsageException)
                {
                    if (e instanceof TypeMismatchDataAccessException)
                    {
                        return "framework.exception.TypeMismatchDataAccess";
                    }
                    else if (e instanceof IncorrectUpdateSemanticsDataAccessException)
                    {
                        return "framework.exception.IncorrectUpdateSemanticsDataAccess";
                    }
                    return "framework.exception.InvalidDataAccessResourceUsage";
                }
                else if (e instanceof UncategorizedDataAccessException)
                {
                    return "framework.exception.UncategorizedDataAccess";
                }
                else if (e instanceof CleanupFailureDataAccessException)
                {
                    return "framework.exception.CleanupFailureDataAccess";
                }
                else if (e instanceof DataIntegrityViolationException)
                {
                    return "framework.exception.DataIntegrityViolation";
                }
                else if (e instanceof DataAccessResourceFailureException)
                {
                    return "framework.exception.DataAccessResourceFailure";
                }
                else if (e instanceof InvalidDataAccessApiUsageException)
                {
                    return "framework.exception.InvalidDataAccessApiUsage";
                }
                return "framework.exception.DataAccess";
            }
            else if (e instanceof StaleObjectStateException)
            {
                return "framework.exception.StaleObjectStateException";
            }
        }
        return "framework.exception.Unexpected";
    }
    
    public static void handleBusinessException(HttpServletRequest request,
            BusinessException bussEx)
    {
    }
    
    public static void handleSystemException(HttpServletRequest request,
            SystemException sysEx)
    {
    }
    
    public static Object[] formatArgumentsI18n(HttpServletRequest request,
            Object[] args, Boolean[] argsi18n)
    {
        Object[] arguments = args;
        return arguments;
    }
    
    private static boolean isArgNeedi18n(Object arg, int objIndex,
            Boolean[] argsi18n)
    {
        boolean isArgi18n = false;
        int argsi18nLength = 0;
        if (argsi18n != null)
        {
            argsi18nLength = argsi18n.length;
        }
        
        if (argsi18nLength >= objIndex && arg instanceof String)
        {
            if (argsi18n[objIndex] != null)
            {
                isArgi18n = argsi18n[objIndex].booleanValue();
            }
        }
        
        return isArgi18n;
    }
    
}