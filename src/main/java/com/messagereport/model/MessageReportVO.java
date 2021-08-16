package com.messagereport.model;

import java.io.Serializable;

public class MessageReportVO implements Serializable {
	
	private Integer msrid;
	private Integer msgid;
	private Integer memid;
	private String  msrtext;
	private String  msrtime;
	private Integer msrsta;
	private String	msgtext;
	private String  memname;
	
	public Integer getMsrid() {
		return msrid;
	}
	public void setMsrid(Integer msrid) {
		this.msrid = msrid;
	}
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	public Integer getMemid() {
		return memid;
	}
	public void setMemid(Integer memid) {
		this.memid = memid;
	}
	public String getMsrtext() {
		return msrtext;
	}
	public void setMsrtext(String msrtext) {
		this.msrtext = msrtext;
	}
	public String getMsrtime() {
		return msrtime;
	}
	public void setMsrtime(String msrtime) {
		this.msrtime = msrtime;
	}
	public Integer getMsrsta() {
		return msrsta;
	}
	public void setMsrsta(Integer msrsta) {
		this.msrsta = msrsta;
	}
	public String getMsgtext() {
		return msgtext;
	}
	public void setMsgtext(String msgtext) {
		this.msgtext = msgtext;
	}
	public String getMemname() {
		return memname;
	}
	public void setMemname(String memname) {
		this.memname = memname;
	}
	

}
