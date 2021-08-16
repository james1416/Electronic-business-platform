package com.cartList.model;

import java.sql.Connection;
import java.util.List;

import com.product.model.ProductVO;

public interface CartListDAO_interface {
	List<CartListVO> getall(Integer cl_memid);
	
	void del(Integer cl_memid, Integer cl_pid);
	
	void add(Integer cl_memid, Integer cl_pid);
	
	Integer updata(CartListVO cartListVO , String up_or_down);
	
	boolean check(Integer cl_memid, Integer cl_pid);
	
	List<ProductVO> beOrder(Integer cl_memid);
	
	void delAll(Integer cl_memid , Connection con);
	
	Integer getSum(Integer cl_memid);
}
