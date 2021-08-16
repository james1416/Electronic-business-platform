package com.product.model;

import java.io.Serializable;

public class ProductVO implements Serializable{
	private Integer p_id;
	private Integer p_memid;
	private Integer p_cgid;
	private String p_name;
	private Integer p_price;
	private Integer p_stock;
	private Integer p_sl;
	private String p_desc;
	private Integer p_sta;
	public ProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductVO(Integer p_id, Integer p_memid, Integer p_cgid, String p_name, Integer p_price, Integer p_stock,
			Integer p_sl, String p_desc, Integer p_sta) {
		super();
		this.p_id = p_id;
		this.p_memid = p_memid;
		this.p_cgid = p_cgid;
		this.p_name = p_name;
		this.p_price = p_price;
		this.p_stock = p_stock;
		this.p_sl = p_sl;
		this.p_desc = p_desc;
		this.p_sta = p_sta;
	}
	public Integer getP_id() {
		return p_id;
	}
	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}
	public Integer getP_memid() {
		return p_memid;
	}
	public void setP_memid(Integer p_memid) {
		this.p_memid = p_memid;
	}
	public Integer getP_cgid() {
		return p_cgid;
	}
	public void setP_cgid(Integer p_cgid) {
		this.p_cgid = p_cgid;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public Integer getP_price() {
		return p_price;
	}
	public void setP_price(Integer p_price) {
		this.p_price = p_price;
	}
	public Integer getP_stock() {
		return p_stock;
	}
	public void setP_stock(Integer p_stock) {
		this.p_stock = p_stock;
	}
	public Integer getP_sl() {
		return p_sl;
	}
	public void setP_sl(Integer p_sl) {
		this.p_sl = p_sl;
	}
	public String getP_desc() {
		return p_desc;
	}
	public void setP_desc(String p_desc) {
		this.p_desc = p_desc;
	}
	public Integer getP_sta() {
		return p_sta;
	}
	public void setP_sta(Integer p_sta) {
		this.p_sta = p_sta;
	}
	
	
}
