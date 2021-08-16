package com.po.model;

import java.util.List;

import com.pd.model.PdVO;

public class PoService {
	private PoDAO_interface dao;
	
	public  PoService() {
		dao = new PoDAO();
	}
	
	//新增一筆訂單
	public void add(PoVO poVO) {
		dao.addorder(poVO);
	}
	//取得自己購買的訂單
	public List<PoVO>allBorder(Integer po_bmemid){
		return dao.getbuyall(po_bmemid);
	}
	//取得自己販售商品的訂單
	public List<PoVO>allSorder(Integer po_smemid){
		return dao.getsellall(po_smemid);
	}
	
	//取得下訂單總數
	public Integer getSum(Integer po_pdid) {
		return dao.count(po_pdid);
	}
	
	//取得此商品是否有人下訂單
	public boolean checkOrder(Integer po_pdid) {
		return dao.haveBuy(po_pdid);
	}
	
	//更新訂單狀態
	public void updateOrder(Integer po_id , Integer po_sta) {
		 dao.updateOrder(po_id, po_sta);
	}
	
	public List<PoVO> adminGetOD(Integer po_sta , Integer start , Integer rows) {
		return dao.adminGetOD(po_sta , start, rows);
	}
	
	public void ODdone(Integer po_id) {
		dao.ODdone(po_id);
	}
	
	public void refund(Integer po_id) {
		dao.refund(po_id);
	}
	
	public void submitAll(String[] submitodid ,String[] refundodid) {
		for(int i = 0 ; i < submitodid.length ; i ++) {
			dao.ODdone(new Integer(submitodid[i]));
		}
		for( int i = 0 ; i < refundodid.length; i++) {
			dao.refund(new Integer(refundodid[i]));
		}
	}
	
	public PoVO getone(Integer po_id) {
		return dao.getone(po_id);
	}
	//拿到自己購買的全部訂單
	public List<PoAndPdBean> getSelfBuyOrdersAll(Integer po_bmemid, Integer start, Integer rows){
		return dao.getSelfBuyOrdersAll(po_bmemid, start, rows);
	}
	//拿到自己購買某個狀態的全部訂單
	public List<PoAndPdBean> getSelfBuyOrdersSTA(Integer po_bmemid, Integer po_sta, Integer start, Integer rows) {
		return dao.getSelfBuyOrdersSTA(po_bmemid, po_sta, start, rows);
	}
	
	public void SetComment(String po_comment , Integer po_point , Integer po_sta , Integer po_id , Integer po_iscom) {
		dao.setComment(po_comment, po_point, po_sta, po_id , po_iscom);
	}
	
	//更新訂單狀態 (買家對賣家發通知)
	public void updateBOrder(Integer po_id , Integer po_sta , Integer po_goldflow) {
		 dao.updateBOrder(po_id, po_sta , po_goldflow);
	}
}
