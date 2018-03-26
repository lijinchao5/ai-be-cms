package com.xuanli.oepcms.entity;

import java.util.Date;

public class HomeworkStudentScoreEntity {
    private Long id;

    private Long studentId;

    private Long homeworkId;

    private Long sectionId;

    private String audioPath;

    private String text;

    private Double score;

    private String remark;

    private Long createId;

    private Date createDate;

    private Long updateId;

    private Date updateDate;

    private String enableFlag;

    private Double integrity;

    private Double pronunciation;

    private Double fluency;

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
        this.audioPath = audioPath == null ? null : audioPath.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public Double getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Double integrity) {
        this.integrity = integrity;
    }

    public Double getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(Double pronunciation) {
        this.pronunciation = pronunciation;
    }

    public Double getFluency() {
        return fluency;
    }

    public void setFluency(Double fluency) {
        this.fluency = fluency;
    }
}