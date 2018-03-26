package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkDetailEntity;
@Mapper
public interface HomeworkDetailEntityMapper {
	/**删除方法，根据id删除*/
    int deleteHomeworkDetailEntity(Long id);
    /**增加方法*/
    int insertHomeworkDetailEntity(HomeworkDetailEntity record);
    /**查询方法,根据id查询*/
    HomeworkDetailEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkDetailEntity(HomeworkDetailEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 下午4:06:02
	 */
	void inserHomeworkDetailBatch(List<HomeworkDetailEntity> homeworkDetailEntities);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午11:13:07
	 */
	int reportHomeworkDetail(long homeworkId);

}