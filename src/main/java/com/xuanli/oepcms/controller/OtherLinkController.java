package com.xuanli.oepcms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.OtherLinkService;
import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: OtherLinkController  
 * @Description: 外链管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@RestController()
@RequestMapping(value = "/link/")
public class OtherLinkController extends BaseController{
	
    @Autowired
    private OtherLinkService service;
    
	@Autowired
	AliOSSUtil aliOSSUtil;
	

	@ApiOperation(value="查询外链列表", notes="查询外链列表")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "name", value = "信息名称", required = false, dataType = "String"),
         @ApiImplicitParam(name = "type", value = "类型：1 专家推荐，2 合作院校", required = false, dataType = "String")
	})
	@RequestMapping(value = "findLinks.do", method = RequestMethod.POST)
	public RestResult<Page<OtherLinkEntity>> findBooks(Integer rows, Integer page, String name, String type) {
		
		OtherLinkEntity entity = new OtherLinkEntity();
		
		if(StringUtil.isNotEmpty(name)) entity.setName(name);
		if(StringUtil.isNotEmpty(type)) entity.setType(type);
		
		Page<OtherLinkEntity> pages = new Page<>(page, rows);
		pages = service.getEntityByPage(pages, entity);
		return ok(pages);
	}


	@ApiOperation(value="查询单条记录", notes="查询单条记录")
	@ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "int")
	})
	@RequestMapping(value = "findLinkById.do", method = RequestMethod.POST)
	public RestResult<List<OtherLinkEntity>> findBooks(Integer id) {
		
		OtherLinkEntity entity = new OtherLinkEntity();
		if(id == null ) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"id不能为空");
		entity.setId(id);
		Page<OtherLinkEntity> pages = Page.noPageBean();
		pages = service.getEntityByPage(pages, entity);
		return ok(pages.getRecords());
	}
	
	/**
	 * 
	 * @Description: 新增修改
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="新增修改", notes="新增修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "单词id", required = false, dataType = "Long"),
        @ApiImplicitParam(name = "wordSpell", value = "单词拼写", required = true, dataType = "String"),
        @ApiImplicitParam(name = "wordMean", value = "单词词义", required = true, dataType = "String"),
        @ApiImplicitParam(name = "type", value = "类型：1 专家推荐，2 合作院校", required = true, dataType = "String"),
        @ApiImplicitParam(name = "pic1", value = "图片文件", required = false, dataType = "File"),
        @ApiImplicitParam(name = "pic2", value = "图片文件", required = false, dataType = "File"),
        @ApiImplicitParam(name = "pic3", value = "图片文件", required = false, dataType = "File"),
        @ApiImplicitParam(name = "link1", value = "链接", required = false, dataType = "String"),
        @ApiImplicitParam(name = "link2", value = "链接", required = false, dataType = "String"),
        @ApiImplicitParam(name = "link3", value = "链接", required = false, dataType = "String"),
        @ApiImplicitParam(name = "desp1", value = "信息", required = false, dataType = "String"),
        @ApiImplicitParam(name = "desp2", value = "信息", required = false, dataType = "String"),
        @ApiImplicitParam(name = "desp3", value = "信息", required = false, dataType = "String")
    })
	@RequestMapping(value = "editLink.do", method = RequestMethod.POST)
	public RestResult<String> editWords( @RequestParam Integer orderby, @RequestParam String type
			, @RequestParam String name, @RequestParam Integer id
			, @RequestParam String desp1, @RequestParam String desp2
			, @RequestParam String desp3, @RequestParam String link1
			, @RequestParam String link2, @RequestParam String link3
			, @RequestParam(value = "pic1", required = false) MultipartFile pic1
			, @RequestParam(value = "pic2", required = false) MultipartFile pic2
			, @RequestParam(value = "pic3", required = false) MultipartFile pic3){
		try {
			if(StringUtil.isEmpty(name)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"信息名称不能为空");
			}
			if(StringUtil.isEmpty(type)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"类型不能为空");
			}
			
			OtherLinkEntity entity = new OtherLinkEntity();
			entity.setName(name);
			entity.setType(type);
			entity.setOrderby(orderby);
			entity.setDesp1(desp1);
			entity.setDesp2(desp2);
			entity.setDesp3(desp3);
			entity.setLink1(link1);
			entity.setLink2(link2);
			entity.setLink3(link3);
			entity.setEnableFlag("T");
			
			//图片文件
			if(pic1 != null) {
				String path = aliOSSUtil.uploadFile(pic1.getInputStream(), 
						"link" , "jpg");
				entity.setPic1(path);
	    	}
			//图片文件
			if(pic2 != null) {
				String path = aliOSSUtil.uploadFile(pic1.getInputStream(), 
						"link" , "jpg");
				entity.setPic2(path);
	    	}
			//图片文件
			if(pic3 != null) {
				String path = aliOSSUtil.uploadFile(pic1.getInputStream(), 
						"link" , "jpg");
				entity.setPic3(path);
	    	}
			
			//更新或新增
			if(id != null) {
				entity.setId(id);
				entity.setUpdateId(getCurrentUser().getId().intValue() + "");
				entity.setUpdateDate(new Date());
				int num = service.updateLink(entity);
				if(num > 0) {
					return ok("操作成功");
				}else {
					return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
				}
			}else {
				int num = service.saveLink(entity);
				entity.setCreateId(getCurrentUser().getId().intValue() + "");
				entity.setCreateDate(new Date());
				if(num > 0) {
					return ok("操作成功");
				}else {
					return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
				}
			}
			
			
			
		} catch (Exception e) {
			logger.error("操作失败", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
		}
	}
	

	/**
	 * 
	 * @Description: 启用禁用
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="启用禁用", notes="启用禁用")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "Integer"),
    })
	@RequestMapping(value = "delLink.do", method = RequestMethod.POST)
	public RestResult<String> delLink(Integer id, String status){
		try {

			OtherLinkEntity entity = new OtherLinkEntity();
			if(id == null ) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"id不能为空");
			entity.setId(id);
			entity.setUpdateId(getCurrentUser().getId().intValue() + "");
			entity.setUpdateDate(new Date());
			if("1".equals(status))entity.setEnableFlag("T");
			else entity.setEnableFlag("F");
			int num = service.updateLink(entity);
			if(num > 0) {
				return ok("操作成功");
			}else {
				return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
			}
		} catch (Exception e) {
			logger.error("操作失败", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
		}
	}
	

	
}
