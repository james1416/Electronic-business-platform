package com.txreport.model;

import java.util.List;

public interface TXReportDAO_interface {
	
	List<TXReportVO> getAll(Integer start , Integer rows);
	
	List<TXReportVO> getAllbySTA(Integer tr_sta , Integer start , Integer rows);
	
	List<TXReportVO> getSelf(Integer tr_bmemid);
	
	void add(TXReportVO txReportVO);
	
	void updata(Integer tr_id , Integer tr_sta);
	
	TXReportVO getone(Integer tr_id);
	
	Boolean check(Integer tr_odid);
	
}
