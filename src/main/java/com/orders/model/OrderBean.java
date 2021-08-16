package com.orders.model;

import java.util.List;

import com.ordersList.model.OrdersListVO;
import com.product.model.ProductVO;

public class OrderBean{
	
	
	OrdersVO ordersVO;
	List<ProductVO> Plist;
	List<OrdersListVO> Olist;
	List<forODimg> ImgList;
	
	
	public OrdersVO getOrdersVO() {
		return ordersVO;
	}
	public void setOrdersVO(OrdersVO ordersVO) {
		this.ordersVO = ordersVO;
	}
	public List<ProductVO> getPlist() {
		return Plist;
	}
	public void setPlist(List<ProductVO> plist) {
		Plist = plist;
	}
	public List<OrdersListVO> getOlist() {
		return Olist;
	}
	public void setOlist(List<OrdersListVO> olist) {
		Olist = olist;
	}
	public List<forODimg> getImgList() {
		return ImgList;
	}
	public void setImgList(List<forODimg> imgList) {
		ImgList = imgList;
	}
	public OrderBean() {
		super();
	}
	
	
	
}

class forODimg{
	private Integer pdid;
	private String pdimg;
	
	public Integer getPdid() {
		return pdid;
	}
	public void setPdid(Integer pdid) {
		this.pdid = pdid;
	}
	public String getPdimg() {
		return pdimg;
	}
	public void setPdimg(String pdimg) {
		this.pdimg = pdimg;
	}
	public forODimg() {
		super();
	}
	
	
}