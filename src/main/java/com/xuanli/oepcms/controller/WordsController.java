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
import com.xuanli.oepcms.entity.WordsEntity;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.WordsService;
import com.xuanli.oepcms.util.AliOSSUtil;
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
@RequestMapping(value = "/words/")
public class WordsController extends BaseController{
	
    @Autowired
    private WordsService service;
    
	@Autowired
	AliOSSUtil aliOSSUtil;
	

	@ApiOperation(value="查询单词列表", notes="查询单词列表")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "wordSpell", value = "单词拼写", required = false, dataType = "String"),
         @ApiImplicitParam(name = "wordMean", value = "单词词义", required = false, dataType = "String"),
         @ApiImplicitParam(name = "type", value = "单词库（数据字典类型-dck）", required = false, dataType = "String")
	})
	@RequestMapping(value = "findWords.do", method = RequestMethod.POST)
	public RestResult<Page<WordsEntity>> findBooks(Integer rows,Integer page,String wordSpell,String wordMean ,String type) {
		
		WordsEntity entity = new WordsEntity();
		
		if(StringUtil.isNotEmpty(wordSpell)) entity.setWordSpell(wordSpell);
		if(StringUtil.isNotEmpty(wordMean)) entity.setWordMean(wordMean);
		if(StringUtil.isNotEmpty(type)) entity.setType(type);
		
		Page<WordsEntity> pages = new Page<>(page, rows);
		pages = service.getEntityByPage(pages, entity);
		return ok(pages);
	}


	@ApiOperation(value="查询单词", notes="查询单词")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "id", value = "单词ID", required = true, dataType = "int")
	})
	@RequestMapping(value = "findWordsById.do", method = RequestMethod.POST)
	public RestResult<List<WordsEntity>> findBooks(Long id) {
		
		WordsEntity entity = new WordsEntity();
		if(id == null ) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"单词id不能为空");
		entity.setId(id);
		Page<WordsEntity> pages = Page.noPageBean();
		pages = service.getEntityByPage(pages, entity);
		return ok(pages.getRecords());
	}
	

	/**
	 * 
	 * @Description: 重复验证
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="单词重复验证", notes="单词重复验证")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "wordSpell", value = "单词拼写", required = true, dataType = "String"),
        @ApiImplicitParam(name = "type", value = "单词库（数据字典类型-dck）", required = true, dataType = "String")
    })
	@RequestMapping(value = "repeatValid.do", method = RequestMethod.POST)
	public RestResult<String> repeatValid( @RequestParam String wordSpell, @RequestParam String type){
		try {
			if(StringUtil.isEmpty(wordSpell)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"单词拼写不能为空");
			}
			if(StringUtil.isEmpty(type)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"单词库不能为空");
			}
			
			WordsEntity entity = new WordsEntity();
			entity.setWordSpell(wordSpell);
			entity.setType(type);
			
			//验证是否重复
			int num = service.repeatValid(entity);
			if(num > 0) {
				return failed(ExceptionCode.REPEAT_ERROR_CODE, "该单词已在当前单词库存在，请勿重复添加.");
			}else {
				return ok("可以新增");
			}
			
		} catch (Exception e) {
			logger.error("重复验证失败", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "重复验证失败");
		}
	}
	

	/**
	 * 
	 * @Description: 单词新增修改
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="单词新增修改", notes="单词新增修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "单词id", required = false, dataType = "Long"),
        @ApiImplicitParam(name = "wordSpell", value = "单词拼写", required = true, dataType = "String"),
        @ApiImplicitParam(name = "wordMean", value = "单词词义", required = true, dataType = "String"),
        @ApiImplicitParam(name = "type", value = "单词库（数据字典类型-dck）", required = true, dataType = "String"),
        
        @ApiImplicitParam(name = "audioPathMFile", value = "男声", required = false, dataType = "File"),
        @ApiImplicitParam(name = "audioPathWFile", value = "女声", required = false, dataType = "File"),
        @ApiImplicitParam(name = "audioPathMUkFile", value = "男声英式", required = false, dataType = "File"),
        @ApiImplicitParam(name = "audioPathWUkFile", value = "女声英式", required = false, dataType = "File"),
        @ApiImplicitParam(name = "audioPathMUsFile", value = "男声美式", required = false, dataType = "File"),
        @ApiImplicitParam(name = "audioPathWUsFile", value = "女声美式", required = false, dataType = "File"),
        @ApiImplicitParam(name = "picturePathFile", value = "图片文件", required = false, dataType = "File")
    })
	@RequestMapping(value = "editWords.do", method = RequestMethod.POST)
	public RestResult<String> editWords( @RequestParam String wordSpell, @RequestParam String type
			, @RequestParam String wordMean, @RequestParam Long id
			
			, @RequestParam(value = "audioPathMFile", required = false) MultipartFile audioPathM
			, @RequestParam(value = "audioPathWFile", required = false) MultipartFile audioPathW
			, @RequestParam(value = "audioPathMUkFile", required = false) MultipartFile audioPathMUk
			, @RequestParam(value = "audioPathWUkFile", required = false) MultipartFile audioPathWUk
			, @RequestParam(value = "audioPathMUsFile", required = false) MultipartFile audioPathMUs
			, @RequestParam(value = "audioPathWUsFile", required = false) MultipartFile audioPathWUs
			, @RequestParam(value = "picturePathFile", required = false) MultipartFile picturePath){
		try {
			if(StringUtil.isEmpty(wordSpell)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"单词拼写不能为空");
			}
			if(StringUtil.isEmpty(type)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"单词库不能为空");
			}
			
			
			WordsEntity entity = new WordsEntity();
			entity.setWordSpell(wordSpell.replaceAll("\\s*", ""));
			entity.setType(type);
			entity.setWordMean(wordMean);
			entity.setEnableFlag("T");
			
			//男生
			if(audioPathM != null) {
				String path = aliOSSUtil.uploadFile(audioPathM.getInputStream(), 
						entity.getWordSpell() , "mp3");
				entity.setAudioPathM(path);
	    	}
			//女生
			if(audioPathW != null) {
				String path = aliOSSUtil.uploadFile(audioPathW.getInputStream(), 
						entity.getWordSpell() , "mp3");
				entity.setAudioPathW(path);
	    	}
			
			//更新或新增
			if(id != null) {
				entity.setId(id);
				entity.setUpdateId(getCurrentUser().getId().intValue() + "");
				entity.setUpdateDate(new Date());
				int num = service.updateWords(entity);
				if(num > 0) {
					return ok("操作成功");
				}else {
					return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
				}
			}else {
				//验证是否重复
				int num = service.repeatValid(entity);
				if(num > 0) {
					return failed(ExceptionCode.REPEAT_ERROR_CODE, "该单词已在当前单词库存在，请勿重复添加.");
				}else {
					num = service.saveWords(entity);
					entity.setCreateId(getCurrentUser().getId().intValue() + "");
					entity.setCreateDate(new Date());
					if(num > 0) {
						return ok("操作成功");
					}else {
						return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
					}
				}
			}
			
			
			
		} catch (Exception e) {
			logger.error("重复验证失败", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, "操作失败");
		}
	}
	

	
}
