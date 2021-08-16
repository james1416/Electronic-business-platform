package com.qa_list.model;

import java.io.Serializable;
import java.util.Date;

public class QAListVO implements Serializable{
	private Integer qal_id;
	private String qal_qcon;
	private String qal_acon;
	private Date qal_tsp;
	private Integer qal_sta;
	public QAListVO() {
	}
	public QAListVO(Integer qal_id, String qal_qcon, String qal_acon, Date qal_tsp, Integer qal_sta) {
		super();
		this.qal_id = qal_id;
		this.qal_qcon = qal_qcon;
		this.qal_acon = qal_acon;
		this.qal_tsp = qal_tsp;
		this.qal_sta = qal_sta;
	}
	public Integer getQal_id() {
		return qal_id;
	}
	public void setQal_id(Integer qal_id) {
		this.qal_id = qal_id;
	}
	public String getQal_qcon() {
		return qal_qcon;
	}
	public void setQal_qcon(String qal_qcon) {
		this.qal_qcon = qal_qcon;
	}
	public String getQal_acon() {
		return qal_acon;
	}
	public void setQal_acon(String qal_acon) {
		this.qal_acon = qal_acon;
	}
	public Date getQal_tsp() {
		return qal_tsp;
	}
	public void setQal_tsp(Date qal_tsp) {
		this.qal_tsp = qal_tsp;
	}
	public Integer getQal_sta() {
		return qal_sta;
	}
	public void setQal_sta(Integer qal_sta) {
		this.qal_sta = qal_sta;
	}
	
}
