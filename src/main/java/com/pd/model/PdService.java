package com.pd.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.Part;

import com.po.model.PoDAO;

public class PdService {
	
	private PdDAO_interface dao;
 
	public PdService() {
		dao = new PdDAO();
	}
	public void  addPd(Integer pd_smemid,Integer pd_pcid,String pd_name,
			  Integer pd_price,Integer pd_no,String pd_desc,Timestamp pd_sdate,
	          Timestamp pd_edate,Timestamp pd_spdate,Integer pd_sta,List<Part> list) {

		PdVO pdVO = new PdVO();
		List<Part> imgs = new ArrayList<Part>();
		pdVO.setPd_smemid(pd_smemid);
		pdVO.setPd_pcid(pd_pcid);
		pdVO.setPd_name(pd_name);
		pdVO.setPd_price(pd_price);
		pdVO.setPd_no(pd_no);
		pdVO.setPd_desc(pd_desc);
		pdVO.setPd_sdate(pd_sdate);
		pdVO.setPd_edate(pd_edate);
		pdVO.setPd_spdate(pd_spdate);
		pdVO.setPd_sta(pd_sta);
		for(Part img : list) {
			if("piimage".equals(img.getName())){
				System.out.println("圖片抓到");
				System.out.println(img.getName());
				imgs.add(img);
				
			}
		}
		
		//新增完商品後 回傳 商品編號
		
		Integer pd_id=dao.addpd(pdVO, imgs);
		//取得當下時間毫秒數  以及預購開始時間的毫秒數
		Timestamp d = new Timestamp(System.currentTimeMillis());
		long system_time =d.getTime();
		long pd_start_time = pd_sdate.getTime();
		//取得 開始預購時間 減掉當下時間 的時間差 判斷是否開始預購
		long isStart = pd_start_time - system_time;
		
		class PdSTimerTask extends TimerTask {
		
		public void run() {
			   Integer pd_sta = 1;
		     dao.update_sta(pd_sta, pd_id);
		  }
		}
		
		Timer StartTimer = new Timer();
		StartTimer.schedule(new PdSTimerTask() , isStart);
		
		
		//判斷是否結束拍賣 並確認是否達到成團人數
		long pd_end_time = pd_edate.getTime();
		//結束時間 - 開始時間 取得 總毫秒數
		
		long isEnd = pd_end_time - system_time;
		
		class PdETimerTask extends TimerTask{
			public void run() {
				Integer pd_sta = 2;
				dao.update_sta(pd_sta, pd_id);
				
				// 下架之後要去計算是否達到成團人數 並且更改訂單狀態
				PoDAO podao = new PoDAO();
				Integer counts = podao.count(pd_id);
				
				PdDAO pddao = new PdDAO();
				Integer pd_no =pddao.findNumber(pd_id);
				
				//若沒有人購買 則不需要更改訂單狀態
				if(counts == 0){
					System.out.println("沒有任何人購買");
					
				//若達到成團數量 將全部訂單狀態改成2 (達標等待出貨)
				}else if( counts >= pd_no) {
					System.out.println("達到成團數量");
					Integer po_sta = 2;
					podao.updateAllSta(po_sta, pd_id);
				}
				//若未達標 將全部訂單狀態改成1 (未達標不出貨)	
				 else {
					System.out.println("未達最低預購數量");
					Integer po_sta = 1;
					podao.updateAllSta(po_sta, pd_id);
				}
			}
		}
		
		Timer EndTimer = new Timer();
		EndTimer.schedule(new PdETimerTask(), isEnd);
		
		}


	
	public void updatePd(Integer pd_smemid,Integer pd_pcid,String pd_name,Integer pd_price,
				Integer pd_no,String pd_desc,Timestamp pd_sdate,
	            Timestamp pd_edate,Timestamp pd_spdate,Integer pd_sta,Integer pd_id,
	            List<Part> list , String needDel[]) {

		PdVO pdVO = new PdVO();
		List<Part> imgs = new ArrayList<Part>();
		pdVO.setPd_smemid(pd_smemid);
		pdVO.setPd_pcid(pd_pcid);
		pdVO.setPd_name(pd_name);
		pdVO.setPd_price(pd_price);
		pdVO.setPd_no(pd_no);
		pdVO.setPd_desc(pd_desc);
		pdVO.setPd_sdate(pd_sdate);
		pdVO.setPd_edate(pd_edate);
		pdVO.setPd_spdate(pd_spdate);
		pdVO.setPd_sta(pd_sta);
		pdVO.setPd_id(pd_id);
		for(Part part : list) {
			if("piimage".equals(part.getName())){
				imgs.add(part);
			}
		}
		Integer pi_imgid[];
		if(needDel == null)	
			pi_imgid = new Integer[0];
		else
			pi_imgid = new Integer[needDel.length];
		for(int i =0 ; i<pi_imgid.length ; i++) {
			
			pi_imgid[i] = new Integer(needDel[i]);
		}
		
		
		dao.update(pdVO, imgs, pi_imgid);
	}
	
	
	
	public  PdVO getOnePd(Integer pd_id) {
		return dao.findByPk(pd_id);
	}

	
	public List<PdVO> getall(){
		return dao.getall();
	}
	
	
	public PdVO getOnePdid(Integer pd_smemid, String pd_name,String pd_desc,Timestamp pd_sdate) {
		return dao.findPdid(pd_smemid, pd_name, pd_desc, pd_sdate);
	}
	
	public List<PdVO> getSelfALL(Integer pd_smemeid){
		return dao.getSelfAll(pd_smemeid);
	}
	// AJAX GET自己販售商品
	public List<PdVO> findMyPDbyPage(Integer pd_smemid ,Integer start,Integer rows){
		return dao.findMyPDbyPage(pd_smemid, start, rows);
	}
	public void offPd(Integer pd_id , Integer pd_sta) {
		 dao.offPd(pd_id, pd_sta);
	}
}
