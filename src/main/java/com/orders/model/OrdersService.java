package com.orders.model;

import java.util.List;
import java.util.Map;

import com.memberorders.model.MemberordersVO;


public class OrdersService {
	private OrdersDAO_interface dao;
	
	public OrdersService() {
		dao = new OrdersDAO();
	}
	
	public Boolean add(Map<String , List<List<Object>>> KeyODandPD , Integer memid) {
		return dao.add(KeyODandPD , memid);
	}
	
	public List<OrdersVO> myBOrders(Integer od_bmemid){
		return dao.myBOrders(od_bmemid);
	}
	
	public List<OrdersVO> mySOrders(Integer od_smemid){
		return dao.mySOrders(od_smemid);
	}
	
	public void changeOD(Integer od_sta , Integer od_id , Integer od_goldflow) {
		dao.changeOD(od_sta, od_id , od_goldflow);
	}
	
	public void changeAllOD(Integer od_sta , String odid[] ,Integer od_goldflow) {
		for(int i = 0 ; i < odid.length ; i ++) {
			Integer od_id = new Integer(odid[i]);
			dao.changeOD(od_sta, od_id , od_goldflow);
		}
		
	}
	
	
	public List<OrdersVO> getLv7Orders(Integer start ,Integer rows){
		return dao.getLv7Orders(start ,rows);
	}
	
	public void appropriation(Integer od_id) {
		dao.updateTradersWalletByOrdersPK(od_id);	
	}
	
	public Integer getCount(Integer mem_id , Integer od_sta) {
		return dao.getCount(mem_id, od_sta);
	}
	
	public void refund(Integer od_id) {
		dao.refund(od_id);
	}
	
	public void submitAll(String[] submitodid ,String[] refundodid) {
		for(int i = 0 ; i < submitodid.length ; i ++) {
			System.out.println(submitodid[i]);
			dao.updateTradersWalletByOrdersPK(new Integer(submitodid[i]));
		}
		for( int i = 0 ; i < refundodid.length; i++) {
			System.out.println(refundodid[i]);
			dao.refund(new Integer(refundodid[i]));
		}
	}
	
	public OrderBean getone(Integer od_id) {
		return dao.getone(od_id);
	}
	
	
	public List<OrdersVO> getAllbySTA(Integer od_sta , Integer start ,Integer rows) {
		return dao.getAllbySTA(od_sta, start, rows);
	}
	
	public Map<Integer, List<MemberordersVO>> checkOrders(Integer od_bmemid) {
		System.out.println("安安安安安安安"+od_bmemid);
		return dao.listAllOrdersByMem_id(od_bmemid);
	}
	
	public List<OrderBean> getSelfOrders(Integer mem_id){
		return dao.getSelfOrders(mem_id);
	}
	
	
	public Map<Integer, List<MemberordersVO>> listOrdersByMem_idandOd_sta(Integer od_bmemid, Integer od_sta){
		return dao.listOrdersByMem_idandOd_sta(od_bmemid, od_sta);
	}
	
	public void updateComment(String od_comment, Integer od_point, Integer od_rating, Integer od_id) {
		dao.updateCommentByOdid(od_comment, od_point, od_rating, od_id);
	}
	
	
	public void returned(Integer od_id, Integer od_sta) {
		dao.returned(od_id, od_sta);
	}
	
}
