package com.admin_authority.model;

import java.util.List;
import java.util.Set;

public class AdminAuthorityService {
	private AdminAuthorityDAO_interface dao ;
	public AdminAuthorityService(){
		dao = new AdminAuthorityDAO();
	}
	public List<AdminAuthorityVO> getall(){
		List<AdminAuthorityVO> list = dao.getall();
		return list;
	}
	public List<AdminAuthorityVO> getone(Integer adminid){
		List<AdminAuthorityVO> list = dao.getone(adminid);
		return list;
	}
	//可能用不到了 因為已經整合進去單一裡面 NULL值我先隨便亂塞以免抱錯
	public void add(Integer adminid , Set<Integer> ChengeAutid) {
		dao.find(adminid, ChengeAutid, null);
		
		
	}
}
