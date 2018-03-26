package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
@Mapper
public interface HomeworkStudentEntityMapper {
	/**删除方法，根据id删除*/
    int deleteHomeworkStudentEntity(Long id);
    /**增加方法*/
    int insertHomeworkStudentEntity(HomeworkStudentEntity record);
    /**查询方法,根据id查询*/
    HomeworkStudentEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkStudentEntity(HomeworkStudentEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 下午4:09:46
	 */
	void insertHomeworkStudentEntityBatch(List<HomeworkStudentEntity> homeworkStudentEntities);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午11:06:52
	 */
	void updateHomeworkStudentEntityBatch(HomeworkStudentEntity homeworkStudentEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午3:13:07
	 */
	List<HomeworkStudentScoreEntity> selectHomeworkStudentEntity(HomeworkStudentEntity homeworkStudentEntity);
	/**评语*/
	int updateHomewordStudentEntityRemark(@Param("userIds")String[] userIds,@Param("homeworkId")Long homeworkId,@Param("remark")String remark);
	
}