package com.yr.entity;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	private int pageSize = 4;// 每页多少条记录
	private int page = 1;// 当前页数
	private int pageCount = 0;// 总页数
	private int pageSizeCount;//总记录数
	private List<T> t = new ArrayList<>();//我们查询的结果集

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		if(pageSizeCount % pageSize == 0)
		{
			return pageSizeCount / pageSize;
		}
		else
		{
			return (pageSizeCount / pageSize) + 1;
		}
		
	}

	public List<T> getT() {
		return t;
	}

	public void setT(List<T> t) {
		this.t = t;
	}

	public int getPageSizeCount() {
		return pageSizeCount;
	}

	public void setPageSizeCount(int pageSizeCount) {
		this.pageSizeCount = pageSizeCount;
	}

	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", page=" + page + ", pageCount=" + pageCount + ", pageSizeCount="
				+ pageSizeCount + ", t=" + t + "]";
	}
	
	
}