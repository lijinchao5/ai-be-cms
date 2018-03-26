/**
 * @fileName:  YunZhiBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:44:28
 */ 
package com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean;

import java.util.List;

/** 
 * @author  QiaoYu 
 */
public class YunZhiBean {
	private String EvalType;
	private String version;
	private double score;
	private List<YunZhiline> lines;
	public String getEvalType() {
		return EvalType;
	}
	public void setEvalType(String evalType) {
		EvalType = evalType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public List<YunZhiline> getLines() {
		return lines;
	}
	public void setLines(List<YunZhiline> lines) {
		this.lines = lines;
	}
	
}
