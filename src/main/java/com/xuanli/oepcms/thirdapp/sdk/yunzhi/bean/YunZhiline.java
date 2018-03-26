/**
 * @fileName:  YunZhiWords.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:45:58
 */ 
package com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean;

import java.util.List;

/** 
 * @author  QiaoYu 
 */
public class YunZhiline {
	private double begin;
	private double end;
	private double fluency;
	private double integrity;
	private double pronunciation;
	private String sample;
	private double score;
	private String usertext;
	private List<YunZhiWords> words;
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
	public double getFluency() {
		return fluency;
	}
	public void setFluency(double fluency) {
		this.fluency = fluency;
	}
	public double getIntegrity() {
		return integrity;
	}
	public void setIntegrity(double integrity) {
		this.integrity = integrity;
	}
	public double getPronunciation() {
		return pronunciation;
	}
	public void setPronunciation(double pronunciation) {
		this.pronunciation = pronunciation;
	}
	public String getSample() {
		return sample;
	}
	public void setSample(String sample) {
		this.sample = sample;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getUsertext() {
		return usertext;
	}
	public void setUsertext(String usertext) {
		this.usertext = usertext;
	}
	public List<YunZhiWords> getWords() {
		return words;
	}
	public void setWords(List<YunZhiWords> words) {
		this.words = words;
	}
}
