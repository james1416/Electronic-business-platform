package com.chatroom.model;

import java.sql.Timestamp;

public class ChatroomVO {
	private Integer ctr_id;
	private Integer ctr_memid;
	private String ctr_text;
	private Timestamp ctr_time;
	private Integer ctr_who;
	
	public ChatroomVO() {
		
	}
	
	public ChatroomVO(Integer ctr_id, Integer ctr_memid, String ctr_text, Timestamp ctr_time, Integer ctr_who) {
		super();
		this.ctr_id = ctr_id;
		this.ctr_memid = ctr_memid;
		this.ctr_text = ctr_text;
		this.ctr_time = ctr_time;
		this.ctr_who = ctr_who;
	}

	public Integer getCtr_id() {
		return ctr_id;
	}

	public void setCtr_id(Integer ctr_id) {
		this.ctr_id = ctr_id;
	}

	public Integer getCtr_memid() {
		return ctr_memid;
	}

	public void setCtr_memid(Integer ctr_memid) {
		this.ctr_memid = ctr_memid;
	}

	public String getCtr_text() {
		return ctr_text;
	}

	public void setCtr_text(String ctr_text) {
		this.ctr_text = ctr_text;
	}

	public Timestamp getCtr_time() {
		return ctr_time;
	}

	public void setCtr_time(Timestamp ctr_time) {
		this.ctr_time = ctr_time;
	}

	public Integer getCtr_who() {
		return ctr_who;
	}

	public void setCtr_who(Integer ctr_who) {
		this.ctr_who = ctr_who;
	}

	@Override
	public String toString() {
		return "ChatroomVO [ctr_id=" + ctr_id + ", ctr_memid=" + ctr_memid + ", ctr_text=" + ctr_text + ", ctr_time="
				+ ctr_time + ", ctr_who=" + ctr_who + "]";
	}
	
}
