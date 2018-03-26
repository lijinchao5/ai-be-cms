/**
 * @fileName:  BookSectionDetailService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:49:12
 */ 
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.mapper.SectionDetailMapper;

/** 
 * @author  QiaoYu 
 */
@Service
public class BookSectionDetailService {
	@Autowired
	SectionDetailMapper sectionDetailDao;
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午10:14:44
	 */
	public List<SectionDetail> getSectionDetail(SectionDetail sectionDetail) {
		return sectionDetailDao.getSectionDetails(sectionDetail);
	}

}
