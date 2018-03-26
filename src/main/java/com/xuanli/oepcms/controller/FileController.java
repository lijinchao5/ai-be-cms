/**
 * @fileName:  FileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月7日 上午11:02:17
 */
package com.xuanli.oepcms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@RestController()
@RequestMapping(value = "/file/")
public class FileController extends BaseController {
	
	@Autowired
	AliOSSUtil aliOSSUtil;

	@RequestMapping(value = "download.do", method = RequestMethod.GET)
	public void download(String type, String id) {
		String filename = UUID.randomUUID().toString().replace("-", "");
		if (type.equals("mp3")) {
			filename = filename + ".mp3";
			response.setContentType("audio/mpeg");
		} else {
			filename = filename + ".jpg";
			response.setContentType("image/jpeg");
		}
		if (StringUtils.isNotBlank(id)) {
			InputStream inputStream = aliOSSUtil.downloadFile(id);
			OutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("下载文件出现错误!");
			}
		}else {
			logger.info("文件id不能为空!");
		}
	}

	@RequestMapping(value = "fileadd.do", method = RequestMethod.POST)
	public RestResult<String> fileadd(HttpServletRequest request) {

		try {
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			
			
			
			
			return ok("文件上传成功");
			
		}catch (Exception e) {
			logger.error("文件上传失败", e);
			e.printStackTrace();
			if(e instanceof ServiceException) return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
			return failed(ExceptionCode.UNKNOW_CODE, "文件上传失败", e.getMessage());
		}
		
		
	}


	
}
