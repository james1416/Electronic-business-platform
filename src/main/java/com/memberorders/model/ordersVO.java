package com.memberorders.model;

import java.util.List;

import com.member.model.MemberVO;
import com.orders.model.OrdersVO;
import com.ordersList.model.OrdersListVO;
import com.product.model.ProductVO;

public class ordersVO {

	
	
//	我們一筆訂單即為一個VO 也就是說， VO內放要放一張訂單的所有包含的資訊

//	通常一個訂單裡面只會有他 "自己"  ， 即為是一個訂單，所以這邊用OrdersVO
 	private OrdersVO ordersVO;
 	
 	
//	一張訂單會有幾個會員資料呢？通常是會有 (賣家) 的會員資料 如果要拿買家比較沒有意義，因為目前的情況是這買家想要查自己的訂單
// 	就沒有必要再拿自己的資訊了。
 	
//	其實這個可以不用，因為一些基本資訊訂單就會帶有了，當然要的話也OK
 	private MemberVO memberVO;
 	
//	訂單內會有幾筆明細呢？ 多筆 ，而如果我們宣告為	OrdersListVO 的話
//	他就只能放一筆而已，所以我們這邊會使用List<>來裝著他的OrdersListVO
	private List<OrdersListVO> ollist;

	
//	訂單內會有幾筆商品呢？ 多筆 ，而如果我們宣告為	productVO 的話
//	他就只能放一筆而已，所以我們這邊會使用List<>來裝著他的productVO
	private List<ProductVO> plist;


	public OrdersVO getOrdersVO() {
		return ordersVO;
	}


	public void setOrdersVO(OrdersVO ordersVO) {
		this.ordersVO = ordersVO;
	}


	public MemberVO getMemberVO() {
		return memberVO;
	}


	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}


	public List<OrdersListVO> getOllist() {
		return ollist;
	}


	public void setOllist(List<OrdersListVO> ollist) {
		this.ollist = ollist;
	}


	public List<ProductVO> getPlist() {
		return plist;
	}


	public void setPlist(List<ProductVO> plist) {
		this.plist = plist;
	}


	public ordersVO() {
		super();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
