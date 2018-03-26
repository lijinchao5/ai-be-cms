package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.BookVersionEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface BookVersionEntityMapper {

	List<BookVersionEntity> getBookVersionByPage(Page<BookVersionEntity> page, BookVersionEntity bookEntity);
	

	/**
	 * 
	 * @Description: 批量插入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	int deleteOldval(String version_val);
	
	/**
	 * 
	 * @Description: 批量插入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	int insertBookVersion(List<BookVersionEntity> list);
}