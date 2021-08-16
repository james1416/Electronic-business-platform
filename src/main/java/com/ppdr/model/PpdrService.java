package com.ppdr.model;

import java.util.List;



public class PpdrService {
	
private PpdrDAO_interface dao;
	
	public PpdrService() {
		dao = new PpdrDAO();
	}
	
	public List<PpdrVO> getall(Integer ppdr_memid){
		return dao.getall(ppdr_memid);
	}
	
	public void add(Integer ppdr_memid ,Integer ppdr_pid ,String ppdr_content) {
		dao.add(ppdr_memid, ppdr_pid, ppdr_content);
	}

}
