/**
 * @fileName:  ReportService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:36:28
 */
package com.xuanli.oepcms.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.controller.bean.HomeworkPicScoreBean;
import com.xuanli.oepcms.controller.bean.HomeworkSymbolScore;
import com.xuanli.oepcms.entity.HomeworkStudentEntity;
import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Service
public class ReportService extends BaseService {
	@Autowired
	HomeworkService homeworkService;
	public static final int TOTALSCORE = 100;

	/**
	 * @Description: TODO 获取作业报告
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午9:39:06
	 */
	public RestResult<Map<String, Object>> homeworkReport(long homeworkId) {
		//获取统计信息
		List<HomeworkStudentScoreEntity> studentScores = homeworkService.getHomeworkStudentScoreByHomeworkId(homeworkId);
		//获取学情分析数据
		//获取音素平均分
		List<HomeworkSymbolScore> symbolScores = homeworkService.getHomeworkSymbolScore(homeworkId);
		//获取每个题的平均分
		List<HomeworkPicScoreBean> subjectScores = homeworkService.getHomeworkPicScore(homeworkId);
		//获取每个类型题平均分
		List<HomeworkPicScoreBean> subjectTypeScores = homeworkService.getHomeworkPicTypeScore(homeworkId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("studentScore", studentScores);
		resultMap.put("symbolScore", symbolScores);
		resultMap.put("subjectScore", subjectScores);
		resultMap.put("subjectTypeScore", subjectTypeScores);
		return ok(resultMap);
	}

	/**
	 * @Description: TODO 生成作业报告
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月22日 上午9:41:19
	 */
	public RestResult<String> generatorHomeworkReport(long homeworkId, Long id) {
		// 生成学生作业分数
		// --获取题目数量
		int subjectSize = homeworkService.reportHomeworkDetail(homeworkId);// 一共十个题
		// --获取学生总得分
		List<HomeworkStudentScoreEntity> studentScoreEntities = homeworkService.reportStudentScore(homeworkId);
		for (HomeworkStudentScoreEntity homeworkStudentScoreEntity : studentScoreEntities) {
			Long studentId = homeworkStudentScoreEntity.getStudentId();
			Double score = homeworkStudentScoreEntity.getScore();
			double studentScore = score / subjectSize;
			// 更新数据库
			HomeworkStudentEntity studentEntity = new HomeworkStudentEntity();
			studentEntity.setHomeworkId(homeworkId);
			studentEntity.setStudentId(studentId);
			BigDecimal b = new BigDecimal(studentScore);
			studentScore = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			studentEntity.setScore(studentScore);
			// 保存本次学生的分数
			homeworkService.updateHomeworkStudentScoreEntityBatch(studentEntity);
		}
		
		
		
		
		
		return ok("生成完成");
	}

}
