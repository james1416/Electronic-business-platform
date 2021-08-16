package com.authority.model;

import java.util.List;

public class AuthorityService {
	private AuthorityDAO_interface dao;
	
	public AuthorityService() {
		dao = new AuthorityDAO();
	}
	
	public List<AuthorityVO> getall(){
		List<AuthorityVO> list = dao.getall();
		return list;
	}
	public void add(String aut_name , String aut_con) {
		AuthorityVO authorityVO= new AuthorityVO();
		authorityVO.setAut_name(aut_name);
		authorityVO.setAut_con(aut_con);
		dao.add(authorityVO);
	}
	public void updata(Integer aut_id , String aut_name , String aut_con) {
		AuthorityVO authorityVO= new AuthorityVO();
		authorityVO.setAut_name(aut_name);
		authorityVO.setAut_con(aut_con);
		authorityVO.setAut_id(aut_id);
		dao.updata(authorityVO);
	}
	public AuthorityVO findone(Integer aut_id) {
		return dao.findone(aut_id);
	}
}
