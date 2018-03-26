package com.xuanli.oepcms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.QuestionOptionEntity;
import com.xuanli.oepcms.entity.QuestionSubjectDetailEntity;
import com.xuanli.oepcms.entity.QuestionSubjectEntity;
import com.xuanli.oepcms.mapper.QuestionOptionEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.QuestionSubjectEntityMapper;
import com.xuanli.oepcms.plugins.Page;

/** 
 * 
 * @ClassName: QuestionManagerService  
 * @Description: 试题管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@Service
public class QuestionManagerService {
	
	@Autowired
	QuestionSubjectEntityMapper subjectDao;
	
	@Autowired
	QuestionSubjectDetailEntityMapper detailEntityDao;
	
	@Autowired
	QuestionOptionEntityMapper optionDao;
	
	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<QuestionSubjectEntity> getSubjectByPage(Page<QuestionSubjectEntity> page, QuestionSubjectEntity entity) {
		page.setRecords(subjectDao.getEntityByPage(page,entity));
		return page;
	}

	/**
	 * 
	 * @Description: 获取内容
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Map<String, Object> getPaperContent(Long subjectId) {
		
		Map<String, Object> map = new HashMap<>();
		
		QuestionSubjectDetailEntity sd = new QuestionSubjectDetailEntity();
		sd.setSubjectId(subjectId);
		map.put("details", detailEntityDao.getEntityByPage(Page.noPageBean(), sd));
		
		QuestionOptionEntity op = new QuestionOptionEntity();
		op.setSubjectId(subjectId);
		map.put("options", optionDao.getEntityByPage(Page.noPageBean(), op));
		
		return map;
	}
	

}
