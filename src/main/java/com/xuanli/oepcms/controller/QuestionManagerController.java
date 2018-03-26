package com.xuanli.oepcms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.QuestionSubjectEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.QuestionManagerService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: QuestionManagerController  
 * @Description: 试题管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@RestController()
@RequestMapping(value = "/questionManager/")
public class QuestionManagerController extends BaseController{
	
	@Autowired
	QuestionManagerService service;


	@ApiOperation(value="查询试题", notes="查询试题")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "type", value = "试题类型", required = false, dataType = "String"),
         @ApiImplicitParam(name = "gradeLevelId", value = "年级（数据字典类型-jcnj）", required = false, dataType = "String"),
         @ApiImplicitParam(name = "term", value = "学期", required = false, dataType = "String")
	})
	@RequestMapping(value = "findQuestions.do", method = RequestMethod.POST)
	public RestResult<Page<QuestionSubjectEntity>> findPapers(Integer rows,Integer page,Integer type, String gradeLevelId, String term) {
		
		QuestionSubjectEntity entity = new QuestionSubjectEntity();
		
		if(type != null) entity.setType(type);
		if(StringUtil.isNotEmpty(gradeLevelId)) entity.setGradeLevelId( Integer.parseInt(gradeLevelId) );
		if(StringUtil.isNotEmpty(term)) entity.setTerm( Integer.parseInt(term) );
		
		Page<QuestionSubjectEntity> pages = new Page<>(page, rows);
		pages = service.getSubjectByPage(pages, entity);
		return ok(pages);
	}

	@ApiOperation(value="试题内容", notes="试题内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "subjectId", value = "id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "questionContent.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> syncPaperContent(Long subjectId) {

		if(subjectId == null) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(service.getPaperContent(subjectId));
	}
	
	
}
