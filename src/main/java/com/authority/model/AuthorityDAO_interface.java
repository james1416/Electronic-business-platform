package com.authority.model;

import java.util.List;

public interface AuthorityDAO_interface {
	List<AuthorityVO> getall();
	void add(AuthorityVO authorityVO);
	void updata(AuthorityVO authorityVO);
	AuthorityVO findone(Integer aut_id);
}
