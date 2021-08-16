package com.admin.model;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface AdminDAO_interface {
		List<AdminVO> getall();
		AdminVO find(Integer adminid);
		void add(AdminVO adminVO ,Set<Integer> ChengeAutid);
		void updata(AdminVO adminVO ,Set<Integer> ChengeAutid);
		AdminVO login(AdminVO adminVO);
		void updataLoginlog(Integer adminid);
		Boolean checkAcc(String adminacc);
		List<AdminVO> search(Map<String, String[]> map);
		//查權限編號
	}

