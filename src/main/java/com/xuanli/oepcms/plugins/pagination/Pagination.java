package com.xuanli.oepcms.plugins.pagination;

import java.io.Serializable;

import org.apache.ibatis.session.RowBounds;

/**
 * 分页实现类
 * 
 * @FileName Pagination.java
 * @Author WangMengmeng E-mail:wangmeng6447@163.com
 * @CreatTime 2016年10月23日 下午7:28:41
 * @Version 1.0
 */
public class Pagination extends RowBounds implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 总数 */
	private int total;

	/* 每页显示条数 */
	private int size = 2;

	/* 总页数 */
	private int pages;

	/* 当前页 */
	private int current = 1;

	/* 查询总记录数（默认 true） */
	private boolean searchCount = true;
	
	/* 查询总记录数（默认 true） */
	private boolean searchAll = false;

	public Pagination() {
		super();
	}

	public Pagination(int current, int size) {
		this(current, size, true);
	}

	public Pagination(int current, int size, boolean searchCount) {
		super(offsetCurrent(current, size), size);
		if (current > 1) {
			this.current = current;
		}
		this.size = size;
		this.searchCount = searchCount;
	}

	protected static int offsetCurrent(int current, int size) {
		if (current > 0) {
			return (current - 1) * size;
		}
		return 0;
	}

	public int getOffsetCurrent() {
		return offsetCurrent(this.current, this.size);
	}

	public boolean hasPrevious() {
		return this.current > 1;
	}

	public boolean hasNext() {
		return this.current < this.pages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.pages = this.total / this.size;
		if (this.total % this.size != 0) {
			this.pages++;
		}
		if (this.current > this.pages) {
			/**
			 * 当前页大于总页数，当前页设置为第一页
			 */
			this.current = 1;
		}
	}

	public int getSize() {
		return size;
	}

	public int getPages() {
		return pages;
	}

	public int getCurrent() {
		return current;
	}

	public boolean isSearchCount() {
		return searchCount;
	}

	public boolean isSearchAll() {
		return searchAll;
	}

	public void setSearchAll(boolean searchAll) {
		this.searchAll = searchAll;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}

	public void setSearchCount(boolean searchCount) {
		this.searchCount = searchCount;
	}

	@Override
	public String toString() {
		return "Pagination { total=" + total + " ,size=" + size + " ,pages=" + pages + " ,current=" + current + " }";
	}
}
