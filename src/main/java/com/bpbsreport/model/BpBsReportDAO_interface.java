package com.bpbsreport.model;

import java.util.List;

public interface BpBsReportDAO_interface {
	public void insert(BpBsReportVO bbsVO);
	public void update(BpBsReportVO bbsVO); //暫時不做太麻煩
	public void delete(Integer bbs_id);
	public BpBsReportVO findByPrimaryKey_forbmemid(Integer bbs_bmemid);
	public BpBsReportVO findByPrimaryKey_forsmemid(Integer bbs_smemid);
	public void changeSta(Integer bbs_sta ,Integer bbs_id);
	public List<BpBsReportVO> getAll();
}
