package com.bidproduct.model;
import java.util.*;

import javax.servlet.http.Part;

import com.bpdetail.model.BpDetailVO;
public interface BidProductDAO_interface {
	public void insert(BidProductVO bidVO); //新增競標商品(舊版)
	public Integer addBid(BidProductVO bidVO,List<Part> list); //子義版
	public void updataBid(BidProductVO bidVO,List<Part> list,Integer bpi_id[]); //
	public void updata(BidProductVO bidVO); //更改商品資訊
	public void delete(Integer bp_id);	//刪除競標商品
	public void changesta(Integer bp_shsta,Integer bp_id); //變更商品狀態
	public List<BidProductVO> getMyBid(Integer bp_smemid); // 查詢會員本身上架商品
	public BidProductVO findByPrimaryKey(Integer bp_id); //依據競標商品編號得到相關商品資訊
	public List<BidProductVO> getAll(); //獲得所有商品資訊
	public List<BidProductVO> getAllBystatus(Integer bp_sta); // 依據不同狀態獲取相關的商品資訊
	public List<BidProductVO> getAll(Map<String, String[]> map); // 萬用複合查詢
	public BidProductVO getBp_id(BidProductVO bidVO); // 抓到新增好的商品主鍵用
//	public BidProductVO getBp_tprice(Integer bp_id); // 不需要為了抓一個欄位再做一個方法
	public void Updateprice(Integer bp_tprice,Integer bp_id,BpDetailVO bpdVO);// 更新最高價格
	public void UpdateBp_bmemid(Integer bp_bmemid,Integer bp_to,Integer bp_id,Integer bp_shsta); // 更新買家
	public List<BidProductVO> getWinbid(Integer bp_bmemid); //獲得得標商品
	public void InsertOrUpdateReceive(String bp_bname ,String bp_add,String bp_tel,Integer bp_id,Integer bp_shsta); // 新增收件相關資料
	public List<BidProductVO> findMyBidByPage(Integer bp_smemid,Integer start, Integer rows); // ajax 下拉拿資料所使用(獲得賣家自己選有商品資料)
	public List<BidProductVO> effbid(Integer bp_smemid);
	public void changeshsta(Integer bp_id,Integer bp_shsta);
	public List<BidProductVO> tobeconfirmed(Integer bp_smemid);
	public List<BidProductVO> completebidb(Integer bp_smemid,Integer start, Integer rows);
	public List<BidProductVO> completebids(Integer bp_smemid,Integer start, Integer rows);
//	public void checkReceive(Integer bp_id);
	
	//管理員拿全部
	public List<BidProductVO> adminGetBP(Integer start, Integer rows);
	//管理員拿單一 
	public List<BidProductVO> adminGetOneBP(Integer bp_shsta , Integer start, Integer rows);
	//管理員撥款
	public void BPdone(Integer bp_id);
	//管理員退款
	public void refund(Integer bp_id);
	//拿自己購買到的東西
	public List<BidProductVO> getSelfBuyOrdersAll(Integer bp_bmemid , Integer start, Integer rows);
	//用狀態拿自己購買到的東西
	public List<BidProductVO> getSelfBuyOrdersSTA(Integer bp_bmemid ,Integer bp_shsta ,  Integer start, Integer rows );

	public List<BidProductVO> cancelbids(Integer bp_smemid);

	
	public void addcom(Integer bp_id,String bp_comment,Integer bp_point,Integer bp_comsta);

}
