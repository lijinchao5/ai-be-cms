package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.AreaService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/*
 * 查询区县下拉
 * 
 */
@RestController()
@RequestMapping(value = "/area/")
public class AreaController extends BaseController{
	@Autowired
	AreaService areaService;
	
	
	@ApiOperation(value="查询区域", notes="根据id查询区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "类型", required = true, dataType = "String"),
    })
	@RequestMapping(value = "findById.do")
	public RestResult<Object> findById(String id){
		if (StringUtil.isEmpty(id)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "id不能为空");
		}
		return ok(areaService.findById(id));
	}
	
	
	@ApiOperation(value="查询区域", notes="根据父id查询区域")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentid", value = "类型", required = true, dataType = "String"),
	})
	@RequestMapping(value = "findByParentid.do")
	public RestResult<Object> findByParentid(String parentid){
		if (StringUtil.isEmpty(parentid)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "id不能为空");
		}
		return ok(areaService.findByParentid(parentid));
	}
	
}
