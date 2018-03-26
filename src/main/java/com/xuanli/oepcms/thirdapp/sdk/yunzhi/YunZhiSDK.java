/**
 * @fileName:  YunZhiSDK.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:21:40
 */
package com.xuanli.oepcms.thirdapp.sdk.yunzhi;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.util.AliOSSUtil;

/**
 * @author QiaoYu
 */
@SuppressWarnings("deprecation")
@Service
public class YunZhiSDK {
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	AliOSSUtil aliOSSUtil;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 下午4:27:52
	 */
	public String generatorStudentScore(HomeworkScoreBean result) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(systemConfig.YUN_ZHI_URL);
		MultipartEntity customMultiPartEntity = new MultipartEntity();
		try {
			HttpResponse response = null;
			if (null != result.getHomeworkType() && result.getHomeworkType().intValue() == 1) {
				// 单词跟读 有音标判分
				customMultiPartEntity.addPart("mode", new StringBody("D", Charset.forName("UTF-8")));
			} else {
				// 句子的有流畅度等..
				customMultiPartEntity.addPart("mode", new StringBody("E", Charset.forName("UTF-8")));
			}
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			customMultiPartEntity.addPart("text", new StringBody(result.getStanderText(), Charset.forName("UTF-8")));
			//ContentBody fileBody = new FileBody(new File(result.getAudioPath()));
			String uuid = UUID.randomUUID().toString().replace("-", "") + ".mp3";
			InputStream is = aliOSSUtil.downloadFile(result.getAudioPath());
			ContentBody fileBody = new InputStreamBody(is, uuid);
			customMultiPartEntity.addPart("voice", fileBody);
			httpPost.setEntity(customMultiPartEntity);
			httpPost.setHeader("appkey", systemConfig.YUN_ZHI_APPKEY);
			String uuid_str = UUID.randomUUID().toString();
			httpPost.setHeader("session-id", uuid_str);
			httpPost.setHeader("device-id", uuid_str);
			response = httpclient.execute(httpPost);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				String text = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return text;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
			httpclient.close();
		}
	}

}
