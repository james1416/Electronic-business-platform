package com.bpbsreport.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BpBsReportVO implements Serializable{
	private Integer bbs_id ;
	private Integer bbs_bpid;
	private Integer bbs_bmemid;
	private Integer bbs_smemid;
	private Timestamp bbs_date;
	private String bbs_ms;
	private Integer bbs_sta;
	
	
	public Integer getBbs_id() {
		return bbs_id;
	}
	public void setBbs_id(Integer bbs_id) {
		this.bbs_id = bbs_id;
	}
	public Integer getBbs_bpid() {
		return bbs_bpid;
	}
	public void setBbs_bpid(Integer bbs_bpid) {
		this.bbs_bpid = bbs_bpid;
	}
	public Integer getBbs_bmemid() {
		return bbs_bmemid;
	}
	public void setBbs_bmemid(Integer bbs_bmemid) {
		this.bbs_bmemid = bbs_bmemid;
	}
	public Integer getBbs_smemid() {
		return bbs_smemid;
	}
	public void setBbs_smemid(Integer bbs_smemid) {
		this.bbs_smemid = bbs_smemid;
	}
	public Timestamp getBbs_date() {
		return bbs_date;
	}
	public void setBbs_date(Timestamp bbs_date) {
		this.bbs_date = bbs_date;
	}
	public String getBbs_ms() {
		return bbs_ms;
	}
	public void setBbs_ms(String bbs_ms) {
		this.bbs_ms = bbs_ms;
	}
	public Integer getBbs_sta() {
		return bbs_sta;
	}
	public void setBbs_sta(Integer bbs_sta) {
		this.bbs_sta = bbs_sta;
	}
	
	
}
