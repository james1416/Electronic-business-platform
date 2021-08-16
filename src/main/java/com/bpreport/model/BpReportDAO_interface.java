package com.bpreport.model;

import java.util.List;

public interface BpReportDAO_interface {
	//新增賣家檢舉
	public void insert(BpReportVO bprVO);
	//更新狀態(管理員)
	public void updatesta(Integer bpr_sta,Integer bpr_id);
	//刪除檢舉
	public void delete(Integer bpr_id);
	
	public void changesta(Integer bpr_id,Integer bpr_sta);
	public BpReportVO findByPrimaryKey(Integer bpr_memid);
	public List<BpReportVO> geAll();

}
