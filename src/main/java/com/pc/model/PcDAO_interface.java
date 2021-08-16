package com.pc.model;

import java.util.List;

public interface PcDAO_interface {
	public PcVO findByPc(Integer pc_id);
	public List<PcVO> getall(); 
}
