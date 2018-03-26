package com.xuanli.oepcms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.AreaUseEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.AreaUseService;
import com.xuanli.oepcms.util.JyUtil;
import com.xuanli.oepcms.vo.RestResult;
/*
 * 区县时间的管理
 * 
 */
@RestController()
@RequestMapping(value = "/area/")
public class AreaUseController extends BaseController{
	@Autowired
	AreaUseService areaUseService;
	/*
	 * 查询区县的分页
	 */
	@RequestMapping(value = "findByPageqx.do")
	public RestResult<Page<AreaUseEntity>> findByPageqx(Integer rows,Integer page,String state,String addressProvince,String addressCity,String addressArea) {
		Page<AreaUseEntity> pages = new Page<>(page, rows);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("addressProvince", addressProvince);
		map.put("state", state);
		map.put("addressCity", addressCity);
		map.put("addressArea", addressArea);
		pages = areaUseService.findByPageqx(pages, map);
		return ok(pages);
	}
	
	/*
	 * 查询城市的分页
	 * 
	 */
	@RequestMapping(value = "findByPagecs.do")
	public RestResult<Page<AreaUseEntity>> findByPagecs(Integer rows,Integer page,String state,String addressProvince,String addressCity,String addressArea) {
		Page<AreaUseEntity> pages = new Page<>(page, rows);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("addressProvince", addressProvince);
		map.put("state", state);
		map.put("addressCity", addressCity);
		map.put("addressArea", addressArea);
		pages = areaUseService.findByPagecs(pages, map);
		return ok(pages);
	}
	/*
	 * 查询省份的分页
	 * 
	 */
	@RequestMapping(value = "findByPagesf.do")
	public RestResult<Page<AreaUseEntity>> findByPagesf(Integer rows,Integer page,String state,String addressProvince,String addressCity,String addressArea) {
		Page<AreaUseEntity> pages = new Page<>(page, rows);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("addressProvince", addressProvince);
		map.put("state", state);
		map.put("addressCity", addressCity);
		map.put("addressArea", addressArea);
		pages = areaUseService.findByPagesf(pages, map);
		return ok(pages);
	}
	
	/*
	 * 更新时间
	 * 
	 */
	@RequestMapping(value = "updateareaUse.do")
	public RestResult<String> updateareaUse(String id, String startdate,String enddate) {
		try {
			AreaUseEntity areaUseEntity=new AreaUseEntity();
			areaUseEntity.setId(id);
			areaUseEntity.setStartdate(JyUtil.getDate(startdate+" 00:00:00"));
			areaUseEntity.setEnddate(JyUtil.getDate(enddate+" 23:59:59"));
			areaUseEntity.setUpdatedate(new Date());
			areaUseEntity.setUpdateid(getCurrentUser().getId().intValue());
			int result = areaUseService.updateareaUse(areaUseEntity);
			if (result > 0) {
				return ok("修改成功");
			} else {
				return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改失败.");
			}
		} catch (Exception e) {
			logger.error("增加用户失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.ADDUSER_ERROR_CODE, "修改失败.");
		}
	}
	/*
	 * 查询单个区域使用时间
	 */
	@RequestMapping(value = "findByAreaUseId.do")
	public RestResult<AreaUseEntity> findByAreaUseId(String id) {
		AreaUseEntity areaUseEntity = areaUseService.findById(id);
		return ok(areaUseEntity);
	}
	
}
