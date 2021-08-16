package com.bidproduct.model;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.Part;

import com.bpdetail.model.BpDetailDAO;
import com.bpdetail.model.BpDetailVO;
import com.bpimage.model.BpImageDAO;
import com.bpimage.model.BpImageDAO_interface;
import com.notice.model.NoticeDAO;


public class BidProductService {
	private BidProductDAO_interface dao;
	private BpImageDAO_interface bpidao;
	public BidProductService() {
		dao = new BidProductDAO();
		bpidao = new BpImageDAO();
	}
	
	//新增商品
	public void addBid(Integer bp_smemid,Integer bp_bpcid,String bp_name,Timestamp bp_stime,Timestamp bp_etime,Integer bp_sprice,Integer bp_inc,String bp_desc,List<Part> list) {
		
		List<Part> imgs = new ArrayList<Part>();
		
		BidProductVO bidVO = new BidProductVO();
		bidVO.setBp_smemid(bp_smemid);
		bidVO.setBp_bpcid(bp_bpcid);
		bidVO.setBp_name(bp_name);
		bidVO.setBp_stime(bp_stime);
		bidVO.setBp_etime(bp_etime);
		bidVO.setBp_sprice(bp_sprice);
		bidVO.setBp_inc(bp_inc);
		bidVO.setBp_desc(bp_desc);
		
		for(Part img :list) {
			if("bpi_img".equals(img.getName())) {
				imgs.add(img);
				System.out.println("抓到圖片了");
			}
		}
		Integer bp_id = dao.addBid(bidVO, imgs);
		Timestamp ct = new Timestamp(System.currentTimeMillis());
		System.out.println("系統時間 :"+ct);
		System.out.println("競標開始時間"+bp_stime);
		System.out.println("競標結束時間"+bp_etime);
		//系統時間毫秒
		long systemtime = ct.getTime();
		//競標開始時間毫秒
		long st = bp_stime.getTime();
		//競標結束時間毫秒
		long et = bp_etime.getTime();
		long s_result = st-systemtime;
		long e_result = et-systemtime;
		System.out.println(s_result);
		System.out.println(e_result);
		// 開始競標run
		class stime extends TimerTask{
			public void run() {
				Integer bp_sta = 1;
				dao.changesta(bp_sta, bp_id);
				System.out.println("開始狀態更新成功");
			}
		}
		// 結束競標run
		class etime extends TimerTask{
			public void run() {
				Integer bp_sta = 2;
				dao.changesta(bp_sta, bp_id);
				System.out.println("結束狀態更新成功");	
				BpDetailDAO bpddao = new BpDetailDAO();
				BpDetailVO bpdVO =bpddao.getTopPrice(bp_id);
				if( bpdVO.getBpd_bmemid() == null) {
					System.out.println("訂單無人購買");
				}else {
					Integer bp_bmemid = bpdVO.getBpd_bmemid();
					Integer bp_to = bpdVO.getBpd_bpprice();
					Integer bp_shsta = new Integer(1) ;
					dao.UpdateBp_bmemid(bp_bmemid,bp_to,bp_id,bp_shsta);
					System.out.println("訂單買家以新增");
				}
				
			}
		}
		Timer stachange = new Timer();
		//排成器已安排
		stachange.schedule(new stime(),s_result);
		stachange.schedule(new etime(),e_result);
		//要關閉TIMER
	}
	//更新商品
	public void updataBid(Integer bp_bpcid,String bp_name,Timestamp bp_stime,Timestamp bp_etime,Integer bp_sprice,Integer bp_inc,Integer bp_imdt,Integer bp_ship, String bp_desc,Integer bp_id,String needDel[],List<Part> list) {
		BidProductVO bidVO = new BidProductVO();
		List<Part> imgs = new ArrayList<Part>();
		bidVO.setBp_id(bp_id);
		bidVO.setBp_bpcid(bp_bpcid);
		bidVO.setBp_name(bp_name);
		bidVO.setBp_stime(bp_stime);
		bidVO.setBp_etime(bp_etime);
		bidVO.setBp_sprice(bp_sprice);
		bidVO.setBp_inc(bp_inc);
		bidVO.setBp_imdt(bp_imdt);
		bidVO.setBp_ship(bp_ship);
		bidVO.setBp_desc(bp_desc);
		System.out.println("進入service");
		
		for(Part part:list) {
			if("piimage".equals(part.getName())) {
				imgs.add(part);
			}
		}
		Integer bpi_id[];
		if(needDel == null) {
			bpi_id = new Integer[0] ;		
		}else {
			bpi_id = new Integer[needDel.length];
		}
		for(int i = 0;i<bpi_id.length;i++) {
			bpi_id[i] = new Integer(needDel[i]);
		}
		dao.updataBid(bidVO, imgs, bpi_id);
	}
	//改變商品狀態用
	public void changesta(Integer bp_shsta, Integer bp_id) {
		dao.changesta(bp_shsta, bp_id);
	}
	//刪除商品
	public void deleteBidProduct(Integer bp_id) {
		dao.delete(bp_id);
	}
	//單一主鍵查詢單一商品
	public BidProductVO getOneBidProduct(Integer bp_id) {
		return dao.findByPrimaryKey(bp_id);
	}
	//查詢全部商品
	public List<BidProductVO> getAll() {
		return dao.getAll();
	}
	//獲得相關狀態的全部商品
	public List<BidProductVO> getAllBystatus(Integer bp_sta){
		return dao.getAllBystatus(bp_sta);
	}
	//笨方法條件查詢主鍵值
	public BidProductVO getBp_id(BidProductVO bidVO) {
		return dao.getBp_id(bidVO);
	}
	//獲得賣家自己本身商品
	public  List<BidProductVO> getMybid(Integer bp_smemid){
		return dao.getMyBid(bp_smemid);
	}
	//更改最高價格
	public void updatebp_tprice(Integer bp_tprice,Integer bp_id,BpDetailVO bpdVO) {
		dao.Updateprice(bp_tprice, bp_id,bpdVO);
	}
	//獲得得標商品資料
	public List<BidProductVO> getwinbid(Integer bp_bmemid) {
		return dao.getWinbid(bp_bmemid);
	}
	//新增修改收件相關
	public void InsertOrUpdateReceive(String bp_bname ,String bp_add,String bp_tel,Integer bp_id,Integer bp_shsta) {
		dao.InsertOrUpdateReceive(bp_bname, bp_add, bp_tel,bp_id,bp_shsta);
	}
	//AJAX 拿到賣家所有商品 未結束
	public List<BidProductVO> findmybidbypage(Integer bp_smemid,Integer start, Integer rows){
		return dao.findMyBidByPage(bp_smemid, start, rows);
	}
	// 拿到有買家的競標商品以填寫資料
	public List<BidProductVO> effbid(Integer bp_smemid){
		return dao.effbid(bp_smemid);
	}
	//更改狀態
	public void changeshsta(Integer bp_id ,Integer bp_shsta) {
		dao.changeshsta(bp_id, bp_shsta);
	}
	//賣家獲得待收貨競標訂單
	public List<BidProductVO> tobeconfirmed(Integer bp_smemid){
		return dao.tobeconfirmed(bp_smemid);
	}
	//買家獲得完成訂單
	public List<BidProductVO> completebidb (Integer bp_smemid,Integer start, Integer rows){
		return dao.completebidb(bp_smemid, start, rows);
	}
	//賣家獲得完成的訂單
	public List<BidProductVO> completebids (Integer bp_smemid,Integer start, Integer rows){
		return dao.completebids(bp_smemid, start, rows);
	}
//	public void change
//	public List<BidproductVO> getAll(Map<String, String[]> map){
//		return null;
//	}
	//賣家獲得被取消的訂單
	public List<BidProductVO> cancelbids(Integer bp_smemid){
		return dao.cancelbids(bp_smemid);
	}
	//買家新增評論
	public void addcom(Integer bp_id,String bp_comment,Integer bp_point,Integer bp_comsta) {
		dao.addcom(bp_id, bp_comment, bp_point, bp_comsta);
	}
	
	
	//管理員拿全部
	public List<BidProductVO> adminGetBP(Integer start , Integer rows){
		return dao.adminGetBP(start, rows);
	}
	//管理員拿單一狀態
	public List<BidProductVO> adminGetOne(Integer bp_shsta , Integer start , Integer rows){
		return dao.adminGetOneBP(bp_shsta, start, rows);
	}
	
	//管理員撥款
	public void BPdone(Integer bp_id) {
		dao.BPdone(bp_id);
	}
	//管理員退款
	public void refund(Integer bp_id) {
		dao.refund(bp_id);
	}
	//管理員一鍵撥退款
	public void submitAll(String[] submitodid ,String[] refundodid) {
		for(int i = 0 ; i < submitodid.length ; i ++) {
			dao.BPdone(new Integer(submitodid[i]));
		}
		for( int i = 0 ; i < refundodid.length; i++) {
			dao.refund(new Integer(refundodid[i]));
		}
	}
	
	//拿自己買的所有訂單
	public List<BidProductVO> getSelfBuyOrdersAll(Integer bp_bmemid, Integer start, Integer rows){
		return dao.getSelfBuyOrdersAll(bp_bmemid, start, rows);
	}
	//拿自己的某個狀態的所有訂單
	public List<BidProductVO> getSelfBuyOrdersSTA(Integer bp_bmemid, Integer bp_shsta, Integer start, Integer rows){
		return dao.getSelfBuyOrdersSTA(bp_bmemid, bp_shsta, start, rows);
	}
}