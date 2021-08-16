package com.pr.model;

import java.util.List;



public class PrService {
	private PrDAO_interface dao;
	
	public PrService() {
		dao = new PrDAO();
	}
	
	public List<PrVO> getSelf(Integer pr_bmemid){
		return dao.getSelf(pr_bmemid);
		
	}
	
	public void add(Integer pr_poid , Integer pr_bmemid , Integer pr_smemid , String pr_desc) {
		PrVO prVO = new PrVO();
		prVO.setPr_poid(pr_poid);
		prVO.setPr_bmemid(pr_bmemid);
		prVO.setPr_smemid(pr_smemid);
		prVO.setPr_desc(pr_desc);
		dao.add(prVO);
		
	}
	
}
