package com.ppdr.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PpdrVO implements Serializable {
	private Integer ppdr_id;
	private Integer ppdr_memid;
	private Integer ppdr_pid;
	private String ppdr_content;
	private Timestamp ppdr_date;
	private Integer ppdr_sta;
	
	public PpdrVO() {
		
	}

	public Integer getPpdr_id() {
		return ppdr_id;
	}

	public void setPpdr_id(Integer ppdr_id) {
		this.ppdr_id = ppdr_id;
	}

	public Integer getPpdr_memid() {
		return ppdr_memid;
	}

	public void setPpdr_memid(Integer ppdr_memid) {
		this.ppdr_memid = ppdr_memid;
	}

	public Integer getPpdr_pid() {
		return ppdr_pid;
	}

	public void setPpdr_pid(Integer ppdr_pid) {
		this.ppdr_pid = ppdr_pid;
	}

	public String getPpdr_content() {
		return ppdr_content;
	}

	public void setPpdr_content(String ppdr_content) {
		this.ppdr_content = ppdr_content;
	}

	public Timestamp getPpdr_date() {
		return ppdr_date;
	}

	public void setPpdr_date(Timestamp ppdr_date) {
		this.ppdr_date = ppdr_date;
	}

	public Integer getPpdr_sta() {
		return ppdr_sta;
	}

	public void setPpdr_sta(Integer ppdr_sta) {
		this.ppdr_sta = ppdr_sta;
	}
	
	
}
