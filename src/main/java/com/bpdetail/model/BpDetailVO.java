package com.bpdetail.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BpDetailVO {
	private Integer bpd_id ;
	private Integer bpd_bpid;
	private Integer bpd_bmemid;
	private Integer bpd_bpprice;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp bpd_bpdate;
	
	
	public Integer getBpd_id() {
		return bpd_id;
	}
	public void setBpd_id(Integer bpd_id) {
		this.bpd_id = bpd_id;
	}
	public Integer getBpd_bpid() {
		return bpd_bpid;
	}
	public void setBpd_bpid(Integer bpd_bpid) {
		this.bpd_bpid = bpd_bpid;
	}
	public Integer getBpd_bmemid() {
		return bpd_bmemid;
	}
	public void setBpd_bmemid(Integer bpd_bmemid) {
		this.bpd_bmemid = bpd_bmemid;
	}
	public Integer getBpd_bpprice() {
		return bpd_bpprice;
	}
	public void setBpd_bpprice(Integer bpd_bpprice) {
		this.bpd_bpprice = bpd_bpprice;
	}
	public Timestamp getBpd_bpdate() {
		return bpd_bpdate;
	}
	public void setBpd_bpdate(Timestamp bpd_bpdate) {
		this.bpd_bpdate = bpd_bpdate;
	}
	
	
}
