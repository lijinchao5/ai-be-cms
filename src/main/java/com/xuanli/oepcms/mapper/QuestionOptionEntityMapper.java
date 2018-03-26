package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.QuestionOptionEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface QuestionOptionEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuestionOptionEntity record);

    int insertSelective(QuestionOptionEntity record);

    QuestionOptionEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionOptionEntity record);

    int updateByPrimaryKey(QuestionOptionEntity record);
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<QuestionOptionEntity> 
	 * @author  WangMeng
	 */
	List<QuestionOptionEntity> getEntityByPage(Page<QuestionOptionEntity> page,QuestionOptionEntity entity);
}