package com.electronicwallet.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public interface ElectronicWalletDAO_interface {
	public List<ElectronicWalletVO> getAll(Integer ele_memid);
	
	public void insertNewPayment(Integer ele_memid, Timestamp ele_time, String ele_rec, Integer ele_mon);
	
	public void walUpdatedByTranOnSmem(Integer od_smemid, Timestamp ele_time, Integer od_price, Connection con);
	
	public void walUpdatedByTranOnBmem(Integer od_bmemid, Timestamp ele_time, Integer od_price, Connection con);
	
	public void refund(Integer od_smemid, Timestamp ele_time, Integer od_price, Connection con);
	
	public Integer findTotalCount(Integer ele_memid);
	
	public List<ElectronicWalletVO> findByPage(Integer ele_memid, Integer start, Integer pageSize);
	
	public ElectronicWalletVO getOneLog(Integer ele_memid, Integer ele_id);
}
