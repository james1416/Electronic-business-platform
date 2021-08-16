package com.tracking.model;

import java.sql.Timestamp;

public class TrackingVO {
	private Integer tk_pid;
	private Integer tk_memid;
	private Timestamp tk_date;
	
	public Integer getTk_pid() {
		return tk_pid;
	}
	public void setTk_pid(Integer tk_pid) {
		this.tk_pid = tk_pid;
	}
	public Integer getTk_memid() {
		return tk_memid;
	}
	public void setTk_memid(Integer tk_memid) {
		this.tk_memid = tk_memid;
	}
	public Timestamp getTk_date() {
		return tk_date;
	}
	public void setTk_date(Timestamp tk_date) {
		this.tk_date = tk_date;
	}
	public TrackingVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TrackingVO(Integer tk_pid, Integer tk_memid, Timestamp tk_date) {
		super();
		this.tk_pid = tk_pid;
		this.tk_memid = tk_memid;
		this.tk_date = tk_date;
	}
	
	
	
}
