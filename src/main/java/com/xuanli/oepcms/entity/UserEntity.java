package com.xuanli.oepcms.entity;

import java.util.Date;

import com.xuanli.oepcms.util.BasePageBean;

public class UserEntity{
    private Long id;

    private String name;

    private String mobile;

    private String password;

    private String sex;

    private Date birthDate;

    private Integer studySectionId;

    private Integer gradeLevelId;

    private Integer bookVersionId;

    private Integer roleId;

    private String nameNum;

    private String remark;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;
    
    private String schoolid;
    
    private String areaid;
    
    private String cmsId;

    
    //下面几个字段只用于查询
    private String clasId;
    private Long clasCreateId;
    

	public String getCmsId() {
		return cmsId;
	}

	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}

	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	/** 
	 * @return 返回 clasCreateId 
	 */
	public Long getClasCreateId() {
		return clasCreateId;
	}

	/** 
	 * @setParam 设置clasCreateId
	 */
	public void setClasCreateId(Long clasCreateId) {
		this.clasCreateId = clasCreateId;
	}

	/** 
	 * @return 返回 clasId 
	 */
	public String getClasId() {
		return clasId;
	}

	/** 
	 * @setParam 设置clasId
	 */
	public void setClasId(String clasId) {
		this.clasId = clasId;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getStudySectionId() {
        return studySectionId;
    }

    public void setStudySectionId(Integer studySectionId) {
        this.studySectionId = studySectionId;
    }

    public Integer getGradeLevelId() {
        return gradeLevelId;
    }

    public void setGradeLevelId(Integer gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }

    public Integer getBookVersionId() {
        return bookVersionId;
    }

    public void setBookVersionId(Integer bookVersionId) {
        this.bookVersionId = bookVersionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getNameNum() {
        return nameNum;
    }

    public void setNameNum(String nameNum) {
        this.nameNum = nameNum == null ? null : nameNum.trim();
    }

    /** 
	 * @return 返回 remark 
	 */
	public String getRemark() {
		return remark;
	}

	/** 
	 * @setParam 设置remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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