package com.bptrackdetail.model;

import java.util.List;

public interface BpTrackDetail_interface {
	public void insert(BpTrackDetailVO btdVO);

	public void delete(Integer tr_bpid,Integer tr_memid);

	public BpTrackDetailVO findByPrimaryKey(Integer tr_memid);

	public List<BpTrackDetailVO> getAll();
}
