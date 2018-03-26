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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.DicEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.DicService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu 
 */
@RestController()
@RequestMapping(value = "/dic/")
public class DicController extends BaseController{
	@Autowired
	DicService dicService;
	
	
	
	@ApiOperation(value="查询字典", notes="根据类型查询字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "String"),
    })
	@RequestMapping(value = "findDicByType.do", method = RequestMethod.POST)
	public RestResult<Object> findDicByType(@RequestParam String type){
		if (StringUtil.isEmpty(type)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "type不能为空");
		}
		return ok(dicService.findDicByType(type));
	}
	
	/*
	 * 增加主字典表
	 * 
	 */
	
	@ApiOperation(value="增加主字典类型", notes="增加主字典类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "字典简拼", required = true, dataType = "String"),
		@ApiImplicitParam(name = "type_name", value = "字典名称", required = true, dataType = "String"),
	})
	@RequestMapping(value = "saveDic.do", method = RequestMethod.POST)
	public RestResult<String> saveDic( String type, String type_name) {
		try {
			if (null == type||null == type_name) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "保存字典参数不能为空");
			}
			DicEntity dicEntity=new DicEntity();
			dicEntity.setType(type);
			dicEntity.setTypeName(type_name);
			int result = dicService.saveDic(dicEntity);
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
	
	@ApiOperation(value="增加主字典类型", notes="增加主字典类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "type", value = "字典简拼", required = true, dataType = "String"),
		@ApiImplicitParam(name = "type_name", value = "字典名称", required = true, dataType = "String"),
	})
	@RequestMapping(value = "updateDic.do", method = RequestMethod.POST)
	public RestResult<String> updateDic(Integer id, String type, String type_name) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "修改字典参数不能为空");
			}
			DicEntity dicEntity=new DicEntity();
			dicEntity.setId(id);
			dicEntity.setType(type);
			dicEntity.setTypeName(type_name);
			int result = dicService.updateDic(dicEntity);
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
			DicEntity dicEntity=new DicEntity();
			dicEntity.setId(id);
			int result = dicService.delDic(dicEntity);
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
	
	@ApiOperation(value="根据id查询主字典表", notes="根据id查询主字典表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "findById.do", method = RequestMethod.POST)
	public RestResult<Object> findById(Integer id) {
		if (null == id) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "删除字典参数不能为空");
		}
		return ok(dicService.findById(id));
	}
	
	
	@ApiOperation(value="查询主字典表", notes="查询主字典表方法")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
        @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
		@ApiImplicitParam(name = "type", value = "字典简拼", required = true, dataType = "String"),
		@ApiImplicitParam(name = "type_name", value = "字典名称，支持模糊查询", required = true, dataType = "String"),
	})
	@RequestMapping(value = "finddic.do", method = RequestMethod.POST)
	public RestResult<Page<DicEntity>> finddic(Integer rows,Integer page,String type,String type_name) {
		Page<DicEntity> pages = new Page<>(page, rows);
		DicEntity dicEntity = new DicEntity();
		dicEntity.setType(type);
		dicEntity.setTypeName(type_name);
		pages = dicService.findByPage(pages, dicEntity);
		return ok(pages);
	}
	
}
