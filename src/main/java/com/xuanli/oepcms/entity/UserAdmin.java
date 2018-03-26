package com.xuanli.oepcms.entity;

public class UserAdmin {
    private Integer id;

	private String uname;

	private String upwd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname == null ? null : uname.trim();
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd == null ? null : upwd.trim();
	}

}