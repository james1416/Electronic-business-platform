package com.article.model;

import java.io.Serializable;
import java.sql.Date;

public class ArticleVO implements Serializable {

	private Integer atid;
	private Integer memid;
	private String memname;
	private String title;
	private String text;
	private Integer like;
	private String postime;
	private Integer status;
	private byte[] mempic;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLike() {
		return like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public String getPostime() {
		return postime;
	}

	public void setPostime(String postime) {
		this.postime = postime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemname() {
		return memname;
	}

	public void setMemname(String memname) {
		this.memname = memname;
	}

	public byte[] getMempic() {
		return mempic;
	}

	public void setMempic(byte[] mempic) {
		this.mempic = mempic;
	}
}
