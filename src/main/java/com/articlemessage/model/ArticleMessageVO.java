package com.articlemessage.model;

import java.io.Serializable;

public class ArticleMessageVO implements Serializable {
	
	private Integer msgid;
	private Integer atid;
	private Integer memid;
	private String msgtext;
	private String msgtime;
	private Integer msgsta;
	private String memname;
	private byte[] mempic;
	
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
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
	public String getMsgtext() {
		return msgtext;
	}
	public void setMsgtext(String msgtext) {
		this.msgtext = msgtext;
	}
	public String getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(String msgtime) {
		this.msgtime = msgtime;
	}
	public Integer getMsgsta() {
		return msgsta;
	}
	public void setMsgsta(Integer msgsta) {
		this.msgsta = msgsta;
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
