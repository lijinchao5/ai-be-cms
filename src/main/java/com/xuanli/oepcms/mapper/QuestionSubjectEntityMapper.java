package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.QuestionSubjectEntity;
import com.xuanli.oepcms.plugins.Page;

@Mapper
public interface QuestionSubjectEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuestionSubjectEntity record);

    int insertSelective(QuestionSubjectEntity record);

    QuestionSubjectEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionSubjectEntity record);

    int updateByPrimaryKey(QuestionSubjectEntity record);
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<QuestionSubjectEntity> 
	 * @author  WangMeng
	 */
	List<QuestionSubjectEntity> getEntityByPage(Page<QuestionSubjectEntity> page,QuestionSubjectEntity entity);
}