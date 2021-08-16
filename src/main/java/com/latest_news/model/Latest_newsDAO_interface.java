package com.latest_news.model;

import java.sql.Timestamp;
import java.util.List;

public interface Latest_newsDAO_interface {
	public List<Latest_newsVO> getAllLN(Integer start , Integer rows);
	public List<Latest_newsVO> listOneLN(Integer ln_id);
	public void deleteLN(Integer ln_id);
	public void updateLN(Latest_newsVO latest_newsVO) ;
	public void addLN(String ln_con) ;
	public Integer findTotalCount();
	public List<Latest_newsVO> findByPage(Integer start, Integer pageSize);
	
}
