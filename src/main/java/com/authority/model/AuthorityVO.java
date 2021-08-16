package com.authority.model;

import java.io.Serializable;

public class AuthorityVO implements Serializable{
	private Integer aut_id;
	private String aut_name;
	private String aut_con;
	public Integer getAut_id() {
		return aut_id;
	}
	public void setAut_id(Integer aut_id) {
		this.aut_id = aut_id;
	}
	public String getAut_name() {
		return aut_name;
	}
	public void setAut_name(String aut_name) {
		this.aut_name = aut_name;
	}
	public String getAut_con() {
		return aut_con;
	}
	public void setAut_con(String aut_con) {
		this.aut_con = aut_con;
	}
	@Override
	public String toString() {
		return "AUTHORITY_VO [aut_id=" + aut_id + ", aut_name=" + aut_name + ", aut_con=" + aut_con + "]";
	}
	public AuthorityVO() {
	}
	public AuthorityVO(Integer aut_id, String aut_name, String aut_con) {
		this.aut_id = aut_id;
		this.aut_name = aut_name;
		this.aut_con = aut_con;
	}
	
	
}
