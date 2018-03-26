package com.xuanli.oepcms.util;

import java.util.ArrayList;
import java.util.List;

public class TreeObject {
	private Integer id;
	private Integer parentId;
	private String name;
	private String parentName;
	private String resKey;
	private String resUrl;
	private Integer level;
	private String type;
	private String description;
	private String icon;
	private Integer ishide;
	private List<TreeObject> children = new ArrayList<TreeObject>();
	private String text;
	private boolean checked;
	private String iconCls;
	private String state;
	private String isCheck;
	private String img;
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TreeObject [id=");
		builder.append(id);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", text=");
		builder.append(text);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIshide() {
		return ishide;
	}

	public void setIshide(Integer ishide) {
		this.ishide = ishide;
	}

	public List<TreeObject> getChildren() {
		return children;
	}

	public void setChildren(List<TreeObject> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
