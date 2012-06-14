package com.phform.server.framework.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class StringUtil
{
    // ~ Static fields/initializers
    // =============================================
    
    private StringUtil()
    {
    }
    
    // ~ Methods
    // ================================================================
    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials string
     * is returned
     * 
     * @param password
     *            Password or other credentials to use in authenticating this
     *            username
     * @param algorithm
     *            Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm)
    {
        byte[] unencodedPassword = password.getBytes();
        
        MessageDigest md;
        try
        {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        }
        catch (Exception e)
        {
            FLog.error("Exception: " + e);
            
            return password;
        }
        
        md.reset();
        
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++)
        {
            if ((encodedPassword[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }
            
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        
        return buf.toString();
    }
    
    /**
     * Generate a special length password string, which contains of 'a'-'z',
     * 'A'-'Z', '0'-'9'
     * 
     * @param length
     * @return String
     */
    public static String randomString(int length)
    {
        StringBuffer password = new StringBuffer();
        int index = 0;
        while (index < length)
        {
            char ascii = (char) Math.floor(Math.random() * 125);
            if ((ascii >= 'a' && ascii <= 'z')
                    || (ascii >= 'A' && ascii <= 'Z')
                    || (ascii >= '0' && ascii <= '9'))
            {
                password.append(String.valueOf(ascii));
                index++;
            }
        }
        return password.toString();
    }
    
    /**
     * Encode a string using Base64 encoding. Used when storing passwords as
     * cookies. <p/> This is weak encoding in that anyone can use the
     * decodeString routine to reverse the encoding.
     * 
     * @param str
     * @return String
     */
    public static String encodeString(String str)
    {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encodeBuffer(str.getBytes()).trim();
    }
    
    /**
     * Decode a string using Base64 encoding.
     * 
     * @param str
     * @return String
     */
    public static String decodeString(String str)
    {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        try
        {
            return new String(dec.decodeBuffer(str));
        }
        catch (IOException io)
        {
            // throw new RuntimeException(io.getMessage(), io.getCause());
            throw new RuntimeException(io.getMessage());
        }
    }
    
    /**
     * get spring context files from classpath
     * 
     * @param str
     * @return String[]
     */
    public static String[] getContextLoaders(String str)
    {
        if (str == null)
            return new String[0];
        StringTokenizer stk = new StringTokenizer(str, "\n");
        int size = stk.countTokens();
        String s[] = new String[size];
        for (int i = 0; i < size; i++)
        {
            s[i] = stk.nextToken().trim();
        }
        return s;
    }
    
    public static boolean isNull(String str)
    {
        return str == null;
    }
    
    public static boolean isEmpty(String str)
    {
        return "".equals(str);
    }
    
    public static boolean isNotNull(String str)
    {
        return !isNull(str);
    }
    
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
    
    public static boolean isNullOrEmpty(String str)
    {
        return isNull(str) || isEmpty(str);
    }
    
    public static boolean isNotNullAndNotEmpty(String str)
    {
        return !isNullOrEmpty(str);
    }
    
    public static String deNull(String str)
    {
        return (str == null) ? "" : str;
    }
    
    public static List getListByToken(String str, String token)
    {
        if (str == null)
        {
            return new ArrayList();
        }
        StringTokenizer st = new StringTokenizer(str, token);
        List list = new ArrayList();
        while (st.hasMoreElements())
        {
            list.add(st.nextElement().toString().trim());
        }
        return list;
    }
    
    public static String getFirstUpperCaseVarName(String var)
    {
        if (var == null || "".equals(var))
        {
            return var;
        }
        return var.substring(0, 1).toUpperCase()
                + var.substring(1, var.length());
    }
    
    public static String getFirstLowerCaseVarName(String var)
    {
        if (var == null || "".equals(var))
        {
            return var;
        }
        return var.substring(0, 1).toLowerCase()
                + var.substring(1, var.length());
    }
    
}
