package com.notice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NoticeVO {
	private Integer nt_id;
	private Integer nt_memid;
	private String nt_text;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date nt_time;
	private Integer nt_view;
	
	public NoticeVO() {
		
	}
	
	public NoticeVO(Integer nt_id, Integer nt_memid, String nt_text, Date nt_time, Integer nt_view) {
		super();
		this.nt_id = nt_id;
		this.nt_memid = nt_memid;
		this.nt_text = nt_text;
		this.nt_time = nt_time;
		this.nt_view = nt_view;
	}

	public Integer getNt_id() {
		return nt_id;
	}

	public void setNt_id(Integer nt_id) {
		this.nt_id = nt_id;
	}

	public Integer getNt_memid() {
		return nt_memid;
	}

	public void setNt_memid(Integer nt_memid) {
		this.nt_memid = nt_memid;
	}

	public String getNt_text() {
		return nt_text;
	}

	public void setNt_text(String nt_text) {
		this.nt_text = nt_text;
	}

	public Date getNt_time() {
		return nt_time;
	}

	public void setNt_time(Date nt_time) {
		this.nt_time = nt_time;
	}

	public Integer getNt_view() {
		return nt_view;
	}

	public void setNt_view(Integer nt_view) {
		this.nt_view = nt_view;
	}


	@Override
	public String toString() {
		return "NoticeVO [nt_id=" + nt_id + ", nt_memid=" + nt_memid + ", nt_text=" + nt_text + ", nt_time=" + nt_time
				+ ", nt_view=" + nt_view + "]";
	}
	
	
}
