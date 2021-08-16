package com.product.model;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

import com.cartList.model.CartListVO;

public interface ProductDAO_interface {
	
	// 叫全部
	List<ProductVO> getall();
	// 叫 "該" 會員的商品
	List<ProductVO> getSelf(Integer p_memid);
	// 找一個商品
	ProductVO findPD (Integer p_id);
	//修改商品
	void updataPD(ProductVO productVO , List<Part> list , Integer del[]);
	//新增商品
	void addPD(ProductVO productVO , List<Part> imgs);
	//
	Integer findPDforAJAX(Integer p_id);
	
	//
	List<ProductVO> getCartPD(List<CartListVO> cartList);
	
	//
	void PDSell(Integer p_id , Integer p_stock , Connection con);
	
	Integer findTotalCount();
	
	List<ProductVO> findByPage(Integer start, Integer rows);
	
	List<ProductVO> findMyPDbyPage(Integer mem_id , Integer start, Integer rows);

	void updataSTA(Integer p_id ,Connection con);
	
	List<ProductVO> searchMyPDbyPage(String search , Integer p_cgid , Integer start, Integer rows);
	
	Integer searchCount(String search , Integer p_cgid );
	
	Set<String> inputPoint(String input , Integer p_cgid);
	
}
