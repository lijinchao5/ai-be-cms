package com.xuanli.oepcms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.entity.ReadArticle;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.service.BookManagerService;
import com.xuanli.oepcms.service.OtherLinkService;
import com.xuanli.oepcms.service.PaperManagerService;
import com.xuanli.oepcms.service.ReadService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: ResourceSyncController  
 * @Description: 数据同步接口
 * @author WangMeng  
 * @date 2018年2月6日  
 *
 */
@RestController()
@RequestMapping(value = "/resourceSync/")
public class ResourceSyncController extends BaseController{
	
	@Autowired
	BookManagerService bookManagerService;
	
	@Autowired
	PaperManagerService paperManagerService;

    @Autowired
    private ReadService readService;
    
    @Autowired
    private OtherLinkService linkService;

	@ApiOperation(value="数据同步-教材信息", notes="数据同步-教材信息")
	@ApiImplicitParams({
	})
	@RequestMapping(value = "syncBooks.do", method = RequestMethod.POST)
	public RestResult<List<BookEntity>> syncBooks() {
		
		BookEntity bookEntity = new BookEntity();
		Page<BookEntity> pages = Page.<BookEntity>noPageBean();
		bookEntity.setEnableFlag("all");
		pages = bookManagerService.getBookEntityByPage(Page.<BookEntity>noPageBean(), bookEntity);
		return ok(pages.getRecords());
	}

	@ApiOperation(value="数据同步-教材内容", notes="数据同步-教材内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "bookId", value = "id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "syncBookContent.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> syncBookContent(String bookId) {

		if(StringUtil.isEmpty(bookId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(bookManagerService.getBookContent(bookId));
	}
	
	@ApiOperation(value="数据同步-试卷信息", notes="数据同步-试卷信息")
	@ApiImplicitParams({
	})
	@RequestMapping(value = "syncPapers.do", method = RequestMethod.POST)
	public RestResult<List<PaperEntity>> syncPapers() {
		
		PaperEntity entity = new PaperEntity();
		Page<PaperEntity> pages = Page.<PaperEntity>noPageBean();
		entity.setEnableFlag("all");
		pages = paperManagerService.getPaperEntityByPage(Page.<PaperEntity>noPageBean(), entity);
		return ok(pages.getRecords());
	}


	@ApiOperation(value="数据同步-试卷内容", notes="数据同步-试卷内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "paperId", value = "id", required = true, dataType = "String"),
	})
	@RequestMapping(value = "syncPaperContent.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> syncPaperContent(String paperId) {

		if(StringUtil.isEmpty(paperId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(paperManagerService.getPaperContent(paperId));
	}
	

	@ApiOperation(value="数据同步-分级阅读", notes="数据同步-分级阅读")
	@ApiImplicitParams({
	})
	@RequestMapping(value = "syncReads.do", method = RequestMethod.POST)
	public RestResult<List<ReadArticle>> syncReads() {
		
		ReadArticle entity = new ReadArticle();
		Page<ReadArticle> pages = Page.<ReadArticle>noPageBean();
		entity.setEnableFlag("all");
		pages = readService.getEntityByPage(Page.<ReadArticle>noPageBean(), entity);
		return ok(pages.getRecords());
	}


	@ApiOperation(value="数据同步-阅读内容", notes="数据同步-阅读内容")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "readId", value = "id", required = true, dataType = "Long"),
	})
	@RequestMapping(value = "syncReadContent.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> syncReadContent(Long readId) {

		if(readId == null) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"参数不能为空");
		}
		
		return ok(readService.getReadArticleCont(readId));
	}
	

	@ApiOperation(value="查询外链列表", notes="查询外链列表")
	@ApiImplicitParams({
         @ApiImplicitParam(name = "type", value = "类型：1 专家推荐，2 合作院校", required = false, dataType = "String")
	})
	@RequestMapping(value = "syncLinks.do", method = RequestMethod.POST)
	public RestResult<List<OtherLinkEntity>> findBooks(String type) {
		
		OtherLinkEntity entity = new OtherLinkEntity();
		if(StringUtil.isNotEmpty(type)) entity.setType(type);
		Page<OtherLinkEntity> pages = Page.noPageBean();
		pages = linkService.getEntityByPage(pages, entity);
		return ok(pages.getRecords());
	}


}
