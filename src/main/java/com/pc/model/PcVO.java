package com.pc.model;
import java.io.Serializable;

public class PcVO implements Serializable{
	private Integer pc_id;
	private String pc_desc;
	
	public PcVO() {
		
	}
	public Integer getPc_id() {
		return pc_id;
	}
	public void setPc_id(Integer pc_id) {
		this.pc_id = pc_id;
	}
	public String getPc_desc() {
		return pc_desc;
	}
	public void setPc_desc(String pc_desc) {
		this.pc_desc = pc_desc;
	}
}
