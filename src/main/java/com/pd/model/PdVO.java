package com.pd.model;
import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
	

public class PdVO implements Serializable {
	
	 private Integer pd_id;
	 private Integer pd_smemid;
	 private Integer pd_pcid;
	 private String pd_name;
	 private Integer pd_price;
	 private Integer pd_no;
	 private String pd_desc;
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	 private Timestamp pd_sdate;
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	 private Timestamp pd_edate;
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	 private Timestamp pd_spdate;
	 private Integer pd_sta;
	 
	public PdVO() {
		
	}

	public Integer getPd_id() {
		return pd_id;
	}

	public void setPd_id(Integer pd_id) {
		this.pd_id = pd_id;
	}

	public Integer getPd_smemid() {
		return pd_smemid;
	}

	public void setPd_smemid(Integer pd_smemid) {
		this.pd_smemid = pd_smemid;
	}

	public Integer getPd_pcid() {
		return pd_pcid;
	}

	public void setPd_pcid(Integer pd_pcid) {
		this.pd_pcid = pd_pcid;
	}

	public String getPd_name() {
		return pd_name;
	}

	public void setPd_name(String pd_name) {
		this.pd_name = pd_name;
	}

	public Integer getPd_price() {
		return pd_price;
	}

	public void setPd_price(Integer pd_price) {
		this.pd_price = pd_price;
	}

	public Integer getPd_no() {
		return pd_no;
	}

	public void setPd_no(Integer pd_no) {
		this.pd_no = pd_no;
	}

	public String getPd_desc() {
		return pd_desc;
	}

	public void setPd_desc(String pd_desc) {
		this.pd_desc = pd_desc;
	}



	public Timestamp getPd_sdate() {
		return pd_sdate;
	}

	public void setPd_sdate(Timestamp pd_sdate) {
		this.pd_sdate = pd_sdate;
	}

	public Timestamp getPd_edate() {
		return pd_edate;
	}

	public void setPd_edate(Timestamp pd_edate) {
		this.pd_edate = pd_edate;
	}

	public Timestamp getPd_spdate() {
		return pd_spdate;
	}

	public void setPd_spdate(Timestamp pd_spdate) {
		this.pd_spdate = pd_spdate;
	}
	
	public Integer getPd_sta() {
		return pd_sta;
	}

	public void setPd_sta(Integer pd_sta) {
		this.pd_sta = pd_sta;
	}

	@Override
	public String toString() {
		return "PdVO [pd_id=" + pd_id + ", pd_smemid=" + pd_smemid + ", pd_pcid=" + pd_pcid + ", pd_name=" + pd_name
				+ ", pd_price=" + pd_price + ", pd_no=" + pd_no + ", pd_desc=" + pd_desc + ", pd_sdate=" + pd_sdate
				+ ", pd_edate=" + pd_edate + ", pd_spdate=" + pd_spdate + ", pd_sta=" + pd_sta + "]";
	}
	

}