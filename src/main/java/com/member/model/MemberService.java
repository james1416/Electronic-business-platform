package com.member.model;

import java.util.List;

public class MemberService {
	private MemberDAO_interface dao;
	
	public MemberService() {
		dao = new MemberDAO();
	}
		
		public void insertMem(MemberVO memberVO) {	
			dao.insert(memberVO);
	}
		
		public void updateMem(MemberVO memberVO) {	
			dao.update(memberVO);
	}
		
	//done
	public List<MemberVO> getOne(Integer mem_id) {
		return dao.findMemByPK(mem_id);	
		}
	
	//done
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
	//done
	public List<MemberVO>login(String account, String password){
		return dao.checkMember(account, password);
	}
	//done
	public List<MemberVO> canThisAccBeUsed(String mem_acc) {
		List<MemberVO> list = dao.checkMemAcc(mem_acc);
		return list;
	}

	//done
	public void uploadIconBlob(byte[] mem_pic, String mem_acc) {
		dao.uploadIconBlob(mem_pic, mem_acc);
	}
	//done
	public byte[] getIconBlob(String mem_acc){
		return dao.getIconBlob(mem_acc);
	}
	//done
	public void insertDefaultIcon(byte[]mem_pic, String mem_acc) {
		dao.InsertDefaultIcon(mem_pic, mem_acc);
	}
	public void updateMemPW(Integer mem_id, String mem_pw) {
		dao.updateMemPW(mem_id, mem_pw);
	}
	public List<MemberVO> checkMemPW(String mem_acc, String mem_pw){
		return dao.checkMemPW(mem_acc, mem_pw);
	}
	
	public Integer checkEle(Integer mem_id) {
		return dao.checkEle(mem_id);
	}
	
	public void changeMemberStatus(String mem_acc) {
		dao.changeMemberStatus(mem_acc);
	}
	
	public void changeStaTo0(Integer mem_sta,Integer mem_id) {
		dao.changeStaTo0(mem_sta ,mem_id);
	}
	public List<MemberVO> checkEmail(String mem_email){
		return dao.checkEmail(mem_email);
	}
    public void changeStaTo1(String mem_acc) {
        dao.changeStaTo1(mem_acc);
    }
    
    public MemberVO getOnee(Integer memid) {
		return dao.getOne(memid);
	}
	
}
