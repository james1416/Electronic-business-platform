package com.articlereport.model;

import java.util.List;

public interface ArticleReportDAO_interface {
	
	public List<ArticleReportVO> getAll(Integer memid);

	
	public void insert(ArticleReportVO articleReportVO);
	
	public List<ArticleReportVO> getAll();
	
	public void reportPass(Integer repid);
	
	public void reportReturn(Integer repid);
}
