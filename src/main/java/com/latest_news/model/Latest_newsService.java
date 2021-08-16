package com.latest_news.model;

import java.sql.Timestamp;
import java.util.List;

import com.pageBean.PageBean;

public class Latest_newsService {
	private Latest_newsDAO_interface dao;
	public Latest_newsService() {
		dao = new Latest_newsDAO();
	}
	
	public List<Latest_newsVO> getAll(Integer start , Integer rows){
		return dao.getAllLN(start , rows);
	}
	public void deleteOne(Integer ln_id) {
		dao.deleteLN(ln_id);
	}
	public Latest_newsVO updatelnVO(String ln_con, Integer ln_id , Integer ln_sta) {
		Latest_newsVO lnVO = new Latest_newsVO();
		lnVO.setLn_sta(ln_sta);
		lnVO.setLn_con(ln_con);
		lnVO.setLn_id(ln_id);
		dao.updateLN(lnVO);
		return lnVO;
		
	}
	public List<Latest_newsVO> getOneLN(Integer ln_id){
		return dao.listOneLN(ln_id);
	}
	public void addLN(String ln_con) {
		dao.addLN(ln_con);
	}
	public PageBean<Latest_newsVO> pageQuery(Integer currentPage, Integer pageSize){
		PageBean<Latest_newsVO> pb = new PageBean<Latest_newsVO>();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		
		Integer totalCount = dao.findTotalCount();
		pb.setTotalCount(totalCount);
		 
		Integer start = (currentPage-1)*pageSize; //開始的紀錄數
		
		List<Latest_newsVO> list = dao.findByPage(start, pageSize);		
		pb.setList(list);
		 
		//設置總頁數 = 總紀錄數/每頁顯示條數
		
		Integer totalPage = totalCount%pageSize==0 ? pageSize:(totalCount/pageSize)+1;
		pb.setTotalPage(totalPage);
		return pb;
	} 
}
