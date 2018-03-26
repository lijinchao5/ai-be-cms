package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkStudentScoreWordEntity;
@Mapper
public interface HomeworkStudentScoreWordEntityMapper {
	/**删除学生作业单词分数方法*/
    int deleteHomeworkStudentScoreWordEntity(Long id);
    /**保存学生作业单词分数方法*/
    int insertHomeworkStudentScoreWordEntity(HomeworkStudentScoreWordEntity record);
    /**查询学生作业单词分数方法*/
    HomeworkStudentScoreWordEntity selectById(Long id);
    /**更新学生作业单词分数方法*/
    int updateHomeworkStudentScoreWordEntity(HomeworkStudentScoreWordEntity record);

}