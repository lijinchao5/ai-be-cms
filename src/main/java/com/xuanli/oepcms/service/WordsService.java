package com.xuanli.oepcms.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.WordsEntity;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.mapper.WordsEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.AliOSSUtil;

/** 
 * 
 * @ClassName: WordsService  
 * @Description: 单词管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@Service
public class WordsService {
	
	@Autowired
	WordsEntityMapper wordsDao;
	
	@Autowired
	AliOSSUtil aliOSSUtil;
	
	/**
	 * 
	 * @Description: 单元测试  WordsServiceTest  初始化导入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	@Transactional
	public int saveWordsList(List<WordsEntity> entitys) throws Exception {

		int length = 150;
		
		List<WordsEntity> temp = new ArrayList<>();
		
		for (WordsEntity words : entitys) {
			
			String audioPathM = words.getAudioPathM();
			String audioPathMUk = words.getAudioPathMUk();
			String audioPathMUs = words.getAudioPathMUs();
			String audioPathW = words.getAudioPathW();
			String audioPathWUk = words.getAudioPathWUk();
			String audioPathWUs = words.getAudioPathWUs();
			
			String picturePath = words.getPicturePath();

	    	//调用阿里云存储
			if(StringUtils.isNotBlank(audioPathM)) {
				audioPathM = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPathM)), 
	    				words.getWordSpell() , "mp3");
	    	}

			if(StringUtils.isNotBlank(audioPathMUk)) {
				audioPathMUk = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPathMUk)), 
	    				words.getWordSpell() , "mp3");
	    	}

			if(StringUtils.isNotBlank(audioPathMUs)) {
				audioPathMUs = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPathMUs)), 
	    				words.getWordSpell() , "mp3");
	    	}

			if(StringUtils.isNotBlank(audioPathW)) {
				audioPathW = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPathW)), 
	    				words.getWordSpell() , "mp3");
	    	}

			if(StringUtils.isNotBlank(audioPathWUk)) {
				audioPathWUk = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPathWUk)), 
	    				words.getWordSpell() , "mp3");
	    	}

			if(StringUtils.isNotBlank(audioPathWUs)) {
				audioPathWUs = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPathWUs)), 
	    				words.getWordSpell() , "mp3");
	    	}
			
			if(StringUtils.isNotBlank(picturePath)) {
				picturePath = aliOSSUtil.uploadFile(new FileInputStream(new File(picturePath)), 
	    				words.getWordSpell() , "jpg");
	    	}
			
			words.setAudioPathM(audioPathM);
			words.setAudioPathMUk(audioPathMUk);
			words.setAudioPathMUs(audioPathMUs);
			words.setAudioPathW(audioPathW);
			words.setAudioPathWUk(audioPathWUk);
			words.setAudioPathWUs(audioPathWUs);
			words.setPicturePath(picturePath);
			
			temp.add(words);
			
			if(temp.size() == length) {
				int i = wordsDao.insertWordsBatch(temp);
				if(i<1) throw new ServiceException("保存失败");
				temp = new ArrayList<>();
			}
		}
		
		if(temp.size()!=0) {
			int i = wordsDao.insertWordsBatch(temp);
			if(i<1) throw new ServiceException("保存失败");
		}
		
		return 1;
	}
	
	
	public int saveWords(WordsEntity words) throws Exception {
		return wordsDao.insertSelective(words);
		
	}

	
	public int updateWords(WordsEntity words) throws Exception {
		return wordsDao.updateByPrimaryKeySelective(words);
		
	}

	
	/**
	 * 
	 * @Description: 重复验证
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public int repeatValid(WordsEntity words) {
		return wordsDao.repeatValid(words);
	}

	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<WordsEntity> getEntityByPage(Page<WordsEntity> page, WordsEntity entity) {
		page.setRecords(wordsDao.getEntityByPage(page,entity));
		return page;
	}

}
