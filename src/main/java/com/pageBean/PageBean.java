package com.pageBean;

import java.util.List;

import com.latest_news.model.Latest_newsVO;

public class PageBean<T> {
	private Integer totalCount;
	private Integer totalPage;
	private Integer currentPage;
	private Integer pageSize;
	private List<T> list;
	
	public PageBean(){
		
	}
	
	public PageBean(Integer totalCount, Integer totalPage, Integer currentPage, Integer pageSize, List<T> list) {
		super();
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.list = list;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
