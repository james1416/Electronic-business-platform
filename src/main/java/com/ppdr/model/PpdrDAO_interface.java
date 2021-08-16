package com.ppdr.model;

import java.util.List;

public interface PpdrDAO_interface {
	
	//取得自己全部被檢舉的商品
	List<PpdrVO> getall(Integer ppdr_memid);
	
	//檢舉一項商品
	void add(Integer ppdr_memid , Integer ppdr_pdid, String ppdr_content);
	
	

}
