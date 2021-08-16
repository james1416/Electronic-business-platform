package com.bpdetail.model;

import java.sql.Connection;
import java.util.*;

public interface BpDetailDAO_interface {
	public void insert (BpDetailVO bpdVO , Connection con); //運用到出價紀錄
	public void update(BpDetailVO bpdVO); //用不到
	public void delete(Integer bpd_id);	//用不到
	public List<BpDetailVO> findByPrimaryKeyfromBmember(Integer bpd_bmemid,Integer start, Integer rows); //買家針對他的出價紀錄做查詢
	public List<BpDetailVO> findByPrimaryKeyfromBPID(Integer bpd_bpid); //關於相關商品的出價紀錄
	public List<BpDetailVO> getAll(); // 看到所有人的總出價紀錄或賣家本身能看 //並無實質意義
	public BpDetailVO getTopPrice(Integer bpd_bpid);
	
}
