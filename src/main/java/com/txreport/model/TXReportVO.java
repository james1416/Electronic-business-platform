package com.txreport.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TXReportVO implements Serializable{
	private Integer tr_id;
	private Integer tr_odid;
	private Integer tr_reporter;
	private Integer tr_reported;
	private String tr_content;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp tr_date;
	private Integer tr_sta;
	public Integer getTr_id() {
		return tr_id;
	}
	public void setTr_id(Integer tr_id) {
		this.tr_id = tr_id;
	}
	public Integer getTr_odid() {
		return tr_odid;
	}
	public void setTr_odid(Integer tr_odid) {
		this.tr_odid = tr_odid;
	}
	public Integer getTr_reporter() {
		return tr_reporter;
	}
	public void setTr_reporter(Integer tr_reporter) {
		this.tr_reporter = tr_reporter;
	}
	public Integer getTr_reported() {
		return tr_reported;
	}
	public void setTr_reported(Integer tr_reported) {
		this.tr_reported = tr_reported;
	}
	public String getTr_content() {
		return tr_content;
	}
	public void setTr_content(String tr_content) {
		this.tr_content = tr_content;
	}
	public Timestamp getTr_date() {
		return tr_date;
	}
	public void setTr_date(Timestamp tr_date) {
		this.tr_date = tr_date;
	}
	public Integer getTr_sta() {
		return tr_sta;
	}
	public void setTr_sta(Integer tr_sta) {
		this.tr_sta = tr_sta;
	}
	public TXReportVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
