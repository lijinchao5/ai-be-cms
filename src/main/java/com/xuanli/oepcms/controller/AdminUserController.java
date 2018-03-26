package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserAdmin;
import com.xuanli.oepcms.service.AdminUserService;
import com.xuanli.oepcms.util.SessionAdminUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/*
 * 登陆后基本操作
 * 
 * 
 */
@RestController()
@RequestMapping(value = "/admin/")
public class AdminUserController extends BaseController {
	@Autowired
	AdminUserService adminUserService;
	@Autowired
	DefaultKaptcha kaptcha;
	@Autowired
	SessionAdminUtil sessionUtil;
	
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	@ApiOperation(value = "登出方法", notes = "用户登出")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "tokenId", value = "用户登录标识", required = false, dataType = "String"),
	})
	public RestResult<String> logout(String tokenId){
		try {
			sessionUtil.removeSessionUser(tokenId);
			return ok("成功登出");
		}catch(Exception e) {
			return ok("成功登出");
		}
	}
	
	
	@RequestMapping(value = "updatepwd.do", method = RequestMethod.POST)
	@ApiOperation(value = "修改密码", notes = "修改密码")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "oldpwd", value = "旧密码", required = false, dataType = "String"),
		@ApiImplicitParam(name = "newpwd", value = "新密码", required = false, dataType = "String"),
	})
	public RestResult<String> updatepwd(String oldpwd,String newpwd){
		try {
			UserAdmin currentUser = getCurrentUser();
			String result = adminUserService.updatepwd(currentUser,oldpwd, newpwd, request);
			if (StringUtil.isEmpty(result)) {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
			} else {
				if (result.equals("2")) {
					// 用户名//或者密码错误
					return failed(ExceptionCode.USERINFO_ERROR_CODE, "原密码错误");
				} else {
					return ok(result);
				}
			}
		}catch(Exception e) {
			return ok("成功登出");
		}
	}

}
