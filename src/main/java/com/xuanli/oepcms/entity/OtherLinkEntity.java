package com.xuanli.oepcms.entity;

import java.util.Date;

public class OtherLinkEntity {
    private Integer id;

    private String name;

    private String desp1;

    private String desp2;

    private String desp3;

    private String pic1;

    private String pic2;

    private String pic3;

    private String link1;

    private String link2;

    private String link3;

    private String type;

    private Integer orderby;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesp1() {
        return desp1;
    }

    public void setDesp1(String desp1) {
        this.desp1 = desp1 == null ? null : desp1.trim();
    }

    public String getDesp2() {
        return desp2;
    }

    public void setDesp2(String desp2) {
        this.desp2 = desp2 == null ? null : desp2.trim();
    }

    public String getDesp3() {
        return desp3;
    }

    public void setDesp3(String desp3) {
        this.desp3 = desp3 == null ? null : desp3.trim();
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1 == null ? null : pic1.trim();
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2 == null ? null : pic2.trim();
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3 == null ? null : pic3.trim();
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1 == null ? null : link1.trim();
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2 == null ? null : link2.trim();
    }

    public String getLink3() {
        return link3;
    }

    public void setLink3(String link3) {
        this.link3 = link3 == null ? null : link3.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
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