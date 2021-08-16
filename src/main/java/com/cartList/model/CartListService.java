package com.cartList.model;

import java.util.List;

import com.product.model.ProductVO;

public class CartListService {
	public CartListDAO_interface dao;
	
	public CartListService() {
		dao = new CartListDAO();
	}
	
	public List<CartListVO> getall(Integer cl_memid){
		return dao.getall(cl_memid);
	}
	
	public void add(Integer cl_memid, Integer cl_pid) {
		dao.add(cl_memid, cl_pid);
	}
	
	public void del(Integer cl_memid, Integer cl_pid) {
		dao.del(cl_memid, cl_pid);
	}
	
	public Integer updata(Integer cl_memid, Integer cl_pid ,String up_or_down) {
		CartListVO cartListVO =new CartListVO();
		cartListVO.setCl_memid(cl_memid);
		cartListVO.setCl_pid(cl_pid);
		return dao.updata(cartListVO , up_or_down);
		
	}
	
	public boolean check(Integer cl_memid, Integer cl_pid) {
		return dao.check(cl_memid, cl_pid);
	}
	public List<ProductVO> beOrder(Integer cl_memid) {
		return dao.beOrder(cl_memid);
	}
	
	public Integer getSum(Integer cl_memid) {
		return dao.getSum(cl_memid);
	}
	
}
