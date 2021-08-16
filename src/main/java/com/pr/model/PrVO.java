package com.pr.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class PrVO implements Serializable{
	private Integer pr_id;
	private Integer pr_poid;
	// bmemid=被檢舉人
	private Integer pr_bmemid;
	// smemid=檢舉人
	private Integer pr_smemid;
	private Timestamp pr_date;
	private String pr_desc;
	private Integer pr_sta;
	
	public PrVO() {
		
	}

	public Integer getPr_id() {
		return pr_id;
	}

	public void setPr_id(Integer pr_id) {
		this.pr_id = pr_id;
	}

	public Integer getPr_poid() {
		return pr_poid;
	}

	public void setPr_poid(Integer pr_poid) {
		this.pr_poid = pr_poid;
	}

	public Integer getPr_bmemid() {
		return pr_bmemid;
	}

	public void setPr_bmemid(Integer pr_bmemid) {
		this.pr_bmemid = pr_bmemid;
	}

	public Integer getPr_smemid() {
		return pr_smemid;
	}

	public void setPr_smemid(Integer pr_smemid) {
		this.pr_smemid = pr_smemid;
	}

	public Timestamp getPr_date() {
		return pr_date;
	}

	public void setPr_date(Timestamp pr_date) {
		this.pr_date = pr_date;
	}

	public String getPr_desc() {
		return pr_desc;
	}

	public void setPr_desc(String pr_desc) {
		this.pr_desc = pr_desc;
	}

	public Integer getPr_sta() {
		return pr_sta;
	}

	public void setPr_sta(Integer pr_sta) {
		this.pr_sta = pr_sta;
	}
	
	

}
