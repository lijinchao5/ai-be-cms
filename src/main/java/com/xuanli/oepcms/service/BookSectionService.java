/**
 * @fileName:  BookSectionService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:49:06
 */ 
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.mapper.SectionEntityMapper;

/** 
 * @author  QiaoYu 
 */
@Service
public class BookSectionService {
	@Autowired
	SectionEntityMapper sectionDao;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:54:45
	 */
	public List<SectionEntity> getSectionEntity(SectionEntity sectionEntity) {
		return sectionDao.getSectionEntity(sectionEntity);
	}

}
