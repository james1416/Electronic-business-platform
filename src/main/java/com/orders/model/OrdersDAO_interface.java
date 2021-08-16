package com.orders.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.memberorders.model.MemberordersVO;
import com.memberorders.model.ordersVO;

public interface OrdersDAO_interface {

	boolean add(Map<String , List<List<Object>>> KeyODandPD , Integer memid);
	
	List<OrdersVO> mySOrders(Integer od_smemid);
	
	List<OrdersVO> myBOrders(Integer od_bmemid);
	
	void changeOD(Integer od_sta , Integer od_id , Integer od_goldflow);
	// 管理員拿全部
	public List<OrdersVO> getAllOrders();
	//拿某個狀態的
	public List<OrdersVO> getAllbySTA(Integer od_sta , Integer start ,Integer rows);
	// 管理員拿狀態是7的
	public List<OrdersVO> getLv7Orders(Integer start ,Integer rows);
	// 更新賣家的錢包
	public void updateTradersWalletByOrdersPK(Integer od_id);
	// 把原本狀態是7的變成9
	public void hideOrdersAfterAppropriation(Integer od_id, Connection con);
	// 狀態8改為6
	public void changeODstaBe6(Integer od_id, Connection con);
	
	public Integer getCount(Integer mem_id , Integer od_sta);
	
	public void refund(Integer od_id);
	
	public OrderBean getone(Integer od_id);
	
	public Map<Integer, List<MemberordersVO>> listAllOrdersByMem_id(Integer od_bmemid);
	
	
	// 這個list內，包含著每一張訂單
	public List<OrderBean> getSelfOrders(Integer mem_id);

	public Map<Integer, List<MemberordersVO>> listOrdersByMem_idandOd_sta(Integer od_bmemid, Integer od_sta);

	public void updateCommentByOdid(String od_comment, Integer od_point, Integer od_rating, Integer od_id);
	
	
	//專給退貨用
	
	public void returned(Integer od_id , Integer od_sta);

}
