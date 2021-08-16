package com.ordersList.model;

import java.sql.Connection;
import java.util.List;

import com.product.model.ProductVO;

public interface OrdersListDAO_interface {
	void add(ProductVO productVO , Connection con ,Integer odid);
	
	List<OrdersListVO> getall(Integer ol_odid);
}
