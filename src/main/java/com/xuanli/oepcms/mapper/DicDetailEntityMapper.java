package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.entity.DicEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface DicDetailEntityMapper {

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:48:49
	 */
	public List<DicDetailEntity> findDicByType(DicEntity dicEntity);
	
	
	void insertSelective(DicDetailEntity dicDetailEntity);


	void updateByPrimaryKeySelective(DicDetailEntity dicDetailEntity);


	void deleteByPrimaryKey(DicDetailEntity dicDetailEntity);


	public Object selectByPrimaryKey(Integer id);


	public List<DicDetailEntity> selectBypage(Page<DicDetailEntity> pages, DicDetailEntity dicDetailEntity);
	
}