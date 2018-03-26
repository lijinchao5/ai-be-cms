/**
 * @fileName:  ClasController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月16日 下午3:08:56
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.service.ClasService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu 
 */
//@RestController()
//@RequestMapping(value = "/class/")
public class ClassController extends BaseController {
	@Autowired
	ClasService clasService;

	@ApiOperation(value="创建班级", notes="增加班级方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "班级名称", required = true, dataType = "String")
    })
	@RequestMapping(value = "addClass.do", method = RequestMethod.POST)
	public RestResult<String> addClass(@RequestParam String grade,@RequestParam String name){
		if(StringUtil.isEmpty(grade)||StringUtil.isEmpty(name)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"年级或班级名称不能为空");
		}
		ClasEntity clasEntity = new ClasEntity();
		clasEntity.setGrade(grade);
		clasEntity.setName(name);
		try {
//			Long userId = getCurrentUser().getId();
//			clasService.saveClas(clasEntity, userId);
			return ok("添加班级成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加班级异常,请联系管理员.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月17日 上午9:57:58
	 */
	@ApiOperation(value="删除班级", notes="删除班级方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级id", required = true, dataType = "Long"),
    })
	@RequestMapping(value = "deleteClass.do", method = RequestMethod.DELETE)
	public RestResult<String> deleteClass(@RequestParam Long classId){
		try {
			if(null==classId) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择班级");
			}
			//classId = getCurrentUser().getId();
			clasService.updateClas(classId);
			return ok("删除班级成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除班级异常,请联系管理员",e);
			return failed(ExceptionCode.UNKNOW_CODE,e.getMessage());
		}
	}
	
	
	@ApiOperation(value="获取教师班级列表", notes="获取教师班级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String")
    })
	@RequestMapping(value = "findClasByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findClasByPage(@RequestParam Integer rows, @RequestParam Integer page) {
		ClasEntity clasEntity = new ClasEntity();
		PageBean pageBean = initPageBean(page, rows);
//		Long userId = getCurrentUser().getId();
//		clasEntity.setCreateId(userId.longValue()+"");
		clasService.findClasByPage(clasEntity, pageBean);
		return ok(pageBean);
	}
}
