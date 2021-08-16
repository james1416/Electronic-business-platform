package com.notice.model;

import java.util.List;

public class NoticeService {
	private NoticeDAO_interface dao;
	
	public NoticeService() {
		dao = new NoticeDAO();
	}
	
	public List<NoticeVO> getAllNotice(Integer nt_memid){
		
		return dao.getall(nt_memid);		
	}
	
	public int deleteOneNotice(Integer mem_id) {
		
		return dao.deleteNotice(mem_id);
		
	}
	
	public Integer getcount(Integer mem_id) {
		return dao.getcount(mem_id);
	}
	
	public void changeViewTo1(Integer nt_id) {
		dao.changeViewTo1(nt_id);
	}
	
}
