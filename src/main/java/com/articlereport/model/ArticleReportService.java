package com.articlereport.model;

import java.util.List;

public class ArticleReportService {
	
	private ArticleReportDAO_interface dao;
	
	public ArticleReportService(){
		dao = new ArticleReportDAO();
	}
	//會員查檢舉紀錄
	public List<ArticleReportVO> getAll(Integer memid) {
		return dao.getAll(memid);
	}
	//獲取全部檢舉
	public List<ArticleReportVO> getAll() {
		return dao.getAll();
	}
	//會員文章檢舉
	public ArticleReportVO addArticleRep(Integer memid, Integer atid , String text) {
		ArticleReportVO articleReportVO = new ArticleReportVO();
		articleReportVO.setMemid(memid);
		articleReportVO.setAtid(atid);
		articleReportVO.setText(text);
		dao.insert(articleReportVO);
		return articleReportVO;
	}
	//審核通過
	public void reportPass(Integer repid) {
		dao.reportPass(repid);
	}
	//審核駁回
	public void reportReturn(Integer repid) {
		dao.reportReturn(repid);
	}

}
	