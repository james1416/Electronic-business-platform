package com.articlecollection.model;

import java.io.Serializable;
import java.sql.Date;

public class ArticleCollectionVO implements Serializable {
	
	private Integer atid;
	private Integer memid;
	private String time;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
