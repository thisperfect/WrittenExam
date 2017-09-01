package com.dayee.writtenExam.model.search;

/**
 * Created by yq.song on 2016/10/8.
 */
public class QueryForm {
	
	/**当前页**/
	protected Integer currentPage=1;
	
	/**分页条数**/
	protected Integer pageSize=20;
	
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
}
