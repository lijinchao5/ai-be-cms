package com.xuanli.oepcms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.BookVersionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.BookManagerService;
import com.xuanli.oepcms.util.ResourceImportBookUtil;
import com.xuanli.oepcms.util.ResourceImportUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: BookManagerController  
 * @Description: 教材管理
 * @author WangMeng  
 * @date 2018年2月6日  
 *
 */
@RestController()
@RequestMapping(value = "/bookManager/")
public class BookManagerController extends BaseController{
	@Autowired
	BookManagerService bookManagerService;

	/**
	 * 
	 * @Description: 新增教材
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="新增教材", notes="新增教材")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "教材名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "bookVersion", value = "教材版本（数据字典类型-jcbb）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "grade", value = "年级（数据字典类型-jcnj）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bookVolume", value = "教材册别（数据字典类型-cb）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bookFile", value = "教材文件", required = true, dataType = "File")
    })
	@RequestMapping(value = "import.do", method = RequestMethod.POST)
	public RestResult<String> insertbook(@RequestParam String name, @RequestParam String bookVersion, @RequestParam String grade,
			@RequestParam String bookVolume, @RequestParam("bookFile") MultipartFile file){
		//拼接文件路径
		String zipFolderPath = "";
		//拼接文件路径
		String filePath = "";
		
		try {
//			if(StringUtil.isEmpty(name)) {
//				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"教材名称不能为空");
//			}
			if(StringUtil.isEmpty(bookVersion)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"教材版本不能为空");
			}
			if(StringUtil.isEmpty(grade)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"年级不能为空");
			}
			if(StringUtil.isEmpty(bookVolume)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"教材册别不能为空");
			}
			BookEntity bookEntity = new BookEntity();
			bookEntity.setName(name);
			bookEntity.setBookVersion(bookVersion);
			bookEntity.setGrade(grade);
			bookEntity.setBookVolume(bookVolume);
			bookEntity.setCreateDate(new Date());
			bookEntity.setEnableFlag("T");
			
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
			String folderPath = ResourceImportBookUtil.getfolderPath(zipFolderPath);
			//根据word文件获取章节树
			List<UnitEntity> units = ResourceImportBookUtil.getUnitTreeByWord(folderPath);
			//填充基本单词对话到章节树对象
			List<UnitEntity> list = ResourceImportBookUtil.getSectionDetailByFolder(folderPath, units);
			//单元列表放入教材对象
			bookEntity.setList(list);
			try {
				bookEntity.setCreateId(getCurrentUser().getId().intValue() + "");
				bookEntity.setCreateDate(new Date());
			} catch (Exception e) {
				logger.error("保存教材-->设置创建人失败!");
			}
			
			int result = bookManagerService.saveBook(bookEntity);
			if (result > 0) {
				return ok("增加教材成功");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "增加教材失败.");
			}
			
			
		} catch (Exception e) {
			logger.error("增加教材失败!", e);
			e.printStackTrace();
			if(e instanceof ServiceException) return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
			return failed(ExceptionCode.UNKNOW_CODE, "增加教材失败.", e.getMessage());
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
	

	/**
	 * 
	 * @Description: 教材重复验证
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="教材重复验证", notes="教材重复验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookVersion", value = "教材版本（数据字典类型-jcbb）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "grade", value = "年级（数据字典类型-jcnj）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bookVolume", value = "教材册别（数据字典类型-cb）", required = true, dataType = "String"),
    })
	@RequestMapping(value = "repeatValid.do", method = RequestMethod.POST)
	public RestResult<String> repeatValid( @RequestParam String bookVersion, @RequestParam String grade,
			@RequestParam String bookVolume){
		try {
			if(StringUtil.isEmpty(bookVersion)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"教材版本不能为空");
			}
			if(StringUtil.isEmpty(grade)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"年级不能为空");
			}
			if(StringUtil.isEmpty(bookVolume)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"教材册别不能为空");
			}
			BookEntity bookEntity = new BookEntity();
			bookEntity.setBookVersion(bookVersion);
			bookEntity.setGrade(grade);
			bookEntity.setBookVolume(bookVolume);
			
			//验证是否重复
			int num = bookManagerService.repeatValid(bookEntity);
			if(num > 0) {
				return failed(ExceptionCode.REPEAT_ERROR_CODE, "该教材已存在，请勿重复导入.");
			}else {
				return ok("可以新增");
			}
			
		} catch (Exception e) {
			logger.error("教材重复验证失败", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "教材重复验证失败");
		}
	}
	

	@ApiOperation(value="查询教材", notes="查询教材")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "name", value = "教材名称", required = false, dataType = "String"),
         @ApiImplicitParam(name = "bookVersion", value = "教材版本（数据字典类型-jcbb）", required = false, dataType = "String"),
         @ApiImplicitParam(name = "grade", value = "年级（数据字典类型-jcnj）", required = false, dataType = "String"),
         @ApiImplicitParam(name = "bookVolume", value = "教材册别（数据字典类型-cb）", required = false, dataType = "String")
	})
	@RequestMapping(value = "findBooks.do", method = RequestMethod.POST)
	public RestResult<Page<BookEntity>> findBooks(Integer rows,Integer page,String name,String grade
			,String bookVersion,String bookVolume) {
		
		
		BookEntity bookEntity = new BookEntity();
		
		if(StringUtil.isNotEmpty(name)) bookEntity.setName(name);
		if(StringUtil.isNotEmpty(grade)) bookEntity.setGrade(grade);
		if(StringUtil.isNotEmpty(bookVersion)) bookEntity.setBookVersion(bookVersion);
		if(StringUtil.isNotEmpty(bookVolume)) bookEntity.setBookVolume(bookVolume);
		
		Page<BookEntity> pages = new Page<>(page, rows);
		pages = bookManagerService.getBookEntityByPage(pages, bookEntity);
		return ok(pages);
		
	}
	
	@ApiOperation(value="教材内容", notes="教材内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "bookId", value = "id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "bookContent.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> syncBookContent(String bookId) {

		if(StringUtil.isEmpty(bookId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(bookManagerService.getBookContent(bookId));
	}
	

	@ApiOperation(value="查询教材版本配置", notes="查询教材版本配置")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "name", value = "教材版本名称", required = false, dataType = "String"),
	})
	@RequestMapping(value = "findBookVersions.do", method = RequestMethod.POST)
	public RestResult<Page<BookVersionEntity>> findBookVersions(Integer rows,Integer page,String name) {
		
		
		BookVersionEntity bookEntity = new BookVersionEntity();
		
		if(StringUtil.isNotEmpty(name)) bookEntity.setName(name);
		
		Page<BookVersionEntity> pages = new Page<>(page, rows);
		pages = bookManagerService.getBookVersionByPage(pages, bookEntity);
		return ok(pages);
		
	}
	
	@ApiOperation(value="查询所选年级的教材版本", notes="查询所选年级的教材版本")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "grade", value = "适用年级", required = true, dataType = "String"),
	})
	@RequestMapping(value = "findBookVersionsByGrade.do", method = RequestMethod.POST)
	public RestResult<List<BookVersionEntity>> findBookVersionsByGrade(String grade) {

		if(StringUtil.isEmpty(grade)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		BookVersionEntity bookEntity = new BookVersionEntity();
		bookEntity.setGrade(grade);
		Page<BookVersionEntity> pages = new Page<>(1, 10000);
		pages = bookManagerService.getBookVersionByPage(pages, bookEntity);
		return ok(pages.getRecords());
		
	}
	@ApiOperation(value="修改教材版本配置", notes="修改教材版本配置")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "bookId", value = "教材版本", required = true, dataType = "String"),
         @ApiImplicitParam(name = "grade", value = "适用年级", required = false, dataType = "String"),
	})
	@RequestMapping(value = "updateBookVersions.do", method = RequestMethod.POST)
	public RestResult<String> updateBookVersions(String version_val, String grade) {
		
		try {

			if(StringUtil.isEmpty(version_val)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			}
			
			List<BookVersionEntity> list = new ArrayList<>();
			
			if(StringUtil.isNotEmpty(grade)) {
				String[] split = grade.split(",");
				for (int i = 0; i < split.length; i++) {
					BookVersionEntity v = new BookVersionEntity();
					v.setNameVal(version_val);
					v.setGrade(split[i]);
					list.add(v);
				}
			}
			
			int result = bookManagerService.updateBookVersion(list,version_val);
			if (result > 0) {
				return ok("编辑成功");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "编辑失败.");
			}
		} catch (Exception e) {
			logger.error("编辑失败!", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "编辑失败.");
		}
		
		
	}
	
}
