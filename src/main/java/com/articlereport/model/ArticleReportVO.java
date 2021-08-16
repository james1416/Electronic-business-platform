package com.articlereport.model;

import java.io.Serializable;
import java.sql.Date;

public class ArticleReportVO implements Serializable {
	
	private Integer repid;
	private Integer memid;
	private Integer atid;
	private String text;
	private String reptime;
	private Integer status;
	private String attitle;
	private String attext;
	private String memname;
	private Integer atsta;
	
	public Integer getRepid() {
		return repid;
	}
	public void setRepid(Integer repid) {
		this.repid = repid;
	}
	public Integer getMemid() {
		return memid;
	}
	public void setMemid(Integer memid) {
		this.memid = memid;
	}
	public Integer getAtid() {
		return atid;
	}
	public void setAtid(Integer atid) {
		this.atid = atid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getReptime() {
		return reptime;
	}
	public void setReptime(String reptime) {
		this.reptime = reptime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAttitle() {
		return attitle;
	}
	public void setAttitle(String attitle) {
		this.attitle = attitle;
	}
	public String getMemname() {
		return memname;
	}
	public void setMemname(String memname) {
		this.memname = memname;
	}
	public String getAttext() {
		return attext;
	}
	public void setAttext(String attext) {
		this.attext = attext;
	}
	public Integer getAtsta() {
		return atsta;
	}
	public void setAtsta(Integer atsta) {
		this.atsta = atsta;
	}

}
