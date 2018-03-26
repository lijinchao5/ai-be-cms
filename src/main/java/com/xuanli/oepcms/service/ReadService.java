package com.xuanli.oepcms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.ReadArticle;
import com.xuanli.oepcms.entity.ReadSentence;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.mapper.ReadArticleMapper;
import com.xuanli.oepcms.mapper.ReadSentenceMapper;
import com.xuanli.oepcms.plugins.Page;

/** 
 * 
 * @ClassName: WordsService  
 * @Description: 单词管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@Service
public class ReadService {
	
	@Autowired
	ReadArticleMapper articleDao;
	
	@Autowired
	ReadSentenceMapper sentenceDao;

	@Transactional
	public int saveArticle(ReadArticle obj) throws Exception {
		
		int i = 0;
		i = articleDao.insertSelective(obj);
		List<ReadSentence> list = obj.getList();
		for (ReadSentence rs : list) {
			rs.setArticleId(obj.getId());
			rs.setCreateId(obj.getCreateId());
			rs.setCreateDate(obj.getCreateDate());
		}
		i = sentenceDao.insertBatch(list);
		return i;
	}

	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	ReadArticle 
	 * @author  WangMeng
	 */
	public ReadArticle getReadArticle(Long id) {
		
		ReadArticle ra = new ReadArticle();
		ra.setId(id);
		List<ReadArticle> ras = articleDao.getEntityByPage(Page.noPageBean() ,ra);
		if(ras.size() < 1) throw new ServiceException("数据不存在！");
		ra = ras.get(0);
		
		ReadSentence rs = new ReadSentence();
		rs.setArticleId(id);
		List<ReadSentence> rss = sentenceDao.getEntityByPage(Page.noPageBean() ,rs);
		if(rss.size() < 1) throw new ServiceException("数据不存在！");
		ra.setList(rss);
		
		return ra;
	}

	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	ReadArticle 
	 * @author  WangMeng
	 */
	public Map<String, Object> getReadArticleCont(Long id) {
		
		Map<String, Object> map = new HashMap<>();
		
		ReadSentence rs = new ReadSentence();
		rs.setArticleId(id);
		map.put("sections", sentenceDao.getEntityByPage(Page.noPageBean() ,rs));
		
		return map;
	}
	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<ReadArticle> getEntityByPage(Page<ReadArticle> page, ReadArticle entity) {
		page.setRecords(articleDao.getEntityByPage(page,entity));
		return page;
	}

}
