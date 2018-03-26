package com.xuanli.oepcms.entity;

import java.util.Date;

public class ReadSentence {
    private Long id;

    private Long articleId;

    private String name;

    private String sentenceCont;

    private String sentenceMean;

    private Integer wordNum;

    private String picturePath;

    private String audioPath;

    private Integer orderNum;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;

    private String fileKey;

    public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSentenceCont() {
        return sentenceCont;
    }

    public void setSentenceCont(String sentenceCont) {
        this.sentenceCont = sentenceCont == null ? null : sentenceCont.trim();
    }

    public String getSentenceMean() {
        return sentenceMean;
    }

    public void setSentenceMean(String sentenceMean) {
        this.sentenceMean = sentenceMean == null ? null : sentenceMean.trim();
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath == null ? null : audioPath.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
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
}