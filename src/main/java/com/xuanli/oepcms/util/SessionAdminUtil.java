/**
 * @fileName:  SessionUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 上午9:48:16
 */
package com.xuanli.oepcms.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.cache.MyRedisCache;
import com.xuanli.oepcms.contents.SystemContents;
import com.xuanli.oepcms.entity.UserAdmin;

/**
 * @author QiaoYu
 */
@Service
public class SessionAdminUtil {
	@Autowired
	private MyRedisCache myRedisCache;
	public Logger logger = Logger.getLogger(SessionAdminUtil.class);

	public UserAdmin getSessionUser(String key) {
		String user = myRedisCache.get(key);
		UserAdmin userEntity = JSONObject.parseObject(user, UserAdmin.class);
		return userEntity;
	}

	public void setSessionUser(String key, UserAdmin userEntity) {
		myRedisCache.put(key, JSONObject.toJSONString(userEntity));
	}
	
	
	public void removeSessionUser(String key) {
		myRedisCache.remove(key);
	}

	public String getRandomNum(String key) {
		String value = myRedisCache.get(key + "_" + SystemContents.RANDOM_NUM);
		return value;
	}

	public void setRandomNum(String key, String num) {
		myRedisCache.put(key + "_" + SystemContents.RANDOM_NUM, num);
	}

	public String getMobileRandomNum(String key) {
		String value = myRedisCache.get(key + "_" + SystemContents.MOBILE_RANDOM_NUM);
		return value;
	}

	public void setMobileRandomNum(String key, String num) {
		myRedisCache.put(key + "_" + SystemContents.MOBILE_RANDOM_NUM, num);
	}

	public String getMobileMessageRandomNum(String key) {
		String value = myRedisCache.get(key + "_" + SystemContents.MOBILE_MESSAGE_RANDOM_NUM);
		return value;
	}

	public void setMobileMessageRandomNum(String key, String num) {
		myRedisCache.put(key + "_" + SystemContents.MOBILE_MESSAGE_RANDOM_NUM, num);
	}
}
