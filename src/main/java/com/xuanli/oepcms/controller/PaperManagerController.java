package com.xuanli.oepcms.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.PaperManagerService;
import com.xuanli.oepcms.util.ResourceImportPaperUtil;
import com.xuanli.oepcms.util.ResourceImportUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: PaperManagerController  
 * @Description: 试卷管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@RestController()
@RequestMapping(value = "/paperManager/")
public class PaperManagerController extends BaseController{
	
	@Autowired
	PaperManagerService service;

	
	@ApiOperation(value="导入试卷", notes="导入试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookFile", value = "教材文件", required = true)
    })
	@RequestMapping(value = "import.do", method = RequestMethod.POST)
	public RestResult<String> importPaper( @RequestParam("bookFile") MultipartFile file, String paperProvince){
		//拼接文件路径
		String zipFolderPath = "";
		//拼接文件路径
		String filePath = "";
		
		try {
			
			if(StringUtils.isBlank(paperProvince)) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "必须选择省份");
			//生成文件名
			String fileName = System.currentTimeMillis() + "";
			//拼接文件路径
			zipFolderPath = request.getSession().getServletContext().getRealPath("/")+"upload"+File.separator+fileName;
			//拼接文件路径
			filePath = zipFolderPath+".zip";
			//存储文件
			ResourceImportUtil.saveFile(file, filePath);
			//解压文件
			ResourceImportUtil.unZip(filePath);
			//获取根目录
			String folderPath = ResourceImportPaperUtil.getfolderPath(zipFolderPath);
			//解析试卷到试题bean
			PaperEntity paperEntity = ResourceImportPaperUtil.readXlsx(folderPath);
			paperEntity.setPaperProvince(paperProvince);
			try {
				paperEntity.setCreateId((long)getCurrentUser().getId());
				paperEntity.setCreateDate(new Date());
				paperEntity.setEnableFlag("T");
			} catch (Exception e) {
				logger.error("导入试卷-->设置创建人失败!");
			}
			System.out.println(JSON.toJSONString(paperEntity));
			int result = service.savePaper(paperEntity);
//			int result = 1;
			if (result > 0) {
				return ok("导入成功");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "导入失败.");
			}
			
			
		} catch (Exception e) {
			logger.error("导入失败.", e);
			e.printStackTrace();
			if(e instanceof ServiceException) return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
			return failed(ExceptionCode.UNKNOW_CODE, "导入失败.", e.getMessage());
		}finally {
			try {
				if(StringUtil.isNotEmpty(filePath)) {
					File zip = new File(filePath);
					zip.delete();
				}
				if(StringUtil.isNotEmpty(zipFolderPath)) {
					File folder = new File(zipFolderPath);
					FileUtils.deleteDirectory(folder);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	

	@ApiOperation(value="查询试卷", notes="查询试卷")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "name", value = "试卷名称", required = false, dataType = "String"),
         @ApiImplicitParam(name = "questionType", value = "试卷类型", required = false, dataType = "String"),
         @ApiImplicitParam(name = "paperType", value = "试卷题型", required = false, dataType = "String"),
         @ApiImplicitParam(name = "gradeLevelId", value = "年级（数据字典类型-jcnj）", required = false, dataType = "String"),
         @ApiImplicitParam(name = "term", value = "学期", required = false, dataType = "String"),
         @ApiImplicitParam(name = "addressProvince", value = "省份", required = false, dataType = "String")
	})
	@RequestMapping(value = "findPapers.do", method = RequestMethod.POST)
	public RestResult<Page<PaperEntity>> findPapers(Integer rows,Integer page,String name,String questionType
			,String paperType, String gradeLevelId, String term, String paperProvince) {
		
		PaperEntity entity = new PaperEntity();
		
		if(StringUtil.isNotEmpty(name)) entity.setName(name);
		if(StringUtil.isNotEmpty(questionType)) entity.setQuestionType( Integer.parseInt(questionType) );
		if(StringUtil.isNotEmpty(paperType)) entity.setPaperType( Integer.parseInt(paperType) );
		if(StringUtil.isNotEmpty(gradeLevelId)) entity.setGradeLevelId( Integer.parseInt(gradeLevelId) );
		if(StringUtil.isNotEmpty(term)) entity.setTerm( Integer.parseInt(term) );
		if(StringUtil.isNotEmpty(paperProvince)) entity.setPaperProvince(paperProvince);
		
		Page<PaperEntity> pages = new Page<>(page, rows);
		pages = service.getPaperEntityByPage(pages, entity);
		return ok(pages);
	}


	@ApiOperation(value="查询试卷", notes="查询试卷")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
	})
	@RequestMapping(value = "findPaper.do", method = RequestMethod.POST)
	public RestResult<PaperEntity> findPaper(Long id) {
		
		PaperEntity entity = new PaperEntity();
		
		if(id == null) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		entity.setId(id);
		
		Page<PaperEntity> pages = Page.noPageBean();
		pages = service.getPaperEntityByPage(pages, entity);
		List<PaperEntity> records = pages.getRecords();
		
		if(records.size()>0) {
			return ok(records.get(0));
		}else {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"数据不存在");
		}
		
	}


	@ApiOperation(value="试卷内容", notes="试卷内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "paperId", value = "id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "paperContent.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> syncPaperContent(String paperId) {

		if(StringUtil.isEmpty(paperId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(service.getPaperContent(paperId));
	}
	
	

	@ApiOperation(value="查询试卷", notes="查询试卷")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
         @ApiImplicitParam(name = "name", value = "试卷名称", required = false, dataType = "String"),
         @ApiImplicitParam(name = "questionType", value = "试卷类型", required = false, dataType = "String"),
         @ApiImplicitParam(name = "paperType", value = "试卷题型", required = false, dataType = "String"),
         @ApiImplicitParam(name = "gradeLevelId", value = "年级（数据字典类型-jcnj）", required = false, dataType = "String"),
         @ApiImplicitParam(name = "term", value = "学期", required = false, dataType = "String"),
         @ApiImplicitParam(name = "addressProvince", value = "省份", required = false, dataType = "String")
	})
	@RequestMapping(value = "updatePaper.do", method = RequestMethod.POST)
	public RestResult<String> updatePaper(Long id,String name,String questionType
			,String paperType, String gradeLevelId, String term, String paperProvince) {
		
		try {

			PaperEntity entity = new PaperEntity();
			if(id == null) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			entity.setId(id);
			
			if(StringUtil.isNotEmpty(name)) entity.setName(name);
			if(StringUtil.isNotEmpty(questionType)) entity.setQuestionType( Integer.parseInt(questionType) );
			if(StringUtil.isNotEmpty(paperType)) entity.setPaperType( Integer.parseInt(paperType) );
			if(StringUtil.isNotEmpty(gradeLevelId)) entity.setGradeLevelId( Integer.parseInt(gradeLevelId) );
			if(StringUtil.isNotEmpty(term)) entity.setTerm( Integer.parseInt(term) );
			if(StringUtil.isNotEmpty(paperProvince)) entity.setPaperProvince(paperProvince);
			
			try {
				entity.setUpdateId((long)getCurrentUser().getId());
				entity.setUpdateDate(new Date());
				entity.setEnableFlag("T");
			} catch (Exception e) {
				logger.error("导入试卷-->设置创建人失败!");
			}
			
			int result = service.updatePaper(entity);
			if (result > 0) {
				return ok("操作成功");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "操作失败.");
			}
			
		} catch (Exception e) {
			logger.error("操作失败.", e);
			e.printStackTrace();
			if(e instanceof ServiceException) return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
			return failed(ExceptionCode.UNKNOW_CODE, "操作失败.", e.getMessage());
		}
		
	}

	
	
}
