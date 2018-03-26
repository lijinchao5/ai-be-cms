package com.xuanli.oepcms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

public class JyUtil {
	/*
	 * 生成两个随机字母
	 * 
	 */
	public static String getschool_id() {
		String abc=UUID.randomUUID().toString().replace("-", "")+"wefd";
		abc=abc.replace("0", "");
		abc=abc.replace("1", "");
		abc=abc.replace("2", "");
		abc=abc.replace("3", "");
		abc=abc.replace("4", "");
		abc=abc.replace("5", "");
		abc=abc.replace("6", "");
		abc=abc.replace("7", "");
		abc=abc.replace("8", "");
		abc=abc.replace("9", "");
		return abc.substring(0, 2).toUpperCase();
	}
	
	public static Date getDate(String dateString) {
		Date parse=null;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}
	
	public static String getuuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String getQName(String type) {
		String qname="";
		if("8".equals(type)) {
			qname="x_";
		}else if("7".equals(type)) {
			qname="q_";
		}else if("6".equals(type)) {
			qname="p_";
		}else if("5".equals(type)) {
			qname="c_";
		}
		return qname;
	}
	
	public static String sendPost(String url,String param) {
//		if("1".equals("1")) {
//			return "1";
//		}
		
		String sendPostUTF8 = HttpRequest.sendPostUTF8(url, param);
		System.out.println(param);
		System.out.println(sendPostUTF8);
//		Map<String,Object> map = JSONObject.parseObject(sendPostUTF8, Map.class);
		if("1".equals(sendPostUTF8)) {
			return "1";
		}else {
			return "0";
		}
		
	}
	
}
