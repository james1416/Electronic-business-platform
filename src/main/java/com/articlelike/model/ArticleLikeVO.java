package com.articlelike.model;

import java.io.Serializable;
import java.sql.Date;

public class ArticleLikeVO implements Serializable {
	
	private Integer atid;
	private Integer memid;
	private String lktime ;
	
	public Integer getAtid() {
		return atid;
	}
	public void setAtid(Integer atid) {
		this.atid = atid;
	}
	public Integer getMemid() {
		return memid;
	}
	public void setMemid(Integer memid) {
		this.memid = memid;
	}
	public String getLktime() {
		return lktime;
	}
	public void setLktime(String lktime) {
		this.lktime = lktime;
	}

}
