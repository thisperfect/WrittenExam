package com.dayee.writtenExam.model.search;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yq.song on 2016/9/27.
 */
public class PageForm<T> {
	
	/**返回数据**/
	private Collection<T> pageData;
	
	/**总页数**/
	private Integer totalPage=0;
	
	/**当前页**/
	private Integer currentPage=0;
	
	/**分页大小**/
	private Integer pageSize=0;
	
	/**数据总条数**/
	private Integer dataCount=0;
	
	public void addPageData(T rowData){
		if(pageData==null){
			pageData=new ArrayList<T>();
		}
		pageData.add(rowData);
	}
	
	public Collection<T> getPageData() {
		return pageData;
	}
	
	public void setPageData(Collection<T> pageData) {
		this.pageData = pageData;
	}
	
	public Integer getTotalPage() {
		
		if(pageSize>0){
			Integer size=dataCount/pageSize;
			Integer mod=dataCount%pageSize;
			
			Integer totalPageTemp=mod==0?size:size+1;
			
			this.totalPage = totalPageTemp;
		}
		return totalPage;
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
	
	public Integer getDataCount() {
		return dataCount;
	}
	
	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
}
