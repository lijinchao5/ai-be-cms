package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ReadArticle;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface ReadArticleMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ReadArticle record);

    int insertSelective(ReadArticle record);

    ReadArticle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReadArticle record);

    int updateByPrimaryKey(ReadArticle record);
    
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<ReadArticle> 
	 * @author  WangMeng
	 */
	List<ReadArticle> getEntityByPage(Page<ReadArticle> page,ReadArticle entity);
}