/**
 * @fileName:  SendMobileMessageService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月16日 上午9:18:09
 */
package com.xuanli.oepcms.thirdapp.sdk.sms;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.config.SMSConfig;
import com.xuanli.oepcms.entity.SMSEntity;
import com.xuanli.oepcms.service.SMSService;

/**
 * @author QiaoYu
 */
@Service
public class SendMobileMessageService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	SMSConfig smsConfig;
	@Autowired
	SMSService smsService;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月16日 上午9:21:14
	 */
	public void sendMsg(String type, String mobile, String randomNum) {
		String msg = "";
		if (type.equals("1")) {
			// 注册验证码
			msg = String.format(smsConfig.regist_template, randomNum);
		} else if (type.equals("2")) {
			// 忘记验证码+
			msg = String.format(smsConfig.forget_template, randomNum);
		} else if (type.equals("3")) {
			// 登录验证码
			msg = String.format(smsConfig.login_template, randomNum);
		}

		logger.info("调用发送短信服务:[" + mobile + "]:" + msg);
		SendSms post = new SendSms();
		String content = smsConfig.sign + msg;
		String rt = "json";
		String parm = "action=" + smsConfig.action + "&account=" + smsConfig.account + "&password=" + smsConfig.password + "&mobile=" + mobile + "&content=" + content + "&extno="
				+ smsConfig.extno + "&rt=" + rt;
		String sr = post.sendPost(smsConfig.host, parm);
		try {
			SMSEntity smsEntity = new SMSEntity();
			smsEntity.setCreateDate(new Date());
			smsEntity.setMobile(mobile);
			smsEntity.setContent(msg);
			smsEntity.setStatus(sr);
			smsService.insertSMS(smsEntity);
		} catch (Exception e) {
			logger.error("调用短信接口出现错误!", e);
		}
	}

}
