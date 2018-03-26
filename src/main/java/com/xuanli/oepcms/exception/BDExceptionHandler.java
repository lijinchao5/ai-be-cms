package com.xuanli.oepcms.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器
 * 
 */
@RestControllerAdvice
public class BDExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(ServiceException.class)
	public Result handleBDException(ServiceException e) {
		e.printStackTrace();
		Result r = new Result();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ModelAndView  handleRuntimeException(RuntimeException e) {
		e.printStackTrace();
		System.out.println("handleRuntimeException");
		ModelAndView mv=new ModelAndView("error");
		mv.addObject("exp", e.getMessage());
		return mv;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

	@ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
	public Result noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
		logger.error(e.getMessage(), e);
		return Result.error("没找找到页面");
	}

	@ExceptionHandler(AuthorizationException.class)
	public Result handleAuthorizationException(AuthorizationException e) {
		logger.error(e.getMessage(), e);
		return Result.error("未授权");
	}

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return Result.error("服务器错误，请联系管理员");
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result handleException(HttpRequestMethodNotSupportedException e) {
		logger.error(e.getMessage(), e);
		return Result.error("请求类型不支持!");
	}
	
}
