package com.tracking.model;

import java.util.List;

public class TrackingService {
	public TrackingDAO_interface dao;
	
	public TrackingService() {
		dao = new TrackingDAO();
	}
	public List<TrackingVO> findSelf(Integer tk_memid){
		return dao.findSelf(tk_memid);
	}
	public void add(Integer tk_pid, Integer tk_memid) {
		TrackingVO trackingVO= new TrackingVO();
		trackingVO.setTk_pid(tk_pid);
		trackingVO.setTk_memid(tk_memid);
		dao.add(trackingVO);
	}
	public void del(Integer tk_pid, Integer tk_memid) {
		TrackingVO trackingVO= new TrackingVO();
		trackingVO.setTk_pid(tk_pid);
		trackingVO.setTk_memid(tk_memid);
		dao.del(trackingVO);
	}
	public boolean check(Integer tk_pid, Integer tk_memid) {
		TrackingVO trackingVO= new TrackingVO();
		trackingVO.setTk_pid(tk_pid);
		trackingVO.setTk_memid(tk_memid);
		return dao.check(trackingVO);
	}
}
