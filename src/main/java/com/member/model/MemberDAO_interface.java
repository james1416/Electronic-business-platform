package com.member.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface MemberDAO_interface {
	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public List<MemberVO> findMemByPK(Integer mem_id);
	public void uploadIconBlob(byte[] mem_pic, String mem_acc);
	public byte[] getIconBlob(String mem_acc);
	public List<MemberVO> getAll();
	public List<MemberVO> checkMemAcc(String mem_acc);
	public List<MemberVO> checkMember(String account, String password);
	public void InsertDefaultIcon(byte[] mem_pic, String mem_acc);
	public void updateEleWal(Integer ele_memid, Integer ele_mon, Connection con);
	public void updateMemPW(Integer mem_id, String mem_pw);
	public List<MemberVO> checkMemPW(String mem_acc, String mem_pw);
	public void addWalletByOrders(Integer od_price, Integer od_smemid, Connection con);
	public void minusWalletByOrders(Integer od_price, Integer od_bmemid, Connection con);
	public Integer checkEle(Integer mem_id);
	public void changeMemberStatus(String mem_acc);
	public void addRP(Integer mem_id , Connection con);
	public void changeStaTo0(Integer mem_sta , Integer mem_id);
	public List<MemberVO> checkEmail(String mem_email);
	public void changeStaTo1(String mem_acc);
	public MemberVO getOne(Integer memid);

}
