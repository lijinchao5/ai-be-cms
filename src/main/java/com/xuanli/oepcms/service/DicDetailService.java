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

import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.entity.DicEntity;
import com.xuanli.oepcms.mapper.DicDetailEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.PageBean;


/** 
 * @author  QiaoYu 
 */
@Service
public class DicDetailService {
	@Autowired
	DicDetailEntityMapper dicDetailEntityMapper;


	public int saveDic(DicDetailEntity dicDetailEntity) {
		int flag=1;
		try {
			dicDetailEntityMapper.insertSelective(dicDetailEntity);
		} catch (Exception e) {
			e.printStackTrace();
			flag=0;
		}
		return flag;
	}


	public int updateDic(DicDetailEntity dicDetailEntity) {
		int flag=1;
		try {
			dicDetailEntityMapper.updateByPrimaryKeySelective(dicDetailEntity);
		} catch (Exception e) {
			flag=0;
		}
		return flag;
	}


	public int delDic(DicDetailEntity dicDetailEntity) {
		int flag=1;
		try {
			dicDetailEntityMapper.deleteByPrimaryKey(dicDetailEntity);
		} catch (Exception e) {
			flag=0;
		}
		return flag;
	}


	public Object findById(Integer id) {
		return dicDetailEntityMapper.selectByPrimaryKey(id);
	}


	public Page<DicDetailEntity> findByPage(Page<DicDetailEntity> pages, DicDetailEntity dicDetailEntity) {
		pages.setRecords(dicDetailEntityMapper.selectBypage(pages,dicDetailEntity));
		return pages;
	}
	
}
