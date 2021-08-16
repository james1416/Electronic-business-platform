package com.bpdetail.model;

import java.sql.Timestamp;
import java.util.List;

public class BpDetailService {
	private BpDetailDAO_interface dao;
	
	public BpDetailService() {
		dao = new BpDetailDAO();
	}
//	被交易取代
//	public BpDetailVO addBpDetail(Integer bpd_bpid,Integer bpd_bmemid,Integer bpd_bpprice,Timestamp bpd_bpdate) {
//		BpDetailVO bpdVO = new BpDetailVO();
//		bpdVO.setBpd_bpid(bpd_bpid);
//		bpdVO.setBpd_bmemid(bpd_bmemid);
//		bpdVO.setBpd_bpprice(bpd_bpprice);
//		bpdVO.setBpd_bpdate(bpd_bpdate);
//		dao.insert(bpdVO);
//		
//		return bpdVO;
//	}
	//依據商品找到相關商品的出價紀錄
	public List<BpDetailVO> getOnebpdfromBpid(Integer bpd_bpid) {
		return dao.findByPrimaryKeyfromBPID(bpd_bpid);
	}
	//依據買家本身找到自身的出價紀錄
	public List<BpDetailVO> getOnebpdfromBpbmemid(Integer bpd_bmemid,Integer start, Integer rows) {
		return dao.findByPrimaryKeyfromBmember(bpd_bmemid, start, rows);
	}
	public List<BpDetailVO> getAll(){
		return dao.getAll();
	}
	
}
