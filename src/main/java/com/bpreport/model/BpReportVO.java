package com.bpreport.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BpReportVO implements Serializable{
	private Integer bpr_id ;
	private Integer bpr_memid;
	private Integer bpr_bpid;
	private String bpr_content;
	private Timestamp bpr_date;
	private Integer bpr_sta;
	public Integer getBpr_id() {
		return bpr_id;
	}
	public void setBpr_id(Integer bpr_id) {
		this.bpr_id = bpr_id;
	}
	public Integer getBpr_memid() {
		return bpr_memid;
	}
	public void setBpr_memid(Integer bpr_memid) {
		this.bpr_memid = bpr_memid;
	}
	public Integer getBpr_bpid() {
		return bpr_bpid;
	}
	public void setBpr_bpid(Integer bpr_bpid) {
		this.bpr_bpid = bpr_bpid;
	}
	public String getBpr_content() {
		return bpr_content;
	}
	public void setBpr_content(String bpr_content) {
		this.bpr_content = bpr_content;
	}
	public Timestamp getBpr_date() {
		return bpr_date;
	}
	public void setBpr_date(Timestamp bpr_date) {
		this.bpr_date = bpr_date;
	}
	public Integer getBpr_sta() {
		return bpr_sta;
	}
	public void setBpr_sta(Integer bpr_sta) {
		this.bpr_sta = bpr_sta;
	}
	
	
}
