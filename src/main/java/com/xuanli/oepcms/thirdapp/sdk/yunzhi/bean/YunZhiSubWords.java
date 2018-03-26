/**
 * @fileName:  YunZhiSubWords.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:50:43
 */ 
package com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean; 

/** 
 * @author  QiaoYu 
 */
public class YunZhiSubWords {
	private double begin;
	private double end;
	private double score;
	private String subtext;
	private double volume;
	public double getBegin() {
		return begin;
	}
	public void setBegin(double begin) {
		this.begin = begin;
	}
	public double getEnd() {
		return end;
	}
	public void setEnd(double end) {
		this.end = end;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}
