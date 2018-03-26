package com.xuanli.oepcms.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UserAdmin;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.SessionAdminUtil;
import com.xuanli.oepcms.vo.RestResult;

@Service
public abstract class BaseController {
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected SessionAdminUtil sessionUtil;
	public final Logger logger = Logger.getLogger(this.getClass());

	public <T> RestResult<T> ok(T result) {
		return RestResult.ok(result);
	}

	public <T> RestResult<T> failed(int code, String message, T result) {
		return RestResult.failed(code, message, result);
	}

	public <T> RestResult<T> failed(int code, String message) {
		return RestResult.failed(code, message);
	}

	public String getTokenId() {
		Enumeration<String> enumeration = request.getHeaders("X-AUTH-TOKEN");
		if (enumeration.hasMoreElements()) {
			String tokenId = (String) enumeration.nextElement();
			return tokenId;
		} else {
			return null;
		}
	}

	public UserAdmin getCurrentUser() {
		Enumeration<String> enumeration = request.getHeaders("X-AUTH-TOKEN");
		if (enumeration.hasMoreElements()) {
			String tokenId = (String) enumeration.nextElement();
			UserAdmin user = sessionUtil.getSessionUser(tokenId);
			return user;
		} else {
			return null;
		}
	}

	protected PageBean initPageBean(Integer page, Integer rows) {
		if (null == page || page.intValue() == 0) {
			page = 1;
		}
		if (null == rows || rows.intValue() == 0) {
			rows = 1;
		}
		PageBean pageBean = new PageBean(page, rows);
		return pageBean;
	}
}
