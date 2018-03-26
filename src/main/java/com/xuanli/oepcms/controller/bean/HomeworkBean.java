/**
 * @fileName:  Subject.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月19日 下午3:40:06
 */ 
package com.xuanli.oepcms.controller.bean; 

/** 
 * @author  QiaoYu 
 */
public class HomeworkBean {
	private int type;
	private String subjects;
	/** 
	 * @return 返回 type 
	 */
	public int getType() {
		return type;
	}
	/** 
	 * @setParam 设置type
	 */
	public void setType(int type) {
		this.type = type;
	}
	/** 
	 * @return 返回 subjects 
	 */
	public String getSubjects() {
		return subjects;
	}
	/** 
	 * @setParam 设置subjects
	 */
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	
}
