package com.bidproduct.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BidProductVO implements java.io.Serializable{
	private Integer bp_id;
	private Integer bp_bmemid;
	private Integer bp_smemid;
	private Integer bp_bpcid;
	private String bp_name;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp bp_stime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Timestamp bp_etime;
	private Integer bp_sprice;
	private Integer bp_inc;
	private Integer bp_imdt;
	private Integer bp_ship;
	private String bp_desc;
	private Integer bp_tprice;
	private Integer bp_sta;
	private Timestamp bp_beend;
	private Integer bp_to;
	private Integer bp_paymth;
	private Integer bp_shsta;
	private String bp_comment;
	private Integer bp_point;
	private String bp_bname;
	private String bp_add;
	private String bp_tel;
	private Integer bp_comsta;

	
	
	public Integer getBp_comsta() {
		return bp_comsta;
	}
	public void setBp_comsta(Integer bp_comsta) {
		this.bp_comsta = bp_comsta;
	}
	public BidProductVO(){}
	public Integer getBp_id() {
		return bp_id;
	}
	public void setBp_id(Integer bp_id) {
		this.bp_id = bp_id;
	}
	public Integer getBp_bmemid() {
		return bp_bmemid;
	}
	public void setBp_bmemid(Integer bp_bmemid) {
		this.bp_bmemid = bp_bmemid;
	}
	public Integer getBp_smemid() {
		return bp_smemid;
	}
	public void setBp_smemid(Integer bp_smemid) {
		this.bp_smemid = bp_smemid;
	}
	public Integer getBp_bpcid() {
		return bp_bpcid;
	}
	public void setBp_bpcid(Integer bp_bpcid) {
		this.bp_bpcid = bp_bpcid;
	}
	public String getBp_name() {
		return bp_name;
	}
	public void setBp_name(String bp_name) {
		this.bp_name = bp_name;
	}
	public Timestamp getBp_stime() {
		return bp_stime;
	}
	public void setBp_stime(Timestamp bp_stime) {
		this.bp_stime = bp_stime;
	}
	public Timestamp getBp_etime() {
		return bp_etime;
	}
	public void setBp_etime(Timestamp bp_etime) {
		this.bp_etime = bp_etime;
	}
	public Integer getBp_sprice() {
		return bp_sprice;
	}
	public void setBp_sprice(Integer bp_sprice) {
		this.bp_sprice = bp_sprice;
	}
	public Integer getBp_inc() {
		return bp_inc;
	}
	public void setBp_inc(Integer bp_inc) {
		this.bp_inc = bp_inc;
	}
	public Integer getBp_imdt() {
		return bp_imdt;
	}
	public void setBp_imdt(Integer bp_imdt) {
		this.bp_imdt = bp_imdt;
	}
	public Integer getBp_ship() {
		return bp_ship;
	}
	public void setBp_ship(Integer bp_ship) {
		this.bp_ship = bp_ship;
	}
	public String getBp_desc() {
		return bp_desc;
	}
	public void setBp_desc(String bp_desc) {
		this.bp_desc = bp_desc;
	}
	public Integer getBp_tprice() {
		return bp_tprice;
	}
	public void setBp_tprice(Integer bp_tprice) {
		this.bp_tprice = bp_tprice;
	}
	public Integer getBp_sta() {
		return bp_sta;
	}
	public void setBp_sta(Integer bp_sta) {
		this.bp_sta = bp_sta;
	}
	public Timestamp getBp_beend() {
		return bp_beend;
	}
	public void setBp_beend(Timestamp bp_beend) {
		this.bp_beend = bp_beend;
	}
	public Integer getBp_to() {
		return bp_to;
	}
	public void setBp_to(Integer bp_to) {
		this.bp_to = bp_to;
	}
	public Integer getBp_paymth() {
		return bp_paymth;
	}
	public void setBp_paymth(Integer bp_paymth) {
		this.bp_paymth = bp_paymth;
	}
	public Integer getBp_shsta() {
		return bp_shsta;
	}
	public void setBp_shsta(Integer bp_shsta) {
		this.bp_shsta = bp_shsta;
	}
	public String getBp_comment() {
		return bp_comment;
	}
	public void setBp_comment(String bp_comment) {
		this.bp_comment = bp_comment;
	}
	public Integer getBp_point() {
		return bp_point;
	}
	public void setBp_point(Integer bp_point) {
		this.bp_point = bp_point;
	}
	public String getBp_bname() {
		return bp_bname;
	}
	public void setBp_bname(String bp_bname) {
		this.bp_bname = bp_bname;
	}
	public String getBp_add() {
		return bp_add;
	}
	public void setBp_add(String bp_add) {
		this.bp_add = bp_add;
	}
	public String getBp_tel() {
		return bp_tel;
	}
	public void setBp_tel(String bp_tel) {
		this.bp_tel = bp_tel;
	}

	
}
