package com.productReport.model;

import java.util.List;

public interface ProductReportDAO_interface {
	
	List<ProductReportVO> getallbyADMIN(Integer start , Integer rows);
	
	List<ProductReportVO> getallbySTA(Integer pr_sta ,Integer start , Integer rows);
	
	List<ProductReportVO> getall(Integer pr_memid);

	void add(Integer pr_memid , Integer pr_pid, String pr_content);
	
	void del(Integer pr_memid , Integer pr_pid );
	
	void updata(Integer pr_id , Integer pr_sta);
	
	ProductReportVO getone(Integer pr_id);
}
