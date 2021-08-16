package com.productReport.model;

import java.util.List;

public class ProductReportService {

	private ProductReportDAO_interface dao;
	
	public ProductReportService() {
		dao = new ProductReportDAO();
	}
	
	public List<ProductReportVO> getall(Integer pr_memid){
		return dao.getall(pr_memid);
	}
	
	public void add(Integer rp_memid ,Integer rp_pid ,String rp_content) {
		dao.add(rp_memid, rp_pid, rp_content);
	}
	public void del(Integer pr_memid , Integer pr_pid) {
		dao.del(pr_memid, pr_pid);
	}
	
	public List<ProductReportVO> getallbyADMIN(Integer start , Integer rows){
		return dao.getallbyADMIN(start , rows);
	}
	
	public  List<ProductReportVO> getallbySTA(Integer pr_sta , Integer start , Integer rows){
		return dao.getallbySTA(pr_sta, start, rows);
	}
	
	public void updata(Integer pr_id , Integer pr_sta) {
		dao.updata(pr_id, pr_sta);
	}
	
	public ProductReportVO getone(Integer pr_id) {
		return dao.getone(pr_id);
	}
}
