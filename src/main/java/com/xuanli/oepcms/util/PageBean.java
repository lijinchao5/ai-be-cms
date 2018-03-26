package com.xuanli.oepcms.util;

import java.util.List;

public class PageBean {
	private Integer page;// 当前页
	private Integer rowFrom;// 开始的记录数
	private Integer rowTo;// 结束的记录数 ===pageSize
	private List<?> rows; // 记录信息
	private int pageSize;
	private Integer total;
	public PageBean(Integer page, Integer pageSize) {
		if (null!= pageSize && pageSize > 1) {
			this.pageSize = pageSize;
		}else {
			this.pageSize = 10;
		}
		this.page = page;
	}
	
	/** <默认构造函数> 
	 */
	public PageBean() {
		super(); 
	}

	public void setTotal(Integer total) {
		// 总记录数
		if (total <= 0) {
			this.total = 0;
		} else {
			this.total = total;
		}
		// 计算总页数
		Integer countPage = this.total % pageSize == 0 ? this.total / this.pageSize : this.total / pageSize + 1;
		// 获取当前页
		if (page < 0) {
			page = 1;
		} else if (page >= countPage) {
			page = countPage;
		} 
		// 计算出开始记录数 和结束记录数
		rowFrom = (page - 1) * pageSize;
		if (total <= 0) {
			rowFrom = 0;
		}
		if (page * pageSize > this.total) {
			rowTo = this.total;
		}else {
			rowTo = page * pageSize;
		}
//		System.out.println(rowFrom + "----------->" + rowTo);
//		System.out.println(rowFrom + "----------->" + pageSize);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRowFrom() {
		return rowFrom;
	}

	public void setRowFrom(Integer rowFrom) {
		this.rowFrom = rowFrom;
	}

	public Integer getRowTo() {
		return rowTo;
	}

	public void setRowTo(Integer rowTo) {
		this.rowTo = rowTo;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "PageBean [page=" + page + ", rowFrom=" + rowFrom + ", rowTo=" + rowTo + ", rows=" + rows + ", pageSize=" + pageSize + ", total=" + total + "]";
	}
	
}
