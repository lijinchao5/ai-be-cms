package com.xuanli.oepcms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ReadArticle;
import com.xuanli.oepcms.entity.ReadSentence;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.ReadService;
import com.xuanli.oepcms.util.AliOSSUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: ReadManagerController  
 * @Description: 分级阅读管理
 * @author WangMeng  
 * @date 2018年2月6日  
 *
 */
@RestController()
@RequestMapping(value = "/read/")
public class ReadManagerController extends BaseController{
	
    @Autowired
    private ReadService service;
    
	@Autowired
	AliOSSUtil aliOSSUtil;
	

	@ApiOperation(value="查询分级阅读列表", notes="查询分级阅读列表")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "rows", value = "每页显示的条数", required = true, dataType = "String"),
         @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "String"),
         @ApiImplicitParam(name = "name", value = "文章名称", required = false, dataType = "String"),
         @ApiImplicitParam(name = "level", value = "级别（数据字典类型-ydjb）", required = false, dataType = "String"),
         @ApiImplicitParam(name = "type", value = "分类（数据字典类型-ydfl）", required = false, dataType = "String")
	})
	@RequestMapping(value = "findArticle.do", method = RequestMethod.POST)
	public RestResult<Page<ReadArticle>> findBooks(Integer rows, Integer page, String name, String level, String type) {
		
		ReadArticle entity = new ReadArticle();
		
		if(StringUtil.isNotEmpty(name)) entity.setName(name);
		if(StringUtil.isNotEmpty(level)) entity.setLevel(level);
		if(StringUtil.isNotEmpty(type)) entity.setType(type);
		
		Page<ReadArticle> pages = new Page<>(page, rows);
		pages = service.getEntityByPage(pages, entity);
		return ok(pages);
	}

	@ApiOperation(value="查询分级阅读", notes="查询分级阅读")
	@ApiImplicitParams({
         @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "int")
	})
	@RequestMapping(value = "findById.do", method = RequestMethod.POST)
	public RestResult<ReadArticle> findBooks(Long id) {
		if(id == null ) return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		return ok(service.getReadArticle(id));
	}
	
	/**
	 * 
	 * @Description: 新增阅读
	 * 
	 * @return	RestResult<String> 
	 * @author  WangMeng
	 */
	@ApiOperation(value="新增阅读", notes="新增阅读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "短文名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "wordNum", value = "单词数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "level", value = "级别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "分类", required = true, dataType = "String"),
            @ApiImplicitParam(name = "itemjson", value = "分段数据（json格式数组）", required = true, dataType = "String")
    })
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	public RestResult<String> insertbook(HttpServletRequest request, @RequestParam String name, @RequestParam Integer wordNum, @RequestParam String level,
			@RequestParam String type, @RequestParam String itemjson){

		try {
			
			if(StringUtils.isBlank(name)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			}
			if(wordNum == null) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			}
			if(StringUtils.isBlank(level)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			}
			if(StringUtils.isBlank(type)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			}
			if(StringUtils.isBlank(itemjson)) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
			}
			
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> files = new HashMap<>();
			List<ReadSentence> list = new ArrayList<>();
					
			logger.debug(itemjson);
			JSONArray array = JSON.parseArray(itemjson);
			for (int i = 0; i < array.size(); i++) {
				
				JSONObject obj = array.getJSONObject(i);
				
				String key = "audiopath"+obj.getString("id");
				MultipartFile file = multipartRequest.getFile(key);
				if(file == null || file.isEmpty()) throw new ServiceException("音频文件缺失");
				files.put(key, file);
				
				String sentenceCont = obj.getString("sentenceCont");
				String sentenceMean = obj.getString("sentenceMean");
				Integer orderNum = obj.getInteger("orderNum");
				Integer sentenceWordNum = obj.getInteger("wordNum");
				
				ReadSentence sentence = new ReadSentence();
				sentence.setSentenceCont(sentenceCont);
				sentence.setSentenceMean(sentenceMean);
				sentence.setOrderNum(orderNum);
				sentence.setWordNum(sentenceWordNum);
				sentence.setFileKey(key);
				sentence.setEnableFlag("T");
				list.add(sentence);
			}
			
			
			for (ReadSentence s : list) {
				MultipartFile file = files.get(s.getFileKey());
				String path = aliOSSUtil.uploadFile(file.getInputStream(), 
						"article" , "mp3");
				s.setAudioPath(path);
			}
			
			ReadArticle article = new ReadArticle();

			article.setName(name);
			article.setWordNum(wordNum);
			article.setType(type);
			article.setLevel(level);
			
			article.setCreateId(getCurrentUser().getId().intValue() + "");
			article.setCreateDate(new Date());
			article.setEnableFlag("T");
			
			article.setList(list);
			
			int result = service.saveArticle(article);
			
			if (result > 0) {
				return ok("增加教材成功");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "新增阅读失败.");
			}
			
		}catch (Exception e) {
			logger.error("新增阅读失败!", e);
			e.printStackTrace();
			if(e instanceof ServiceException) return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
			return failed(ExceptionCode.UNKNOW_CODE, "新增阅读失败.", e.getMessage());
		}
	}

	@ApiOperation(value="短文内容", notes="短文内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "articleId", value = "id", required = true, dataType = "Long"),
	})
	@RequestMapping(value = "articleInfo.do", method = RequestMethod.POST)
	public RestResult<ReadArticle> syncBookContent(Long articleId) {

		if(articleId == null) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(service.getReadArticle(articleId));
	}
	
}
