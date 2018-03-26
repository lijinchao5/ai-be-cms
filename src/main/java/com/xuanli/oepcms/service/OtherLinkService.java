package com.xuanli.oepcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.mapper.OtherLinkEntityMapper;
import com.xuanli.oepcms.plugins.Page;

/** 
 * 
 * @ClassName: OtherLinkService  
 * @Description: 外链管理
 * @author WangMeng  
 * @date 2018年2月26日  
 *
 */
@Service
public class OtherLinkService {
	
	@Autowired
	OtherLinkEntityMapper dao;
	
	public int saveLink(OtherLinkEntity words) throws Exception {
		return dao.insertSelective(words);
		
	}

	public int updateLink(OtherLinkEntity words) throws Exception {
		return dao.updateByPrimaryKeySelective(words);
		
	}

	/**
	 * 
	 * @Description: 获取列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<OtherLinkEntity> getEntityByPage(Page<OtherLinkEntity> page, OtherLinkEntity entity) {
		page.setRecords(dao.getEntityByPage(page,entity));
		return page;
	}

}
