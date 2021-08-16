package com.bpclassification.model;

import java.io.Serializable;

public class BpClassificationVO implements Serializable{
	private Integer bpc_id;
	private String bpc_cgname;

	public BpClassificationVO() {
		
	}
	
	public Integer getBpc_id() {
		return bpc_id;
	}
	public void setBpc_id(Integer bpc_id) {
		this.bpc_id = bpc_id;
	}
	public String getBpc_cgname() {
		return bpc_cgname;
	}
	public void setBpc_cgname(String bpc_cgname) {
		this.bpc_cgname = bpc_cgname;
	}
	
}
