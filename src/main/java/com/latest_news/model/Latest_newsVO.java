package com.latest_news.model;

import java.sql.*;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Latest_newsVO {
	private Integer ln_id;
	private String ln_con;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Timestamp ln_tsp;
	private Integer ln_sta;
	
	public Latest_newsVO() {
		
	}
	
	public Latest_newsVO(Integer ln_id, String ln_con, Timestamp ln_tsp, Integer ln_sta) {
		super();
		this.ln_id = ln_id;
		this.ln_con = ln_con;
		this.ln_tsp = ln_tsp;
		this.ln_sta = ln_sta;
	}
	public Integer getLn_id() {
		return ln_id;
	}
	public void setLn_id(Integer ln_id) {
		this.ln_id = ln_id;
	}
	public String getLn_con() {
		return ln_con;
	}
	public void setLn_con(String ln_con) {
		this.ln_con = ln_con;
	}
	public Timestamp getLn_tsp() {
		return ln_tsp;
	}
	public void setLn_tsp(Timestamp ln_tsp) {
		this.ln_tsp = ln_tsp;
	}
	public Integer getLn_sta() {
		return ln_sta;
	}
	public void setLn_sta(Integer ln_sta) {
		this.ln_sta = ln_sta;
	}
	@Override
	public String toString() {
		return "Latest_newsVO [ln_id=" + ln_id + ", ln_con=" + ln_con + ", ln_tsp=" + ln_tsp + ", ln_sta=" + ln_sta
				+ "]";
	}
	
}
