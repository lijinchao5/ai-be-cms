package com.xuanli.oepcms.util;

import java.util.Random;
import java.util.UUID;

public class RanNumUtil {
	
    public static String createRandomNum(int num) {  
        StringBuilder code = new StringBuilder();  
        Random random = new Random();  
        // 6位验证码  
        for (int i = 0; i < num; i++) {  
            code.append(String.valueOf(random.nextInt(10)));  
        }  
        System.out.println(code);
        return code.toString();
    }
    
    public static String getRandom() {
    	return UUID.randomUUID().toString().replace("-", "");
    }
}
