package com.txreport.model;

import java.util.List;

public class TXReportService {
	private TXReportDAO_interface dao;
	
	public TXReportService() {
		dao = new TXReportDAO();
	}
	
	public void updata(Integer tr_id , Integer tr_sta) {
		dao.updata(tr_id, tr_sta);
	}
	
	public List<TXReportVO> getSelf(Integer tr_bmemid){
		return dao.getSelf(tr_bmemid);
		
	}
	
	public void add(Integer tr_odid , Integer tr_reporter , Integer tr_reported , String tr_content) {
		TXReportVO txReportVO = new TXReportVO();
		txReportVO.setTr_odid(tr_odid);
		txReportVO.setTr_reporter(tr_reporter);
		txReportVO.setTr_reported(tr_reported);
		txReportVO.setTr_content(tr_content);
		dao.add(txReportVO);
		
	}
	
	public List<TXReportVO> getAll(Integer start , Integer rows){
		return dao.getAll(start, rows);
	}
	
	public List<TXReportVO> getAllbySTA(Integer tr_sta, Integer start , Integer rows){
		return dao.getAllbySTA(tr_sta, start, rows);
	}
	
	public TXReportVO getone(Integer tr_id){
		return dao.getone(tr_id);
	}
	
	public boolean check(Integer tr_odid) {
		return dao.check(tr_odid);
	}
	
}
