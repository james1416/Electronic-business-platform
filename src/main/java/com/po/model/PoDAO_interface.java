package com.po.model;

import java.util.List;

import com.bidproduct.model.BidProductVO;
import com.pd.model.PdVO;

public interface PoDAO_interface {
	//新增訂單
	public void addorder(PoVO poVO);
	
	//計算單一商品訂單總數
	public Integer count(Integer po_pdid);
	//變更訂單狀態
	public void updateOrder(Integer po_id , Integer po_sta);
	
	//
	public void updateAllSta(Integer po_sta , Integer po_pdid);
	
	//查詢會員購買訂單
	public List<PoVO> getbuyall(Integer po_bmemid);
	
	//查詢會員販售訂單
	public List<PoVO> getsellall(Integer po_smemid);
	
	
	//查看商品是否有人購買
	public boolean haveBuy(Integer po_pdid);
	
	//管理員拿需撥退款訂單
	public List<PoVO> adminGetOD(Integer po_sta ,Integer start , Integer rows);
	//撥款
	public void ODdone(Integer po_id);
	//退款
	public void refund(Integer po_id);
	//拿單一
	public PoVO getone(Integer po_id);
	
	//拿自己購買到的東西
	public List<PoAndPdBean> getSelfBuyOrdersAll(Integer po_bmemid , Integer start, Integer rows);
	//用狀態拿自己購買到的東西
	public List<PoAndPdBean> getSelfBuyOrdersSTA(Integer po_bmemid ,Integer po_sta ,  Integer start, Integer rows );
	//取得購買此商品的所有訂單資訊
	public List<PoVO> getbuyOrder(Integer po_pdid);
	// 給予訂單評價
	public void setComment(String po_comment ,Integer po_point ,Integer po_sta , Integer po_id ,Integer po_iscom );
	// 更新訂單狀態 (買家給賣家發通知)
	public void updateBOrder(Integer po_id , Integer po_sta , Integer po_goldflow);
		
	
	
}
