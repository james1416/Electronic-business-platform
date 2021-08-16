package com.ordersList.model;

import java.util.List;

public class OrdersListService {
	
	private OrdersListDAO_interface dao ;
	
	public OrdersListService() {
		dao = new OrdersListDAO();
	}
	
	public List<OrdersListVO> getall(Integer ol_odid) {
		return dao.getall(ol_odid);
	}
}
