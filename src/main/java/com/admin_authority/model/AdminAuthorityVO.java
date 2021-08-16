package com.admin_authority.model;

import java.io.Serializable;

import com.admin.model.AdminVO;
import com.authority.model.AuthorityVO;

public class AdminAuthorityVO implements Serializable{
	private Integer aa_adminid;
	private Integer aa_autid;
	private AdminVO adminVO;
	private AuthorityVO authorityVO;
	public AdminAuthorityVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminAuthorityVO(Integer aa_adminid, Integer aa_autid, AdminVO adminVO, AuthorityVO authorityVO) {
		super();
		this.aa_adminid = aa_adminid;
		this.aa_autid = aa_autid;
		this.adminVO = adminVO;
		this.authorityVO = authorityVO;
	}
	public Integer getAa_adminid() {
		return aa_adminid;
	}
	public void setAa_adminid(Integer aa_adminid) {
		this.aa_adminid = aa_adminid;
	}
	public Integer getAa_autid() {
		return aa_autid;
	}
	public void setAa_autid(Integer aa_autid) {
		this.aa_autid = aa_autid;
	}
	public AdminVO getAdminVO() {
		return adminVO;
	}
	public void setAdminVO(AdminVO adminVO) {
		this.adminVO = adminVO;
	}
	public AuthorityVO getAuthorityVO() {
		return authorityVO;
	}
	public void setAuthorityVO(AuthorityVO authorityVO) {
		this.authorityVO = authorityVO;
	}
	
}
