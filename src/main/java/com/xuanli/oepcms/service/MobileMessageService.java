/**
 * @fileName:  MobileMessageService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午4:02:15
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.thirdapp.sdk.sms.SendMobileMessageService;

/**
 * @author QiaoYu
 */
@Service
public class MobileMessageService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	UserService userService;
	@Autowired
	SendMobileMessageService sendMobileMessageService;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午4:22:05
	 */
	public String registMsg(String mobile, String randomNum) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		List<UserEntity> userEntities = userService.selectUserEntity(userEntity);
		if (null != userEntities && userEntities.size() <= 0) {
			// 查找短信模板
			// 调用发送短信服务
			sendMobileMessageService.sendMsg("1", mobile, randomNum);
			return "1";
		} else {
			// 手机号码已经存在
			return "2";
		}
	}

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 上午9:16:09
	 */
	public String forgetPassword(String mobile, String randomNum) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		List<UserEntity> userEntities = userService.selectUserEntity(userEntity);
		if (null != userEntities && userEntities.size() > 0) {
			// 调用发送短信服务
			sendMobileMessageService.sendMsg("2", mobile, randomNum);
			return "1";
		} else {
			// 手机号码不存在,不用发送短信
			return "2";
		}
	}

}
