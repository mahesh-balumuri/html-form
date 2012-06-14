package com.phform.server.framework.util;

import java.util.Random;
import java.util.UUID;

public class ValidateCodeUtil
{
    
    public static String encode(int validateBit)
    {
        String code = "";
        Random rand = new Random();
        for (int i = 0; i < validateBit; i++)
        {
            code += String.valueOf(rand.nextInt(10));
        }
        return code;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println(encode(6));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
    
}
