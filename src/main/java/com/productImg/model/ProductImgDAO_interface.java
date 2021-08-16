package com.productImg.model;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.Part;

public interface ProductImgDAO_interface {
	void addPIMG(Integer p_id , List<Part> imgs, Connection con);
	
	byte[] getone(Integer pi_pid);
	
	List<ProductImgVO> getall(Integer pi_pid);
	
	void del(Integer pi_id[] , Connection con);
	
	boolean check(Integer pi_pid);
	
}
