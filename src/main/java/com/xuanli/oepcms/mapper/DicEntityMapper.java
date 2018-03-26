package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.DicEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface DicEntityMapper {
	
	void insertDicEntity(DicEntity dicEntity);
	
	void updateById(DicEntity dicEntity);
	
	void deleteById(DicEntity dicEntity);
	
	DicEntity selectById(int id);
	
	/*
	 * 查询分页总数
	 * 
	 */
	int findByPageTotal(DicEntity dicEntity);
	
	/*
	 * 
	 * 查询分页
	 */
	List<DicEntity> findByPage(Page<DicEntity> pages,DicEntity dicEntity);
    
    

}