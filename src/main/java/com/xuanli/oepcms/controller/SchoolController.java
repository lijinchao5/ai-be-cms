package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.SchoolService;
import com.xuanli.oepcms.util.JyUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/*
 * 学校的查询 新增 修改 删除
 * 
 */
@RestController()
@RequestMapping(value = "/school/")
public class SchoolController extends BaseController{
	@Autowired
	SchoolService schoolService ;
	
	
	@ApiOperation(value="创建学校", notes="增加学校方法")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "学校名称", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressProvince", value = "学校省份id，根据区域数据获取", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressCity", value = "学校城市id，根据区域数据获取", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressArea", value = "学校区域id，根据区域数据获取", required = true, dataType = "String"),
	})
	@RequestMapping(value = "saveSchool.do", method = RequestMethod.POST)
	public RestResult<String> saveSchool( String name, String addressProvince, String addressCity,
			 String addressArea) {
		try {
			if (null == name||null == addressProvince||null == addressCity||null == addressArea) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "保存学校参数不能为空");
			}
			SchoolEntity school=new SchoolEntity();
			school.setName(name);
			school.setAddressArea(addressArea);
			school.setAddressCity(addressCity);
			school.setAddressProvince(addressProvince);
			try {
				school.setCreateId(getCurrentUser().getId().intValue() + "");
			} catch (Exception e) {
				logger.error("设置创建人失败!");
			}
			int result = schoolService.saveSchool(school);
			if (result > 0) {
				return ok("增加学校成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加学校失败.");
			}
		} catch (Exception e) {
			logger.error("增加学校失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "增加学校失败.");
		}
	}
	
	@ApiOperation(value="修改学校", notes="修改学校方法")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "学校id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "学校名称", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressProvince", value = "学校省份id，根据区域数据获取", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressCity", value = "学校城市id，根据区域数据获取", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressArea", value = "学校区域id，根据区域数据获取", required = true, dataType = "String"),
	})
	@RequestMapping(value = "updateSchool.do", method = RequestMethod.POST)
	public RestResult<String> updateSchool( String id, String name, String addressProvince, String addressCity,
			 String addressArea) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "修改学校参数不能为空");
			}
			SchoolEntity school=new SchoolEntity();
			school.setName(name);
			school.setAddressArea(addressArea);
			school.setAddressCity(addressCity);
			school.setAddressProvince(addressProvince);
			school.setId(Long.parseLong(id));
			try {
				school.setUpdateId(getCurrentUser().getId().intValue() );
			} catch (Exception e) {
				logger.error("设置创建人失败!");
			}
			int result = schoolService.updateSchool(school);
			if (result > 0) {
				return ok("修改学校成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改学校失败.");
			}
		} catch (Exception e) {
			logger.error("修改学校失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改学校失败.");
		}
	}
	@ApiOperation(value="修改学校起始结束时间", notes="修改学校起始结束时间")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "学校id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "startdate", value = "开始时间", required = true, dataType = "String"),
		@ApiImplicitParam(name = "enddate", value = "结束时间", required = true, dataType = "String"),
	})
	@RequestMapping(value = "updateSchooldate.do", method = RequestMethod.POST)
	public RestResult<String> updateSchooldate( String id, String startdate, String enddate) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "修改学校参数不能为空");
			}
			SchoolEntity school=new SchoolEntity();
			school.setStartdate(JyUtil.getDate(startdate+" 00:00:00"));
			school.setEnddate(JyUtil.getDate(enddate+" 23:59:59"));
			school.setId(Long.parseLong(id));
			try {
				school.setUpdateId(getCurrentUser().getId().intValue() );
			} catch (Exception e) {
				logger.error("设置创建人失败!");
			}
			int result = schoolService.updateSchool(school);
			if (result > 0) {
				return ok("修改学校成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改学校失败.");
			}
		} catch (Exception e) {
			logger.error("修改学校失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改学校失败.");
		}
	}
	
	@ApiOperation(value="删除学校", notes="删除学校方法")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "学校id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "deleteSchool.do", method = RequestMethod.POST)
	public RestResult<String> deleteSchool(String id) {
		try {
			if (null == id) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "修改学校参数不能为空");
			}
			SchoolEntity school=new SchoolEntity();
			school.setId(Long.parseLong(id));
			try {
				school.setUpdateId(getCurrentUser().getId().intValue() );
			} catch (Exception e) {
				logger.error("设置创建人失败!");
			}
			int result = schoolService.deleteSchool(school);
			if (result > 0) {
				return ok("删除学校成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "删除学校失败.");
			}
		} catch (Exception e) {
			logger.error("删除学校失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "删除学校失败.");
		}
	}
	
	
	@ApiOperation(value="查询单个学校", notes="根据id查询学校")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "学校id", required = true, dataType = "String"),
    })
	@RequestMapping(value = "findSchoolById.do", method = RequestMethod.POST)
	public RestResult<Object> findSchoolById(String id){
		if (StringUtil.isEmpty(id)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "id不能为空");
		}
		return ok(schoolService.findDicById(id));
	}
	
	@ApiOperation(value="查询学校", notes="查询学校方法")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
		@ApiImplicitParam(name = "name", value = "学校名称，支持模糊查询", required = true, dataType = "String"),
		@ApiImplicitParam(name = "schoolId", value = "学校ID", required = true, dataType = "String"),
		@ApiImplicitParam(name = "state", value = "状态", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressProvince", value = "省份", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressCity", value = "市", required = true, dataType = "String"),
		@ApiImplicitParam(name = "addressArea", value = "区县", required = true, dataType = "String"),
	})
	@RequestMapping(value = "findSchool.do")
	public RestResult<Page<SchoolEntity>> findSchool(Integer rows,Integer page,String name,String schoolId,String state,String addressProvince,String addressCity,String addressArea) {
		System.out.println(state);
		Page<SchoolEntity> pages = new Page<>(page, rows);
		SchoolEntity schoolEntity=new SchoolEntity();
		schoolEntity.setName(name);
		schoolEntity.setSchoolId(schoolId);
		schoolEntity.setState(state);
		schoolEntity.setAddressProvince(addressProvince);
		schoolEntity.setAddressCity(addressCity);
		schoolEntity.setAddressArea(addressArea);
		pages = schoolService.findByPage(pages, schoolEntity);
		return ok(pages);
	}
	
}
