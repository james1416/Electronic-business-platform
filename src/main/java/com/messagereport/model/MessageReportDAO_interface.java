package com.messagereport.model;

import java.util.List;

public interface MessageReportDAO_interface {

	public List<MessageReportVO> getAll(Integer memid);
	
	public void insert(MessageReportVO messageReportVO);
	
	public void reportReturn(Integer repid);
	
	public void reportPass(Integer repid);
	
	public List<MessageReportVO> admingetAll();
}
