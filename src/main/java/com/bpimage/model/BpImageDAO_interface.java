package com.bpimage.model;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.Part;

public interface BpImageDAO_interface {
	public void Insert(Integer bpi_bpid,List<Part> imgs,Connection con);
	public byte[] getOne(Integer bpi_bpid);
	public List<BpImageVO> getAll(Integer bpi_bpid);
	public boolean check(Integer bpi_bpid);
	public void delete(Integer bpi_bpid, Connection con);

}
