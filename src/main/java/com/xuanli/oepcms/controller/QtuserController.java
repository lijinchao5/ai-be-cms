package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.QtuserService;
import com.xuanli.oepcms.util.ConstantUtil;
import com.xuanli.oepcms.util.JyUtil;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
/*
 * 前台用户管理
 * 
 */
@RestController
@RequestMapping(value = "/user/")
public class QtuserController extends BaseController {
	@Autowired
	private QtuserService qtuserService;
	@Autowired
	SessionUtil sessionUtil;

	@ApiIgnore
	@RequestMapping(value = "saveUser.do")
	public RestResult<String> saveUser(String name,String type,String relationid ) {
		try {
			UserEntity user=new UserEntity();
			user.setNameNum(JyUtil.getQName(type)+name);
			user.setCmsId(JyUtil.getuuid());
			user.setRoleId(Integer.parseInt(type));
			if("8".equals(type)) {
				user.setSchoolid(relationid);
			}else {
				user.setAreaid(relationid);
			}
			try {
				user.setCreateId(getCurrentUser().getId().intValue() + "");
			} catch (Exception e) {
				logger.error("设置创建人失败!");
			}
			user.setPassword(PasswordUtil.generate(ConstantUtil.USERPASSWORD));
			int result = qtuserService.saveUser(user);
			if (result > 0) {
				return ok("增加用户成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加用户失败.");
			}
		} catch (Exception e) {
			logger.error("增加用户失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加用户失败.");
		}
	}


	/**
	 * @Description: TODO 重置密码
	 */
	@ApiOperation(value = "重置密码", notes = "重置用户密码方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "Long id", value = "用户id", required = true, dataType = "Long") })
	@RequestMapping(value = "resetPassword.do")
	public RestResult<String> resetPassword(Long id) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "参数Id不能为空");
			}
			UserEntity userEntity = new UserEntity();
			userEntity.setId(id);
			int result = qtuserService.resetStudentPassword(userEntity);
			if (result > 0) {
				return ok("操作成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "重置密码失败.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重置密码出现错误.");
			return failed(ExceptionCode.REST_STUDENT_PASSWORD_ERROR, "重置密码出现错误.");
		}
	}

	
	@ApiIgnore
	@RequestMapping(value = "updateUser.do")
	public RestResult<String> updateUser(UserEntity user) {
		try {
			if (null == user) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "修改用户不能为空");
			}
			try {
				user.setCreateId(getCurrentUser().getId().intValue() + "");
			} catch (Exception e) {
				logger.error("设置创建人失败!");
			}
			int result = qtuserService.updateUser(user);
			if (result > 0) {
				return ok("增加用户成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改用户失败.");
			}
		} catch (Exception e) {
			logger.error("增加用户失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改用户失败.");
		}
	}
	
	@ApiIgnore
	@RequestMapping(value = "delUser.do")
	public RestResult<String> delUser(long  id) {
		try {
			int result = qtuserService.deleteUserEntity(id);
			if (result > 0) {
				return ok("删除用户成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "删除用户失败");
			}
		} catch (Exception e) {
			logger.error("删除用户失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改用户失败.");
		}
	}
	
	
	@ApiIgnore
	@RequestMapping(value = "findByID.do")
	public RestResult<Object> findByID(long  id) {
		try {
			UserEntity user = qtuserService.selectById(id);
			return ok(user);
		} catch (Exception e) {
			logger.error("查询用户失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "查询用户失败.");
		}
	}
	
	@ApiIgnore
	@RequestMapping(value = "findByPage.do")
	public RestResult<Object> findByPage(Integer rows,Integer page,String name,String relationid,String type) {
		Page<UserEntity> pages = new Page<>(page, rows);
		UserEntity user=new UserEntity();
		user.setName(name);
		if("8".equals(type)) {
			user.setSchoolid(relationid);
		}else {
			user.setAreaid(relationid);
		}
		pages = qtuserService.findByPage(pages, user);
		return ok(pages);
	}
	
	@ApiIgnore
	@RequestMapping(value = "findByName.do")
	public RestResult<Object> findByName(String type,String name) {
		boolean findByName = qtuserService.findByName(JyUtil.getQName(type)+name);
		return ok(findByName);
	}
	

}
