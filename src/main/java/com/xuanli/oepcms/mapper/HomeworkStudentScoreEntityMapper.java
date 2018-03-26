package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.controller.bean.HomeworkPicScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkScoreBean;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
@Mapper
public interface HomeworkStudentScoreEntityMapper {
    int deleteHomeworkStudentScoreEntity(Long id);

    int insertHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

    HomeworkStudentScoreEntity selectById(Long id);

    int updateHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午10:47:32
	 */
	List<HomeworkStudentScoreEntity> reportStudentScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 下午4:12:04
	 */
	List<HomeworkScoreBean> getStudentHomework(HomeworkScoreBean homeworkScoreBean);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:48:56
	 */
	List<HomeworkPicScoreBean> getHomeworkPickScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:52:43
	 */
	List<HomeworkPicScoreBean> getHomeworkPicTypeScore(long homeworkId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月23日 下午2:58:29
	 */
	List<HomeworkStudentScoreEntity> selectHomeworkStudentScore(HomeworkStudentScoreEntity homeworkStudentScoreEntity);
	
	List<HomeworkScoreBean> getStudentHomework(@Param("homeworkId")Long homeworkId,@Param("studentId")Long studentId, @Param("homeworkType")String homeworkType);
}