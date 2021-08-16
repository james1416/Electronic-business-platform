package com.cartList.model;

import java.io.Serializable;

public class CartListVO implements Serializable{
	private Integer cl_memid;
	private Integer cl_pid;
	private Integer cl_pq;
	public CartListVO(Integer cl_memid, Integer cl_pid, Integer cl_pq) {
		super();
		this.cl_memid = cl_memid;
		this.cl_pid = cl_pid;
		this.cl_pq = cl_pq;
	}
	public CartListVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getCl_memid() {
		return cl_memid;
	}
	public void setCl_memid(Integer cl_memid) {
		this.cl_memid = cl_memid;
	}
	public Integer getCl_pid() {
		return cl_pid;
	}
	public void setCl_pid(Integer cl_pid) {
		this.cl_pid = cl_pid;
	}
	public Integer getCl_pq() {
		return cl_pq;
	}
	public void setCl_pq(Integer cl_pq) {
		this.cl_pq = cl_pq;
	}
	
	
}
