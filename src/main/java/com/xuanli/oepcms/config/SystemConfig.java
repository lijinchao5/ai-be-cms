/**
 * @fileName:  SystemConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月19日 上午9:51:54
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
 * @author QiaoYu
 */
@Component
public class SystemConfig implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	private RelaxedPropertyResolver propertyResolver;

	public String FILE_BASEPATH = "";
	public String YUN_ZHI_APPKEY = "";
	public String YUN_ZHI_URL = "";
	public String QIANTAI_URL = "";

	@PostConstruct
	public void initMethod() {
		FILE_BASEPATH = propertyResolver.getProperty("file.base-path");
		QIANTAI_URL = propertyResolver.getProperty("file.qt-url");
		YUN_ZHI_APPKEY = propertyResolver.getProperty("yunzhi.appkey");
		YUN_ZHI_URL = propertyResolver.getProperty("yunzhi.url");
		logger.debug("初始化完成系统配置信息...");
	}

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月22日 下午4:23:54
	 */

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "systemconfig.");
	}
}
