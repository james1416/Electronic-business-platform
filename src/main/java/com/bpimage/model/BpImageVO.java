package com.bpimage.model;

import java.io.Serializable;

public class BpImageVO implements Serializable{
	private Integer bpi_id;
	private Integer bpi_bpid;
	private byte[] bpi_img;

	
	public Integer getBpi_id() {
		return bpi_id;
	}
	public void setBpi_id(Integer bpi_id) {
		this.bpi_id = bpi_id;
	}
	public Integer getBpi_bpid() {
		return bpi_bpid;
	}
	public void setBpi_bpid(Integer bpi_bpid) {
		this.bpi_bpid = bpi_bpid;
	}
	public byte[] getBpi_img() {
		return bpi_img;
	}
	public void setBpi_img(byte[] bpi_img) {
		this.bpi_img = bpi_img;
	}

}
