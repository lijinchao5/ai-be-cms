/**
 * @fileName:  AliOSSUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月2日 上午9:53:24
 */
package com.xuanli.oepcms.util;

import java.io.InputStream;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.xuanli.oepcms.config.AliOSSPool;

/**
 * @author QiaoYu
 */
@Service
public class AliOSSUtil {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	AliOSSPool aliOSSPool;
	/**
	 * 上传文件
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月2日 上午10:01:09
	 */
	public String uploadFile(InputStream is, String path, String suffix) {
		String uuid = path + "_" + UUID.randomUUID().toString().replace("-", "") + "." + suffix;
		OSSClient ossClient = aliOSSPool.ossClient;
		ossClient.putObject(new PutObjectRequest(aliOSSPool.BUCKET_NAME, uuid, is));
		return uuid;
	}
	/**
	 * 下载文件
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月2日 上午10:01:14
	 */
	public InputStream downloadFile(String osId) {
		OSSClient ossClient = aliOSSPool.ossClient;
		boolean exists = ossClient.doesObjectExist(aliOSSPool.BUCKET_NAME, osId);
		if (exists) {
			// 存在
			OSSObject object = ossClient.getObject(aliOSSPool.BUCKET_NAME, osId);
			InputStream is = object.getObjectContent();
			logger.info("文件OSID:[" + osId + "]文件Content-Type:" + object.getObjectMetadata().getContentType());
			return is;
		} else {
			logger.error("文件OSID:[" + osId + "]文件不存在");
			return null;
		}

	}

}
