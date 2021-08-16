package com.po.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


public class PoVO implements Serializable {
	private Integer po_id;
	private Integer po_bmemid;
	private Integer po_smemid;
	private Integer po_pdid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp po_date;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp po_ship;
	private Integer po_qty;
	private Integer po_price;
	private Integer po_pay;
	private Integer po_sta;
	private String po_comment;
	private Integer po_point;
	private String po_tel;
	private String po_bname;
	private String po_home;
	private Integer po_goldflow;
	private Integer po_iscom;
	
	
	public PoVO() {
		
	}

	
	
	
	public Integer getPo_goldflow() {
		return po_goldflow;
	}


	public void setPo_goldflow(Integer po_goldflow) {
		this.po_goldflow = po_goldflow;
	}




	public Integer getPo_iscom() {
		return po_iscom;
	}




	public void setPo_iscom(Integer po_iscom) {
		this.po_iscom = po_iscom;
	}




	public Integer getPo_id() {
		return po_id;
	}

	public void setPo_id(Integer po_id) {
		this.po_id = po_id;
	}

	public Integer getPo_smemid() {
		return po_smemid;
	}

	public void setPo_smemid(Integer po_smemid) {
		this.po_smemid = po_smemid;
	}

	public Integer getPo_pdid() {
		return po_pdid;
	}

	public void setPo_pdid(Integer po_pdid) {
		this.po_pdid = po_pdid;
	}

	public Timestamp getPo_date() {
		return po_date;
	}

	public void setPo_date(Timestamp po_date) {
		this.po_date = po_date;
	}

	public Timestamp getPo_ship() {
		return po_ship;
	}

	public void setPo_ship(Timestamp po_ship) {
		this.po_ship = po_ship;
	}

	public Integer getPo_qty() {
		return po_qty;
	}

	public void setPo_qty(Integer po_qty) {
		this.po_qty = po_qty;
	}

	public Integer getPo_price() {
		return po_price;
	}

	public void setPo_price(Integer po_price) {
		this.po_price = po_price;
	}

	public Integer getPo_pay() {
		return po_pay;
	}

	public void setPo_pay(Integer po_pay) {
		this.po_pay = po_pay;
	}

	public Integer getPo_sta() {
		return po_sta;
	}

	public void setPo_sta(Integer po_sta) {
		this.po_sta = po_sta;
	}

	public String getPo_comment() {
		return po_comment;
	}

	public void setPo_comment(String po_comment) {
		this.po_comment = po_comment;
	}

	public Integer getPo_point() {
		return po_point;
	}

	public void setPo_point(Integer po_point) {
		this.po_point = po_point;
	}
	
	public Integer getPo_bmemid() {
		return po_bmemid;
	}

	public void setPo_bmemid(Integer po_bmemid) {
		this.po_bmemid = po_bmemid;
	}

	public String getPo_tel() {
		return po_tel;
	}

	public void setPo_tel(String po_tel) {
		this.po_tel = po_tel;
	}

	public String getPo_bname() {
		return po_bname;
	}

	public void setPo_bname(String po_bname) {
		this.po_bname = po_bname;
	}

	public String getPo_home() {
		return po_home;
	}

	public void setPo_home(String po_home) {
		this.po_home = po_home;
	}
	
	
	

}
