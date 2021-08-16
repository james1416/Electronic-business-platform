package com.orders.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrdersVO implements Serializable {
	private Integer od_id;
	private Integer od_bmemid;
	private Integer od_smemid;
	private Integer od_price;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp od_date;
	private Integer od_shipping;
	private String od_shipinfo;
	private Integer od_payment;
	private Integer od_sta;
	private String od_notes;
	private String od_comment;
	private Integer od_point;
	private String od_name;
	private String od_tel;
	private Integer od_rating;
	private Integer od_goldflow;
	public Integer getOd_id() {
		return od_id;
	}
	public void setOd_id(Integer od_id) {
		this.od_id = od_id;
	}
	public Integer getOd_bmemid() {
		return od_bmemid;
	}
	public void setOd_bmemid(Integer od_bmemid) {
		this.od_bmemid = od_bmemid;
	}
	public Integer getOd_smemid() {
		return od_smemid;
	}
	public void setOd_smemid(Integer od_smemid) {
		this.od_smemid = od_smemid;
	}
	public Integer getOd_price() {
		return od_price;
	}
	public void setOd_price(Integer od_price) {
		this.od_price = od_price;
	}
	public Timestamp getOd_date() {
		return od_date;
	}
	public void setOd_date(Timestamp od_date) {
		this.od_date = od_date;
	}
	public Integer getOd_shipping() {
		return od_shipping;
	}
	public void setOd_shipping(Integer od_shipping) {
		this.od_shipping = od_shipping;
	}
	public String getOd_shipinfo() {
		return od_shipinfo;
	}
	public void setOd_shipinfo(String od_shipinfo) {
		this.od_shipinfo = od_shipinfo;
	}
	public Integer getOd_payment() {
		return od_payment;
	}
	public void setOd_payment(Integer od_payment) {
		this.od_payment = od_payment;
	}
	public Integer getOd_sta() {
		return od_sta;
	}
	public void setOd_sta(Integer od_sta) {
		this.od_sta = od_sta;
	}
	public String getOd_notes() {
		return od_notes;
	}
	public void setOd_notes(String od_notes) {
		this.od_notes = od_notes;
	}
	public String getOd_comment() {
		return od_comment;
	}
	public void setOd_comment(String od_comment) {
		this.od_comment = od_comment;
	}
	public Integer getOd_point() {
		return od_point;
	}
	public void setOd_point(Integer od_point) {
		this.od_point = od_point;
	}
	public String getOd_name() {
		return od_name;
	}
	public void setOd_name(String od_name) {
		this.od_name = od_name;
	}
	public String getOd_tel() {
		return od_tel;
	}
	public void setOd_tel(String od_tel) {
		this.od_tel = od_tel;
	}
	public Integer getOd_rating() {
		return od_rating;
	}
	public void setOd_rating(Integer od_rating) {
		this.od_rating = od_rating;
	}
	public Integer getOd_goldflow() {
		return od_goldflow;
	}
	public void setOd_goldflow(Integer od_goldflow) {
		this.od_goldflow = od_goldflow;
	}
	public OrdersVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
