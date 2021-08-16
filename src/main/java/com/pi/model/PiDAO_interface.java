package com.pi.model;

import java.sql.Connection;
import java.util.*;

import javax.servlet.http.Part;

public interface PiDAO_interface {
	
	
	//新增圖片
	public void addPIMG(Integer pd_id,List<Part> imgs,Connection con);
	
	public boolean check(Integer pi_pdid);
	
	public byte[] getone(Integer pi_pdid);
	
	public List<PiVO> getall(Integer pi_pdid);
	
	void del(Integer pi_imgid[],Connection con);
	
}
