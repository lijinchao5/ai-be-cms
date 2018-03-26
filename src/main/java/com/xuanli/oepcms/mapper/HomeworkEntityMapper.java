package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkEntity;
@Mapper
public interface HomeworkEntityMapper {
	/**删除方法，根据id删除*/
    int deleteHomeworkEntity(Long id);
    /**增加方法*/
    int insertHomeworkEntity(HomeworkEntity record);
    /**查询方法,根据id查询*/
    HomeworkEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkEntity(HomeworkEntity record);

}