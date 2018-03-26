package com.xuanli.oepcms.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static boolean isNotEmpty(String str) {
		if (null != str && str.length() > 0 && !"null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(String str) {
		if (null != str && str.trim().length() > 0 && !"null".equals(str) && !"undefined".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	public static String getEncoding(String str) {
		if (StringUtil.isEmpty(str))
			return str;
		String[] encodeArr = new String[] { "ISO-8859-1", "GB2312", "GBK", "UTF-8", "UTF-16" };
		try {
			for (String encode : encodeArr) {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					return encode;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static boolean isMobile(String mobile) {
		if (isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");  
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static boolean isEmaile(String email) {
		if (isEmpty(email)) {
			return false;
		}
		Pattern p = Pattern.compile("^\\\\w+((-\\\\w+)|(\\\\.\\\\w+))*\\\\@[A-Za-z0-9]+((\\\\.|-)[A-Za-z0-9]+)*\\\\.[A-Za-z0-9]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午3:20:41
	 */
	public static String getRandomZM(int num) {
		String str = "zxcvbnmasdfghjklqwertyuiop";
		StringBuilder code = new StringBuilder();  
        Random random = new Random();  
        for (int i = 0; i < num; i++) { 
        	int index = random.nextInt(26);
            code.append(str.substring(index,index+1));  
        }  
        return code.toString();
	}
}
