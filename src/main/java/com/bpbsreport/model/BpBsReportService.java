package com.bpbsreport.model;

import java.sql.Timestamp;
import java.util.List;

public class BpBsReportService {
	private BpBsReportDAO_interface dao;
	public BpBsReportService() {
		dao = new BpBsReportDAO();
	}
	public BpBsReportVO addBpBs(Integer bbs_bpid,Integer bbs_bmemid,Integer bbs_smemid,Timestamp bbs_date,String bbs_ms) {
		BpBsReportVO bbsVO = new BpBsReportVO();
		bbsVO.setBbs_bpid(bbs_bpid);
		bbsVO.setBbs_bmemid(bbs_bmemid);
		bbsVO.setBbs_smemid(bbs_smemid);
		bbsVO.setBbs_date(bbs_date);
		bbsVO.setBbs_ms(bbs_ms);
		dao.insert(bbsVO);
		return bbsVO;
	}
	public void deleteBpBs(Integer bbs_id) {
		dao.delete(bbs_id);
	}
	public BpBsReportVO getOneForbmemid(Integer bbs_bmemid) {
		return dao.findByPrimaryKey_forbmemid(bbs_bmemid);
	}
	public BpBsReportVO getOneForsmemid(Integer bbs_smemid) {
		return dao.findByPrimaryKey_forsmemid(bbs_smemid);
	}
	public List<BpBsReportVO> getAll(){
		return dao.getAll();
	}
	public void chnageSta(Integer bbs_sta ,Integer bbs_id) {
		dao.changeSta(bbs_sta, bbs_id);
	}
}
