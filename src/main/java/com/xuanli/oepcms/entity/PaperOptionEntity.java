package com.xuanli.oepcms.entity;

import java.util.Date;

public class PaperOptionEntity {
    private Long id;

    private Long detailId;

    private String result;

    private String pointResult;

    private String correctResult;

    private Long createId;

    private Date createDate;

    private Long updateId;

    private Date updateDate;

    private String enableFlag;

    private String paperId;

    public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPointResult() {
        return pointResult;
    }

    public void setPointResult(String pointResult) {
        this.pointResult = pointResult;
    }

    public String getCorrectResult() {
        return correctResult;
    }

    public void setCorrectResult(String correctResult) {
        this.correctResult = correctResult;
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