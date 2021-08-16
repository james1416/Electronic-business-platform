package com.bptrackdetail.model;

import java.util.List;

import com.bpbsreport.model.BpBsReportDAO;

public class BpTrackDetailService {
	private BpTrackDetail_interface dao;
	
	public BpTrackDetailService() {
		dao = new BpTrackDetailDAO();
	}
	
	public BpTrackDetailVO addbtd(Integer tr_bpid,Integer tr_memid) {
		BpTrackDetailVO btdVO = new BpTrackDetailVO();
		btdVO.setTr_bpid(tr_bpid);
		btdVO.setTr_memid(tr_memid);
		dao.insert(btdVO);
		return btdVO;
	}
	
	public void deletebtd(Integer tr_bpid,Integer tr_memid) {
		dao.delete(tr_bpid,tr_memid);
	}
	
	public BpTrackDetailVO getOnebtd(Integer tr_memid) {
		return dao.findByPrimaryKey(tr_memid);
	}
	public List<BpTrackDetailVO> getAll(){
		return dao.getAll();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
