package com.productImg.model;

import java.io.Serializable;

public class ProductImgVO implements Serializable{
	private Integer pi_id;
	private Integer pi_pid;
	private byte[] pi_image;
	public ProductImgVO() {
	}
	public ProductImgVO(Integer pi_id, Integer pi_pid, byte[] pi_image) {
		super();
		this.pi_id = pi_id;
		this.pi_pid = pi_pid;
		this.pi_image = pi_image;
	}
	
	public Integer getPi_id() {
		return pi_id;
	}
	
	public void setPi_id(Integer pi_id) {
		this.pi_id = pi_id;
	}
	
	public Integer getPi_pid() {
		return pi_pid;
	}
	
	public void setPi_pid(Integer pi_pid) {
		this.pi_pid = pi_pid;
	}
	
	public byte[] getPi_image() {
		return pi_image;
	}
	
	public void setPi_image(byte[] pi_image) {
		this.pi_image = pi_image;
	}
	
}
