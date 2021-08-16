package com.productReport.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductReportVO implements Serializable{
	private Integer pr_id;
	private Integer pr_memid;
	private Integer pr_pid;
	private String pr_content;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp pr_date;
	private Integer pr_sta;
	public ProductReportVO() {
		super();
	}
	public ProductReportVO(Integer pr_id, Integer pr_memid, Integer pr_pid, String pr_content, Timestamp pr_date,
			Integer pr_sta) {
		super();
		this.pr_id = pr_id;
		this.pr_memid = pr_memid;
		this.pr_pid = pr_pid;
		this.pr_content = pr_content;
		this.pr_date = pr_date;
		this.pr_sta = pr_sta;
	}
	public Integer getPr_id() {
		return pr_id;
	}
	public void setPr_id(Integer pr_id) {
		this.pr_id = pr_id;
	}
	public Integer getPr_memid() {
		return pr_memid;
	}
	public void setPr_memid(Integer pr_memid) {
		this.pr_memid = pr_memid;
	}
	public Integer getPr_pid() {
		return pr_pid;
	}
	public void setPr_pid(Integer pr_pid) {
		this.pr_pid = pr_pid;
	}
	public String getPr_content() {
		return pr_content;
	}
	public void setPr_content(String pr_content) {
		this.pr_content = pr_content;
	}
	public Timestamp getPr_date() {
		return pr_date;
	}
	public void setPr_date(Timestamp pr_date) {
		this.pr_date = pr_date;
	}
	public Integer getPr_sta() {
		return pr_sta;
	}
	public void setPr_sta(Integer pr_sta) {
		this.pr_sta = pr_sta;
	}
	
	
}
