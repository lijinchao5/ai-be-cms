/**
 * @fileName:  AliOSSConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月1日 下午5:44:57
 */
package com.xuanli.oepcms.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.PutObjectRequest;

/**
 * @author QiaoYu
 */
@Component
public class AliOSSPool implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(AliOSSPool.class);

	public RelaxedPropertyResolver propertyResolver;

	/**
	 * 初始化yml配置
	 */
	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "systemconfig.aliOSS.");
	}

	public OSSClient ossClient = null;
	public String BUCKET_NAME = "";

	@PostConstruct
	public void initMethod() {
		BUCKET_NAME = propertyResolver.getProperty("bucketName");
		String endpoint = propertyResolver.getProperty("endpoint");
		String accessKeyId = propertyResolver.getProperty("accessKeyId");
		String accessKeySecret = propertyResolver.getProperty("accessKeySecret");
		CredentialsProvider credsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
		ClientConfiguration config = new ClientConfiguration();
		config.setMaxConnections(propertyResolver.getProperty("pool.maxConnections", Integer.class));
		config.setSocketTimeout(propertyResolver.getProperty("pool.socketTimeout", Integer.class));
		config.setConnectionTimeout(propertyResolver.getProperty("pool.connectionTimeout", Integer.class));
		config.setConnectionRequestTimeout(propertyResolver.getProperty("pool.connectionRequestTimeout", Integer.class));
		config.setIdleConnectionTime(propertyResolver.getProperty("pool.idleConnectionTime", Integer.class));
		config.setMaxErrorRetry(propertyResolver.getProperty("pool.maxErrorRetry", Integer.class));
		config.setSupportCname(propertyResolver.getProperty("pool.supportCname", Boolean.class));
		config.setSLDEnabled(propertyResolver.getProperty("pool.SLDEnabled", Boolean.class));
		if (propertyResolver.getProperty("pool.protocol", "HTTP").equals("HTTP")) {
			config.setProtocol(Protocol.HTTP);
		} else {
			config.setProtocol(Protocol.HTTPS);
		}
		config.setUserAgent(propertyResolver.getProperty("pool.userAgent"));
		ossClient = new OSSClient(endpoint, credsProvider, config);
		logger.debug("初始化阿里巴巴OOS对象存储配置...");
//		try {
//			ossClient.putObject(new PutObjectRequest(BUCKET_NAME, UUID.randomUUID().toString().replace("-", "")+".txt", createSampleFile()));
//		} catch (OSSException e) {
//			e.printStackTrace();
//		} catch (ClientException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		logger.debug("测试存储完成......");
	}

	private static File createSampleFile() throws IOException {
		File file = File.createTempFile("oss-java-sdk-", ".txt");
		file.deleteOnExit();
		Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		writer.write("abcdefghijklmnopqrstuvwxyz\n");
		writer.write("0123456789011234567890\n");
		writer.close();
		return file;
	}

}
