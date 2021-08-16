package com.pi.model;

import java.io.Serializable;

public class PiVO implements Serializable{
	private Integer pi_imgid;
	private Integer pi_pdid;
	private byte[] pi_desc;
	
	public PiVO() {		
	}

	public Integer getPi_imgid() {
		return pi_imgid;
	}

	public void setPi_imgid(Integer pi_imgid) {
		this.pi_imgid = pi_imgid;
	}

	public Integer getPi_pdid() {
		return pi_pdid;
	}

	public void setPi_pdid(Integer pi_pdid) {
		this.pi_pdid = pi_pdid;
	}

	public byte[] getPi_desc() {
		return pi_desc;
	}

	public void setPi_desc(byte[] pi_desc) {
		this.pi_desc = pi_desc;
	}
	
}
