package com.electronicwallet.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ElectronicWalletVO {
	private Integer ele_id;
	private Integer ele_memid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp ele_time;
	private String ele_rec;
	private Integer ele_mon;
	
	
	public ElectronicWalletVO() {
		
	}
	
	public ElectronicWalletVO(Integer ele_id, Integer ele_memid, Timestamp ele_time, String ele_rec, Integer ele_mon) {
		super();
		this.ele_id = ele_id;
		this.ele_memid = ele_memid;
		this.ele_time = ele_time;
		this.ele_rec = ele_rec;
		this.ele_mon = ele_mon;
	}
	public Integer getEle_id() {
		return ele_id;
	}
	public void setEle_id(Integer ele_id) {
		this.ele_id = ele_id;
	}
	public Integer getEle_memid() {
		return ele_memid;
	}
	public void setEle_memid(Integer ele_memid) {
		this.ele_memid = ele_memid;
	}
	public Timestamp getEle_time() {
		return ele_time;
	}
	public void setEle_time(Timestamp ele_time) {
		this.ele_time = ele_time;
	}
	public String getEle_rec() {
		return ele_rec;
	}
	public void setEle_rec(String ele_rec) {
		this.ele_rec = ele_rec;
	}
	public Integer getEle_mon() {
		return ele_mon;
	}
	public void setEle_mon(Integer ele_mon) {
		this.ele_mon = ele_mon;
	}
	@Override
	public String toString() {
		return "ElectronicWalletVO [ele_id=" + ele_id + ", ele_memid=" + ele_memid + ", ele_time=" + ele_time
				+ ", ele_rec=" + ele_rec + ", ele_mon=" + ele_mon + "]";
	}
	
	
}
