package com.memberorders.model;

import java.io.Serializable;
import java.util.List;

import com.member.model.MemberVO;
import com.orders.model.OrdersVO;
import com.ordersList.model.OrdersListVO;
import com.product.model.ProductVO;

public class MemberordersVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	
	private OrdersVO ordersVO;
	private OrdersListVO ordersListVO;
	
	private ProductVO productVO;
	
	private MemberVO memberVO;
	
	public MemberordersVO() {
		
	}

	public MemberordersVO(OrdersVO ordersVO, OrdersListVO ordersListVO, ProductVO productVO, MemberVO memberVO) {
		super();
		this.ordersVO = ordersVO;
		this.ordersListVO = ordersListVO;
		this.productVO = productVO;
		this.memberVO = memberVO;
	}

	public OrdersVO getOrdersVO() {
		return ordersVO;
	}

	public void setOrdersVO(OrdersVO ordersVO) {
		this.ordersVO = ordersVO;
	}

	public OrdersListVO getOrdersListVO() {
		return ordersListVO;
	}

	public void setOrdersListVO(OrdersListVO ordersListVO) {
		this.ordersListVO = ordersListVO;
	}

	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	@Override
	public String toString() {
		return "MemberordersVO [ordersVO=" + ordersVO + ", ordersListVO=" + ordersListVO + ", productVO=" + productVO
				+ ", memberVO=" + memberVO + "]";
	}
	
	

}
