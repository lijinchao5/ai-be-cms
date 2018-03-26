/**
 * @fileName:  SMSConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月3日 下午1:59:37
 */ 
package com.xuanli.oepcms.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** 
 * @author  QiaoYu 
 */
@Component
public class SMSConfig implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(SMSConfig.class);

	public RelaxedPropertyResolver propertyResolver;

	/**
	 * 初始化yml配置
	 */
	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "systemconfig.sms.");
	}

	public String regist_template = "";
	public String forget_template = "";
	public String login_template = "";
	public String action = "";
	public String account = "";
	public String password = "";
	public String sign = "";
	public String extno = "";
	public String host = "";

	@PostConstruct
	public void initMethod() {
		regist_template = propertyResolver.getProperty("regist_template");
		forget_template = propertyResolver.getProperty("forget_template");
		login_template = propertyResolver.getProperty("login_template");
		action = propertyResolver.getProperty("action");
		account = propertyResolver.getProperty("account");
		password = propertyResolver.getProperty("password");
		sign = propertyResolver.getProperty("sign");
		extno = propertyResolver.getProperty("extno");
		host = propertyResolver.getProperty("host");
		logger.debug("初始化完成短信系统配置信息...");
	}

}
