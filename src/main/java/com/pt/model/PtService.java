package com.pt.model;

import java.util.List;

public class PtService {
	private PtDAO_interface dao;
	
	public PtService() {
		dao = new PtDAO();
	}
	
	//新增預購
	public void addPt(Integer pt_memid,Integer pt_pdid) {
		PtVO ptVO = new PtVO();		
		ptVO.setPt_memid(pt_memid);
		ptVO.setPt_pdid(pt_pdid);
		dao.insertTrk(ptVO);
	}
	//拿到一個會員的追蹤
	public List<PtVO> getOneMEM(Integer pt_memid) {
		return dao.findTrkByMem(pt_memid);
	}
	
	public void deletePt(Integer pt_memid,Integer pt_pdid) {
		dao.deleteTrk(pt_memid, pt_pdid);
	}
	
	
	//判斷是否收藏
	public boolean  isTracking(Integer pt_memid , Integer pt_pdid) {
		PtVO ptVO = dao.findByMemAndTrk(pt_memid, pt_pdid);
		
		//如果有值則為有收藏(true)
		return ptVO != null; 
	}   
}
