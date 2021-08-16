package com.pt.model;

import java.util.List;

public interface PtDAO_interface {
	// 新增追蹤
	public void insertTrk(PtVO ptVO);
	//移除追蹤
	public void deleteTrk(Integer pt_memid,Integer pt_pdid);
	
	//會員追蹤商品清單
	public List<PtVO> findTrkByMem(Integer pd_memid);
	
	//
	public PtVO findByMemAndTrk(Integer pt_memid,Integer pt_pdid);
	
}
