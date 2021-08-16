package com.pr.model;

import java.util.List;

public interface PrDAO_interface {
	
	List<PrVO> getAll();
	
	List<PrVO> getSelf(Integer pr_bmemid);
	
	//新增一筆檢舉
	void add(PrVO prVO);
	
	//經由檢舉訂單編號 更改狀態
	void update(Integer pr_id);
}
