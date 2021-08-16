package com.ordersList.model;

import java.io.Serializable;

public class OrdersListVO implements Serializable {
	private Integer ol_odid;
	private Integer ol_pid;
	private Integer ol_pq;
	private Integer ol_price;
	public OrdersListVO() {
		super();
	}
	public OrdersListVO(Integer ol_odid, Integer ol_pid, Integer ol_pq, Integer ol_price) {
		super();
		this.ol_odid = ol_odid;
		this.ol_pid = ol_pid;
		this.ol_pq = ol_pq;
		this.ol_price = ol_price;
	}
	public Integer getOl_odid() {
		return ol_odid;
	}
	public void setOl_odid(Integer ol_odid) {
		this.ol_odid = ol_odid;
	}
	public Integer getOl_pid() {
		return ol_pid;
	}
	public void setOl_pid(Integer ol_pid) {
		this.ol_pid = ol_pid;
	}
	public Integer getOl_pq() {
		return ol_pq;
	}
	public void setOl_pq(Integer ol_pq) {
		this.ol_pq = ol_pq;
	}
	public Integer getOl_price() {
		return ol_price;
	}
	public void setOl_price(Integer ol_price) {
		this.ol_price = ol_price;
	}
	
	
	
}
