/**
 * @fileName:  ReportController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:32:57
 */ 
package com.xuanli.oepcms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.ReportService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
//@RestController
//@RequestMapping(value = "/report/")
public class ReportController extends BaseController{
	@Autowired
	ReportService reportService;
	/**
	 * @Description:  TODO 生成作业报告
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午9:40:32
	 */
	@RequestMapping(value = "generatorHomeworkReport.do", method = RequestMethod.PUT)
	public RestResult<String> generatorHomeworkReport(@RequestParam Long homeworkId) {
		if(null==homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择作业");
		}
//		UserEntity userEntity = getCurrentUser();
//		return reportService.generatorHomeworkReport(homeworkId,userEntity.getId());
		return null;
	}
	/**
	 * @Description:  TODO 获取作业报告信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午9:40:43
	 */
	@RequestMapping(value = "homeworkReport.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> homeworkReport(@RequestParam Long homeworkId) {
		if(null==homeworkId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择作业");
		}
		return reportService.homeworkReport(homeworkId);
	}
}
