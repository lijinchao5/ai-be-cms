/**
 * @fileName:  FileUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月19日 上午9:41:50
 */
package com.xuanli.oepcms.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.config.SystemConfig;

/**
 * @author QiaoYu
 */
@Service
public class FileUtil {
	@Autowired
	SystemConfig systemConfig;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月19日 上午10:07:02
	 */
	public String uploadFile(InputStream inputStream, String path, HttpServletRequest request) throws IOException {
		String contextBasePath = request.getSession().getServletContext().getRealPath("/");
		String basePath = contextBasePath + "uploadFile";
		String thisBasePath = basePath + File.separator + path + File.separator;
		String fileName = UUID.randomUUID().toString().replace("-", "") + ".mp3";
		String cdDir = FileUtil.getCdDir();
		String savePath = cdDir + File.separator;
		String imagePath = thisBasePath + savePath + fileName;
		String realPath = thisBasePath + savePath;
		File savePathFile = new File(realPath);
		if (!savePathFile.exists()) {
			savePathFile.mkdirs();
		}
		File imageFile = new File(imagePath);
		// 这里就是保存
		FileUtil.copyInputStreamToFile(inputStream, imageFile);
		return imagePath;
	}

	/**
	 * 输出文件到磁盘
	 * 
	 * @param data
	 * @param filePathAndName
	 * @throws Exception
	 */
	public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
		FileUtils.copyInputStreamToFile(inputStream, file);
	}

	public static String getCdDir() {
		Calendar cal = Calendar.getInstance();
		String year = "" + (cal.get(Calendar.YEAR));
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		String day = "" + cal.get(Calendar.DAY_OF_MONTH);
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		if (month.length() < 2) {
			month = "0" + month;
		}
		if (day.length() < 2) {
			day = "0" + day;
		}
		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		// 生成的目录
		String cdDir = year + File.separator + month + File.separator + day + File.separator + hour;
		return cdDir;
	}

}
