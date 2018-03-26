/**
 * @fileName:  DicService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月30日 上午9:43:59
 */ 
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.entity.DicEntity;
import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.mapper.DicDetailEntityMapper;
import com.xuanli.oepcms.mapper.DicEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.PageBean;


/** 
 * @author  QiaoYu 
 */
@Service
public class DicService {
	@Autowired
	DicEntityMapper dicEntityMapper;
	
	@Autowired
	DicDetailEntityMapper dicDetailEntityMapper;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:46:00
	 */
	public List<DicDetailEntity> findDicByType(String type) {
		DicEntity dicEntity = new DicEntity();
		dicEntity.setType(type);
		return dicDetailEntityMapper.findDicByType(dicEntity);
	}

	public int saveDic(DicEntity dicEntity) {
		int flag=1;
		try {
			dicEntityMapper.insertDicEntity(dicEntity);
		} catch (Exception e) {
			e.printStackTrace();
			flag=0;
		}
		return flag;
	}

	public int updateDic(DicEntity dicEntity) {
		int flag=1;
		try {
			dicEntityMapper.updateById(dicEntity);
		} catch (Exception e) {
			flag=0;
		}
		return flag;
	}

	public int delDic(DicEntity dicEntity) {
		int flag=1;
		try {
			dicEntityMapper.deleteById(dicEntity);
		} catch (Exception e) {
			flag=0;
		}
		return flag;
	}

	public Object findById(Integer id) {
		return dicEntityMapper.selectById(id);
	}
	
	
	public Page<DicEntity> findByPage(Page<DicEntity> pages,DicEntity dicEntity) {
		pages.setRecords(dicEntityMapper.findByPage(pages,dicEntity));
		return pages;
		
	}
	
}
