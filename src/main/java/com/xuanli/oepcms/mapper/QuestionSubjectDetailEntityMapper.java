package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.QuestionSubjectDetailEntity;
import com.xuanli.oepcms.plugins.Page;

@Mapper
public interface QuestionSubjectDetailEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuestionSubjectDetailEntity record);

    int insertSelective(QuestionSubjectDetailEntity record);

    QuestionSubjectDetailEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionSubjectDetailEntity record);

    int updateByPrimaryKey(QuestionSubjectDetailEntity record);
    
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<QuestionSubjectDetailEntity> 
	 * @author  WangMeng
	 */
	List<QuestionSubjectDetailEntity> getEntityByPage(Page<QuestionSubjectDetailEntity> page,QuestionSubjectDetailEntity entity);
}