package com.admin.model;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdminService {
	private AdminDAO_interface dao;
	
	public AdminService() {
		dao = new AdminDAO();
	}
	
	public List<AdminVO> getall(){
		List<AdminVO> list = dao.getall();
		return list;
	}
	public void add(String adminacc , String adminpw , Set<Integer> ChengeAutid) {
		AdminVO adminVO	= new AdminVO();
		adminVO.setAdmin_acc(adminacc);
		adminVO.setAdmin_pw(adminpw);
		dao.add(adminVO , ChengeAutid);
	}
	public void updata(Integer admin_id , String admin_acc , String admin_pw , Integer admin_sta ,Set<Integer>ChengeAutid) {
		AdminVO adminVO = new AdminVO();
		adminVO.setAdmin_id(admin_id);
		adminVO.setAdmin_acc(admin_acc);
		adminVO.setAdmin_pw(admin_pw);
		adminVO.setAdmin_sta(admin_sta);
		dao.updata(adminVO , ChengeAutid);
	}
	public AdminVO find(Integer adminid) {
		return dao.find(adminid);
		
	}
	public AdminVO login(String adminacc , String adminpw) {
		AdminVO adminVO =new AdminVO();
		adminVO.setAdmin_acc(adminacc);
		adminVO.setAdmin_pw(adminpw);
		return dao.login(adminVO);
	}
	public void updateLoginlog(Integer adminid) {
		dao.updataLoginlog(adminid);
	}
	
	public Boolean checkAcc(String adminacc) {
		return dao.checkAcc(adminacc);
	}
	
	public List<AdminVO> search(Map<String,String[]> map){
		return dao.search(map);
	}
}
