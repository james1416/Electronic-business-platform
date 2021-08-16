package com.member.model;

import java.sql.Date;
import java.util.Arrays;

public class MemberVO implements java.io.Serializable{

	private Integer mem_id;
	private String mem_name;
	private String mem_uid;
	private String mem_bth;
	private Integer mem_sex;
	private String mem_email;
	private String mem_tel;
	private String mem_add;
	private String mem_acc;
	private String mem_pw;
	private Integer mem_sta;
	private Integer mem_ele;
	private Integer mem_rp;
	private Double mem_point;
	private byte[] mem_pic;
	
	public MemberVO(Integer mem_id, String mem_name, String mem_uid, String mem_bth, Integer mem_sex, String mem_email,
			String mem_tel, String mem_add, String mem_acc, String mem_pw, Integer mem_sta, Integer mem_ele,
			Integer mem_rp, Double mem_point, byte[] mem_pic) {
		super();
		this.mem_id = mem_id;
		this.mem_name = mem_name;
		this.mem_uid = mem_uid;
		this.mem_bth = mem_bth;
		this.mem_sex = mem_sex;
		this.mem_email = mem_email;
		this.mem_tel = mem_tel;
		this.mem_add = mem_add;
		this.mem_acc = mem_acc;
		this.mem_pw = mem_pw;
		this.mem_sta = mem_sta;
		this.mem_ele = mem_ele;
		this.mem_rp = mem_rp;
		this.mem_point = mem_point;
		this.mem_pic = mem_pic;
	}

	public MemberVO() {
		
	}
	
	public byte[] getMem_pic() {
		return mem_pic;
	}
	public void setMem_pic(byte[] mem_pic) {
		this.mem_pic = mem_pic;
	}
	
	public Integer getMem_id() {
		return mem_id;
	}
	public void setMem_id(Integer mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_uid() {
		return mem_uid;
	}
	public void setMem_uid(String mem_uid) {
		this.mem_uid = mem_uid;
	}
	public String getMem_bth() {
		return mem_bth;
	}
	public void setMem_bth(String string) {
		this.mem_bth = string;
	}
	public Integer getMem_sex() {
		return mem_sex;
	}
	public void setMem_sex(Integer mem_sex) {
		this.mem_sex = mem_sex;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_tel() {
		return mem_tel;
	}
	public void setMem_tel(String mem_tel) {
		this.mem_tel = mem_tel;
	}
	public String getMem_add() {
		return mem_add;
	}
	public void setMem_add(String mem_add) {
		this.mem_add = mem_add;
	}
	public String getMem_acc() {
		return mem_acc;
	}
	public void setMem_acc(String mem_acc) {
		this.mem_acc = mem_acc;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public Integer getMem_sta() {
		return mem_sta;
	}
	public void setMem_sta(Integer mem_sta) {
		this.mem_sta = mem_sta;
	}
	public Integer getMem_ele() {
		return mem_ele;
	}
	public void setMem_ele(Integer mem_ele) {
		this.mem_ele = mem_ele;
	}
	public Integer getMem_rp() {
		return mem_rp;
	}
	public void setMem_rp(Integer mem_rp) {
		this.mem_rp = mem_rp;
	}
	public Double getMem_point() {
		return mem_point;
	}
	public void setMem_point(Double mem_point) {
		this.mem_point = mem_point;
	}
	
	@Override
	public String toString() {
		return "MemVO [mem_id=" + mem_id + ", mem_name=" + mem_name + ", mem_uid=" + mem_uid + ", mem_bth=" + mem_bth
				+ ", mem_sex=" + mem_sex + ", mem_email=" + mem_email + ", mem_tel=" + mem_tel + ", mem_add=" + mem_add
				+ ", mem_acc=" + mem_acc + ", mem_pw=" + mem_pw + ", mem_sta=" + mem_sta + ", mem_ele=" + mem_ele
				+ ", mem_rp=" + mem_rp + ", mem_point=" + mem_point + ", mem_pic=" + Arrays.toString(mem_pic) + "]";
	}
}
