/**
 * @fileName:  CorsConfiguration.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月23日 上午11:19:12
 */
package com.xuanli.oepcms.config;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author QiaoYu
 */
@Configuration
public class MyCorsConfiguration implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(MyCorsConfiguration.class);

	public RelaxedPropertyResolver propertyResolver;

	/**
	 * 初始化yml配置
	 */
	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "systemconfig.");
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		for (String allowedOrigin : getValues("cors.allowedOrigins", "*")) {
			config.addAllowedOrigin(allowedOrigin.trim());
			logger.debug("add allowedOrigin["+allowedOrigin.trim()+"]");
		}
		for (String allowedHeader : getValues("cors.allowedHeaders", "*")) {
			config.addAllowedHeader(allowedHeader.trim());
			logger.debug("add allowedOrigin["+allowedHeader.trim()+"]");
		}
		for (String allowedMethod : getValues("cors.allowedMethods", "*")) {
			config.addAllowedMethod(allowedMethod.trim());
			logger.debug("add allowedOrigin["+allowedMethod.trim()+"]");
		}
		for (String exposedHeader : getValues("cors.exposedHeaders", "*")) {
			config.addExposedHeader(exposedHeader.trim());
			logger.debug("add allowedOrigin["+exposedHeader.trim()+"]");
		}
		config.setMaxAge(Long.parseLong(propertyResolver.getProperty("cors.maxAge", "86400")));
		config.setAllowCredentials(Boolean.parseBoolean(propertyResolver.getProperty("cors.allowCredentials", "false")));
		source.registerCorsConfiguration(propertyResolver.getProperty("cors.path", "/**"), config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(1);
		logger.info("configuration CORS complate......");
		return bean;
	}

	private List<String> getValues(String key, String defaultValue) {
		String[] values = propertyResolver.getProperty(key, defaultValue).split(",");
		return Arrays.asList(values);
	}

}
