package com.electronicwallet.model;

import java.sql.Timestamp;
import java.util.List;

import com.pageBean.PageBean;

public class ElectronicWalletService {
	private ElectronicWalletDAO_interface dao;
	public ElectronicWalletService(){
		dao = new ElectronicWalletDAO();
	}
	
	public List<ElectronicWalletVO> getAll(Integer ele_memid){
		return dao.getAll(ele_memid);
	}
	public void insertNewPayment(Integer ele_memid, Timestamp ele_time, String ele_rec, Integer ele_mon) {
		dao.insertNewPayment(ele_memid, ele_time, ele_rec, ele_mon);
	}
	
	public PageBean<ElectronicWalletVO> pageQuery(Integer ele_memid, Integer currentPage, Integer pageSize){
		PageBean<ElectronicWalletVO> pb = new PageBean<ElectronicWalletVO>();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		System.out.println("hi from service");
		Integer totalCount = dao.findTotalCount(ele_memid);
		pb.setTotalCount(totalCount);
		 
		Integer start = (currentPage-1)*pageSize; 
		
		List<ElectronicWalletVO> list = dao.findByPage(ele_memid, start, pageSize);		
		pb.setList(list);
		 
		
		
		Integer totalPage = totalCount % pageSize==0 ? (totalCount/pageSize) :(totalCount/pageSize)+1;
		pb.setTotalPage(totalPage);
		return pb;
	}
	
	public ElectronicWalletVO getOneLog(Integer ele_memid, Integer ele_id) {
		return dao.getOneLog(ele_memid, ele_id);
	}
	
}
