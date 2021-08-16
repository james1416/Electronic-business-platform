package com.tracking.model;

import java.util.List;

public interface TrackingDAO_interface {

	List<TrackingVO> findSelf(Integer tk_memid);
	
	void add(TrackingVO trackingVO);
	
	void del(TrackingVO trackingVO);
	
	Boolean check(TrackingVO trackingVO);
}
