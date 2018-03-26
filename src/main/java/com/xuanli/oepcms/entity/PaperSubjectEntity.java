package com.xuanli.oepcms.entity;

import java.util.Date;
import java.util.List;

public class PaperSubjectEntity {
    private Long id;

    private Long paperId;

    private String subjectNum;

    private String subject;

    private String score;

    private String audio;

    private String totalTime;

    private Integer type;

    private Long createId;

    private Date createDate;

    private Long updateId;

    private Date updateDate;

    private String enableFlag;
    
    private List<PaperSubjectDetailEntity> list;

    public List<PaperSubjectDetailEntity> getList() {
		return list;
	}

	public void setList(List<PaperSubjectDetailEntity> list) {
		this.list = list;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        this.enableFlag = enableFlag;
    }
}