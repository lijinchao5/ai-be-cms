package com.xuanli.oepcms.plugins;

import java.util.Collections;
import java.util.List;

import com.xuanli.oepcms.plugins.pagination.Pagination;

/**
 * 基础分页拓展
 *  
 * @FileName Page.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午7:44:05
 * @Version 1.0
 */
public class Page<T> extends Pagination {

	private static final long serialVersionUID = 1L;

	/**
	 * 查询数据列表
	 */
	private List<T> records = Collections.emptyList();


	protected Page() {
		/* 保护 */
	}


	public Page( int current, int size ) {
		super(current, size);
	}


	public Page(List<T> records) {
		this.records = records;
	}


	public List<T> getRecords() {
		return records;
	}


	public void setRecords( List<T> records ) {
		this.records = records;
	}

	public static <W> Page<W> noPageBean() {
		Page<W> page = new Page<W>();
		page.setSearchAll(true);
		return page;
	}

	@Override
	public String toString() {
		StringBuffer pg = new StringBuffer();
		pg.append(" Page:{ [").append(super.toString()).append("], ");
		if ( records != null ) {
			pg.append("records-size:").append(records.size());
		} else {
			pg.append("records is null");
		}
		return pg.append(" }").toString();
	}
}
