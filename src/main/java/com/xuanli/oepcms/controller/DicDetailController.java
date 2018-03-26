/**
 * @fileName:  DicController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月30日 上午9:40:49
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.DicDetailService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 *  
 */
@RestController()
@RequestMapping(value = "/dicdetail/")
public class DicDetailController extends BaseController{
	@Autowired
	DicDetailService dicDetailService;
	
	
	/*
	 * 增加主字典表
	 * 
	 */
	
	@ApiOperation(value="增加字典", notes="增加字典")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "dic_id", value = "字典主表id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "字典名称", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name_val", value = "字典简介", required = true, dataType = "String"),
		@ApiImplicitParam(name = "orderby", value = "排序", required = true, dataType = "String"),
	})
	@RequestMapping(value = "saveDic.do", method = RequestMethod.POST)
	public RestResult<String> saveDic(Integer dic_id, String name, String name_val,String orderby) {
		try {
			if (null == dic_id||null == name||null == name_val) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "增加字典参数不能为空");
			}
			DicDetailEntity dicDetailEntity=new DicDetailEntity();
			dicDetailEntity.setDicId(dic_id);
			dicDetailEntity.setNameVal(name_val);
			dicDetailEntity.setName(name);
			dicDetailEntity.setEnableFlag("T");
			int orderbyint=0;
			try {
				orderbyint=Integer.parseInt(orderby);
			} catch (Exception e) {
			}
			dicDetailEntity.setOrderby(orderbyint);
			
			int result = dicDetailService.saveDic(dicDetailEntity);
			if (result > 0) {
				return ok("增加字典成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加字典失败.");
			}
		} catch (Exception e) {
			logger.error("增加字典失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加字典失败.");
		}
	}
	
	/*
	 * 修改主字典表
	 * 
	 */
	
	@ApiOperation(value="修改字典类型", notes="修改字典类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "字典名称", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name_val", value = "字典类型", required = true, dataType = "String"),
		@ApiImplicitParam(name = "orderby", value = "排序", required = true, dataType = "String"),
	})
	@RequestMapping(value = "updateDic.do", method = RequestMethod.POST)
	public RestResult<String> updateDic(Integer id, String name, String name_val,String orderby) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "修改字典参数不能为空");
			}
			DicDetailEntity dicDetailEntity=new DicDetailEntity();
			dicDetailEntity.setNameVal(name_val);
			dicDetailEntity.setName(name);
			dicDetailEntity.setId(id);
			int orderbyint=0;
			try {
				orderbyint=Integer.parseInt(orderby);
			} catch (Exception e) {
			}
			dicDetailEntity.setOrderby(orderbyint);
			
			int result = dicDetailService.updateDic(dicDetailEntity);
			if (result > 0) {
				return ok("修改字典成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改字典失败.");
			}
		} catch (Exception e) {
			logger.error("修改字典失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改字典失败.");
		}
	}
	
	/*
	 * 删除主字典表
	 * 
	 */
	
	@ApiOperation(value="删除主字典类型", notes="删除主字典类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "delDic.do", method = RequestMethod.POST)
	public RestResult<String> delDic(Integer id) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "删除字典参数不能为空");
			}
			DicDetailEntity dicDetailEntity=new DicDetailEntity();
			dicDetailEntity.setId(id);
			int result = dicDetailService.delDic(dicDetailEntity);
			if (result > 0) {
				return ok("删除字典成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "删除字典失败.");
			}
		} catch (Exception e) {
			logger.error("删除字典失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "删除字典失败.");
		}
	}
	
	/*
	 * 根据id查询主字典表
	 * 
	 */
	
	@ApiOperation(value="根据id查询字典表", notes="根据id查询字典表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "findById.do", method = RequestMethod.POST)
	public RestResult<Object> findById(Integer id) {
		if (null == id) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "删除字典参数不能为空");
		}
		return ok(dicDetailService.findById(id));
	}
	
	
	@ApiOperation(value="查询字典表", notes="查询字典表方法")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
        @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
		@ApiImplicitParam(name = "dic_id", value = "字典简拼", required = true, dataType = "String"),
	})
	@RequestMapping(value = "finddic.do", method = RequestMethod.POST)
	public RestResult<Page<DicDetailEntity>> finddic(Integer rows,Integer page,Integer dic_id) {
		Page<DicDetailEntity> pages = new Page<>(page, rows);
		DicDetailEntity dicDetailEntity=new DicDetailEntity();
		dicDetailEntity.setDicId(dic_id);
		pages = dicDetailService.findByPage(pages, dicDetailEntity);
		return ok(pages);
	}
	
	
	
}
