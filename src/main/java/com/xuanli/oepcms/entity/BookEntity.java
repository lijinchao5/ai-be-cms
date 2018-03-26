package com.xuanli.oepcms.entity;

import java.util.Date;
import java.util.List;

public class BookEntity {
    private Long id;

    private String name;
    
    private String bookVersion;

    private String grade;

    private String bookVolume;
    
    private String bookVersionName;

    private String gradeName;

    private String bookVolumeName;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;
    
    private List<UnitEntity> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookVersion() {
		return bookVersion;
	}

	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

	public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBookVolume() {
        return bookVolume;
    }

    public void setBookVolume(String bookVolume) {
        this.bookVolume = bookVolume;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
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

	public String getBookVersionName() {
		return bookVersionName;
	}

	public void setBookVersionName(String bookVersionName) {
		this.bookVersionName = bookVersionName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getBookVolumeName() {
		return bookVolumeName;
	}

	public void setBookVolumeName(String bookVolumeName) {
		this.bookVolumeName = bookVolumeName;
	}

	public List<UnitEntity> getList() {
		return list;
	}

	public void setList(List<UnitEntity> list) {
		this.list = list;
	}
    
}