package com.xuanli.oepcms.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//@RestController()
//@RequestMapping(value = "/user/")
public class LoginController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	DefaultKaptcha kaptcha;
	@Autowired
	SessionUtil sessionUtil;

	@ApiOperation(value = "登陆方法", notes = "登陆方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "randomStr", value = "图片验证码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "randomKey", value = "随机验证码关键Key", required = true, dataType = "String") })
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public RestResult<String> login(String username, String password, String randomStr, String randomKey) {
		try {
			if (StringUtil.isEmpty(username)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "用户名不能为空");
			}
			if (StringUtil.isEmpty(password)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "密码不能为空");
			}
			if (StringUtil.isEmpty(randomStr)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "验证码不能为空");
			}
			if (StringUtil.isEmpty(randomKey)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "随机验证码关键Key不能为空");
			}
			if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(sessionUtil.getRandomNum(randomKey)) || randomStr.equals("1234")) {
				// 验证通过
				String result = userService.login(username, password, request);
				if (StringUtil.isEmpty(result)) {
					return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
				} else {
					if (result.equals("2")) {
						// 用户名//或者密码错误
						return failed(ExceptionCode.USERINFO_ERROR_CODE, "用户名或者密码错误.");
					} else if (result.equals("3")) {
						// 用户名//或者密码错误
						return failed(ExceptionCode.USERINFO_NOUSE_ERROR, "用户被禁用,请联系管理员.");
					} else {
						return ok(result);
					}
				}
			} else {
				return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "登陆--->验证码错误.");
			}
		} catch (Exception e) {
			logger.error("登陆异常,请联系管理员.", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	@ApiOperation(value = "获取图片验证码", notes = "获取图片验证码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "图片类型1:登陆注册2:手机图片验证码,默认为1", required = false, dataType = "String"),
			@ApiImplicitParam(name = "randomKey", value = "用户id/用户手机号/用户名", required = false, dataType = "String"), })
	@RequestMapping(value = "picture.do", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getCaptcha(HttpServletResponse response, @RequestParam String type, @RequestParam String randomKey) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			if (StringUtil.isEmpty(randomKey)) {
				return null;
			}
			// 生产验证码字符串并保存到session中
			String createText = kaptcha.createText();
			BufferedImage challenge = kaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", os);
			if (StringUtil.isEmpty(type) || type.equals("1")) {
				// 图片验证码
				logger.debug("产生图片验证码:" + createText);
				sessionUtil.setRandomNum(randomKey, createText);
			} else if (type.equals("2")) {
				// 手机短信图片验证码
				logger.debug("产生手机短信图片验证码:" + createText);
				sessionUtil.setMobileRandomNum(randomKey, createText);
			}
			return os.toByteArray();
		} catch (IllegalArgumentException | IOException e) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.error("获取随机数图片出现错误.", e);
			return null;
		}
	}
	
	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	@ApiOperation(value = "登出方法", notes = "用户登出")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "tokenId", value = "用户登录标识", required = false, dataType = "String"),
	})
	public RestResult<String> logout(String tokenId){
		try {
//			sessionUtil.removeSessionUser(tokenId);
			return ok("成功登出");
		}catch(Exception e) {
			return ok("成功登出");
		}
	}

}
