package com.xuanli.oepcms.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.mapper.AreaEntityMapper;
import com.xuanli.oepcms.mapper.PaperEntityMapper;
import com.xuanli.oepcms.mapper.PaperOptionEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectDetailEntityMapper;
import com.xuanli.oepcms.mapper.PaperSubjectEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.AliOSSUtil;

/** 
 * 
 * @ClassName: PaperManagerService  
 * @Description: 试卷管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@Service
public class PaperManagerService {
	
	@Autowired
	PaperEntityMapper paperDao;
	
	@Autowired
	PaperSubjectEntityMapper paperSubjectDao;
	
	@Autowired
	PaperSubjectDetailEntityMapper detailEntityDao;
	
	@Autowired
	PaperOptionEntityMapper optionDao;
	
	@Autowired
	AreaEntityMapper areaDao;
	
	@Autowired
	AliOSSUtil aliOSSUtil;
	
	/**
	 * 
	 * @Description: 导入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	@Transactional
	public int savePaper(PaperEntity entity) throws Exception {

		paperDao.insertPaperEntity(entity);

		List<PaperSubjectEntity> subjectlist = entity.getList();
		for (PaperSubjectEntity ps : subjectlist) {
			
			//声音
        	String audioPath = ps.getAudio();
			System.out.println(audioPath);
        	//调用阿里云存储
        	if(StringUtils.isNotBlank(audioPath) && new File(audioPath).exists()) {
        		audioPath = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPath)), 
        				entity.getPaperNum() , "mp3");
        	}

			System.out.println(audioPath);
        	ps.setAudio(audioPath);
			ps.setPaperId(entity.getId());
			ps.setCreateId(entity.getCreateId());
			ps.setCreateDate(entity.getCreateDate());
			ps.setEnableFlag("T");
			paperSubjectDao.insertPaperSubjectEntity(ps);
			
			List<PaperSubjectDetailEntity> paperSubjectDetailList = ps.getList();
			for (PaperSubjectDetailEntity psd : paperSubjectDetailList) {

				Integer type = psd.getType();
				
    			//声音
            	String guideAudio = psd.getGuideAudio();
            	String questionAudio = psd.getQuestionAudio();
            	String pic = null;
				if(type == 3) pic = psd.getQuestion();
            	//调用阿里云存储
            	if(StringUtils.isNotBlank(guideAudio)) {
            		guideAudio = aliOSSUtil.uploadFile(new FileInputStream(new File(guideAudio)), 
            				entity.getPaperNum() , "mp3");
            	}
            	//调用阿里云存储
            	if(StringUtils.isNotBlank(questionAudio)) {
            		questionAudio = aliOSSUtil.uploadFile(new FileInputStream(new File(questionAudio)), 
            				entity.getPaperNum() , "mp3");
            	}
            	//调用阿里云存储
            	if(StringUtils.isNotBlank(pic)) {
            		pic = aliOSSUtil.uploadFile(new FileInputStream(new File(pic)), 
            				entity.getPaperNum() , "jpg");
            		psd.setQuestion(pic);
            	}
            	
            	//设置上传标志
				psd.setGuideAudio(guideAudio);
				psd.setQuestionAudio(questionAudio);
				
				psd.setSubjectId(ps.getId());
				psd.setCreateId(entity.getCreateId());
				psd.setCreateDate(entity.getCreateDate());
				psd.setEnableFlag("T");
				detailEntityDao.insertPaperSubjectDetailEntity(psd);
				
				PaperOptionEntity op = psd.getOption();
				if(op!=null) {
					op.setDetailId(psd.getId());
					op.setCreateId(entity.getCreateId());
					op.setCreateDate(entity.getCreateDate());
					op.setEnableFlag("T");
					optionDao.insertPaperOptionEntity(op);
				}
				
			}
		}
		return 1;
	}
	
	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<PaperEntity> getPaperEntityByPage(Page<PaperEntity> page, PaperEntity entity) {
		page.setRecords(paperDao.getEntityByPage(page,entity));
		return page;
	}

	/**
	 * 
	 * @Description: 导入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	@Transactional
	public int updatePaper(PaperEntity entity) throws Exception {
		
		return paperDao.updatePaperEntity(entity);
		
	}

	/**
	 * 
	 * @Description: 获取试卷所有内容
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Map<String, Object> getPaperContent(String paperId) {
		
		Map<String, Object> map = new HashMap<>();
		
		PaperSubjectEntity s = new PaperSubjectEntity();
		s.setPaperId(Long.parseLong(paperId));
		map.put("subjects", paperSubjectDao.getEntityByPage(Page.noPageBean(), s));
		
		PaperSubjectDetailEntity sd = new PaperSubjectDetailEntity();
		sd.setPaperId(paperId);
		map.put("details", detailEntityDao.getEntityByPage(Page.noPageBean(), sd));
		
		PaperOptionEntity op = new PaperOptionEntity();
		op.setPaperId(paperId);
		map.put("options", optionDao.getEntityByPage(Page.noPageBean(), op));
		
		return map;
	}
	

}
