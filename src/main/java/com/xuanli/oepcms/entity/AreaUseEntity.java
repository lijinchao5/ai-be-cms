package com.xuanli.oepcms.entity;

import java.util.Date;

public class AreaUseEntity {
    private String id;

    private Date startdate;

    private Date enddate;
    
    private int updateid;
    
    private Date updatedate;
    
    private String addressProvinceName;
    
    private String addressCityName;
    
    private String addressAreaName;
    
    private String schoolcount;
    
    private String qxcount;
    
    private String cscount;
    
    
    public int getUpdateid() {
		return updateid;
	}

	public void setUpdateid(int updateid) {
		this.updateid = updateid;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getQxcount() {
		return qxcount;
	}

	public void setQxcount(String qxcount) {
		this.qxcount = qxcount;
	}

	public String getCscount() {
		return cscount;
	}

	public void setCscount(String cscount) {
		this.cscount = cscount;
	}

	public String getSchoolcount() {
		return schoolcount;
	}

	public void setSchoolcount(String schoolcount) {
		this.schoolcount = schoolcount;
	}

	public String getAddressProvinceName() {
		return addressProvinceName;
	}

	public void setAddressProvinceName(String addressProvinceName) {
		this.addressProvinceName = addressProvinceName;
	}

	public String getAddressCityName() {
		return addressCityName;
	}

	public void setAddressCityName(String addressCityName) {
		this.addressCityName = addressCityName;
	}

	public String getAddressAreaName() {
		return addressAreaName;
	}

	public void setAddressAreaName(String addressAreaName) {
		this.addressAreaName = addressAreaName;
	}


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}