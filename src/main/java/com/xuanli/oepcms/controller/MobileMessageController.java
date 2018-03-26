/**
 * @fileName:  MobileMessage.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午3:48:51
 */
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.MobileMessageService;
import com.xuanli.oepcms.util.RanNumUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author QiaoYu
 */
//@RestController
//@RequestMapping(value = "/mobileMessage/")
public class MobileMessageController extends BaseController {
	@Autowired
	MobileMessageService mobileMessageService;
	@Autowired
	SessionUtil sessionUtil;
	/**
	 * @Description: TODO 注册
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午3:56:32
	 */
	@ApiOperation(value="发送手机短信", notes="注册时用户发送手机短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomStr", value = "手机短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomKey", value = "随机验证码关键key", required = true, dataType = "String")
    })
	@RequestMapping(value = "registMsg.do", method = RequestMethod.GET)
	public RestResult<String> registMsg(@RequestParam String mobile, @RequestParam String randomStr,@RequestParam String randomKey) {
		if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(sessionUtil.getMobileRandomNum(randomKey))) {
			if (!StringUtil.isMobile(mobile)) {
				return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码错误.");
			}
			try {
				String randomNum = RanNumUtil.createRandomNum(6);
				String result = mobileMessageService.registMsg(mobile, randomNum);
				if (StringUtil.isEmpty(result) || result.equals("1")) {
					// 发送短信成功
					sessionUtil.setMobileMessageRandomNum(randomKey, randomNum);
					return ok("发送短信成功!");
				} else if (result.equals("2")) {
					// 手机号码已经存在
					return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码已经存在.");
				} else {
					return failed(ExceptionCode.UNKNOW_CODE, "发送短信未知错误.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("发送短信异常.", e);
				return failed(ExceptionCode.SENDMSG_ERROR_CODE, "发送短信异常.");
			}
		} else {
			logger.error("发送短信--->验证码错误.");
			return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "验证码错误.");
		}
	}

	/**
	 * @Description: TODO 忘记密码
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午3:56:23
	 */
	@ApiOperation(value="忘记密码", notes="忘记密码时用户发送手机短信验证码重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomStr", value = "手机短信验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomKey", value = "随机验证码关键key", required = true, dataType = "String")
    })
	@RequestMapping(value = "forgetPassword.do", method = RequestMethod.GET)
	public RestResult<String> forgetPassword(@RequestParam String mobile, @RequestParam String randomStr,@RequestParam String randomKey) {
		if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(sessionUtil.getMobileRandomNum(randomKey))) {
			if (!StringUtil.isMobile(mobile)) {
				return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码错误.");
			}
			try {
				String randomNum = RanNumUtil.createRandomNum(6);
				String result = mobileMessageService.forgetPassword(mobile, randomNum);
				if (StringUtil.isEmpty(result) || result.equals("1")) {
					// 发送短信成功
					sessionUtil.setMobileMessageRandomNum(getTokenId(), randomNum);
					return ok("发送短信成功!");
				} else if (result.equals("2")) {
					// 手机号码已经存在
					return failed(ExceptionCode.MOBILE_ERROR_CODE, "手机号码不存在.");
				} else {
					return failed(ExceptionCode.UNKNOW_CODE, "发送短信未知错误.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("发送短信异常.", e);
				return failed(ExceptionCode.SENDMSG_ERROR_CODE, "发送短信异常.");
			}
		} else {
			logger.error("发送短信--->验证码错误.");
			return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "验证码错误.");
		}
	}

	/**
	 * @Description: TODO 使用手机号登陆
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午3:56:14
	 */
	@ApiIgnore
	@RequestMapping(value = "loginMsg.do", method = RequestMethod.GET)
	public RestResult<String> loginMsg(@RequestParam String mobile, @RequestParam String randomStr) {
		return null;
	}
}
