package com.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class AdminVO implements Serializable {
	private Integer admin_id ;
	private String admin_acc;
	private String admin_pw;
	private Timestamp admin_log;
	private Integer admin_sta;
	
	public AdminVO() {
	}
	
	public Integer getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_acc() {
		return admin_acc;
	}
	public void setAdmin_acc(String admin_acc) {
		this.admin_acc = admin_acc;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}
	public Timestamp getAdmin_log() {
		return admin_log;
	}
	public void setAdmin_log(Timestamp admin_log) {
		this.admin_log = admin_log;
	}
	public Integer getAdmin_sta() {
		return admin_sta;
	}
	public void setAdmin_sta(Integer admin_sta) {
		this.admin_sta = admin_sta;
	}

	@Override
	public String toString() {
		return "AdminVO [admin_id=" + admin_id + ", admin_acc=" + admin_acc + ", admin_pw=" + admin_pw + ", admin_log="
				+ admin_log + ", admin_sta=" + admin_sta + "]";
	}
	
}
