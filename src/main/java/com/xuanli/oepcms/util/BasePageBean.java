/**
 * 文 件 名:  BasePageBean.java 
 * 描    述:  <描述> 
 * 修 改 人:  QiaoYu 
 * 修改时间:  2017年3月28日 下午10:04:36
 */ 
package com.xuanli.oepcms.util; 

/** 
 * @author  QiaoYu 
 */
public class BasePageBean {
	private int start;
	private int end;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	/** <默认构造函数> 
	 */
	public BasePageBean() {
		super(); 
	}
	
}
