package com.admin_authority.model;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public interface AdminAuthorityDAO_interface {
	List<AdminAuthorityVO> getall();
	
	List<AdminAuthorityVO> getone(Integer adminid);
	
	void add(Integer adminid, Set<Integer> ChengeAutid, Set<Integer> HaveAutID, Connection con);
	
	void del(Integer adminid, Set<Integer> ChengeAutid, Set<Integer> HaveAutID, Connection con);
	
	void find(Integer adminid ,Set<Integer> ChengeAutid, Connection con);
}
