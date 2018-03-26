/**
 * @fileName:  ReportServiceTest.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月23日 下午3:02:17
 */ 
package com.xuanli.oepcms.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
public class ReportServiceTest extends BaseTest{
	@Autowired
	ReportService reportService;
	
	@Test
	public void getReportMsg() {
//		RestResult<Map<String, Object>> result= reportService.homeworkReport(1);
//		Map<String, Object> map = result.getResult();
//		System.out.println(JSON.toJSONString(map));
	}
}
