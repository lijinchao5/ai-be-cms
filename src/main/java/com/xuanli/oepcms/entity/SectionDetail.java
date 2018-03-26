package com.xuanli.oepcms.entity;

import java.util.Date;

public class SectionDetail {
    private Long id;

    private String personName;

    private String name;

    private String type;

    private String sectionId;

    private String audioPath;

    private String text;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;

    private String mAudioPath;

    private String wAudioPath;

    private String picturePath;

    private String wordType;

    private String wordMean;

    private String pointType;
    
    private Integer orderNum;
    
    private Integer dialogNum;

    private String bookId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId == null ? null : sectionId.trim();
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

    public String getmAudioPath() {
        return mAudioPath;
    }

    public void setmAudioPath(String mAudioPath) {
        this.mAudioPath = mAudioPath == null ? null : mAudioPath.trim();
    }

    public String getwAudioPath() {
        return wAudioPath;
    }

    public void setwAudioPath(String wAudioPath) {
        this.wAudioPath = wAudioPath == null ? null : wAudioPath.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType == null ? null : wordType.trim();
    }

    public String getWordMean() {
        return wordMean;
    }

    public void setWordMean(String wordMean) {
        this.wordMean = wordMean == null ? null : wordMean.trim();
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType == null ? null : pointType.trim();
    }

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getDialogNum() {
		return dialogNum;
	}

	public void setDialogNum(Integer dialogNum) {
		this.dialogNum = dialogNum;
	}
    
}