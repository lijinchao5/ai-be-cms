/**
 * @fileName:  YunZhiSubWords.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:48:18
 */ 
package com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean;

import java.util.List;

/** 
 * @author  QiaoYu 
 */
public class YunZhiWords {
	private double begin;
	private double end;
	private double score;
	private String text;
	private int type;
	private double volume;
	private String phonetic;
	private String usertext;
	private int StressOfWord;
	private List<YunZhiSubWords> subwords;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public String getPhonetic() {
		return phonetic;
	}
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	public String getUsertext() {
		return usertext;
	}
	public void setUsertext(String usertext) {
		this.usertext = usertext;
	}
	public int getStressOfWord() {
		return StressOfWord;
	}
	public void setStressOfWord(int stressOfWord) {
		StressOfWord = stressOfWord;
	}
	public List<YunZhiSubWords> getSubwords() {
		return subwords;
	}
	public void setSubwords(List<YunZhiSubWords> subwords) {
		this.subwords = subwords;
	}
	
	
}
