/**
 * @fileName:  HomeworkScoreBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午3:46:27
 */ 
package com.xuanli.oepcms.controller.bean; 

/** 
 * @author  QiaoYu 
 */
public class HomeworkScoreBean {
	private Long id;
	private Long studentId;
	private Long homeworkId;
	private Long sectionId;
	private String audioPath;
	private String studentText;
	private Double studentScore;
	private Integer homeworkType;
	private Long sectionDetailId;
	private String standerAudioPath;
	private String standerText;
	private String standerMAudioPath;
	private String standerWAudioPath;
	private String standerWordMean;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getStudentText() {
		return studentText;
	}
	public void setStudentText(String studentText) {
		this.studentText = studentText;
	}
	public Double getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(Double studentScore) {
		this.studentScore = studentScore;
	}
	public Integer getHomeworkType() {
		return homeworkType;
	}
	public void setHomeworkType(Integer homeworkType) {
		this.homeworkType = homeworkType;
	}
	public Long getSectionDetailId() {
		return sectionDetailId;
	}
	public void setSectionDetailId(Long sectionDetailId) {
		this.sectionDetailId = sectionDetailId;
	}
	public String getStanderAudioPath() {
		return standerAudioPath;
	}
	public void setStanderAudioPath(String standerAudioPath) {
		this.standerAudioPath = standerAudioPath;
	}
	public String getStanderText() {
		return standerText;
	}
	public void setStanderText(String standerText) {
		this.standerText = standerText;
	}
	public String getStanderMAudioPath() {
		return standerMAudioPath;
	}
	public void setStanderMAudioPath(String standerMAudioPath) {
		this.standerMAudioPath = standerMAudioPath;
	}
	public String getStanderWAudioPath() {
		return standerWAudioPath;
	}
	public void setStanderWAudioPath(String standerWAudioPath) {
		this.standerWAudioPath = standerWAudioPath;
	}
	public String getStanderWordMean() {
		return standerWordMean;
	}
	public void setStanderWordMean(String standerWordMean) {
		this.standerWordMean = standerWordMean;
	}
	
}
