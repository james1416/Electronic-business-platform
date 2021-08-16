package com.pc.model;

import java.util.List;

public class PcService {
	
	private PcDAO_interface dao;
	
	public PcService() {
		dao = new PcDAO();
	}
	
	public PcVO getOnePc(Integer pc_id) {
		return dao.findByPc(pc_id);
	}
	
	public List<PcVO> getall(){
		return dao.getall();
	}
}
