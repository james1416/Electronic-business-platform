package com.pd.model;

import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.Part;

public interface PdDAO_interface {
    
	//新增預購商品
	public Integer addpd(PdVO pdVO,List<Part> imgs); 
    
	//修改預購商品
    public void update(PdVO pdVO, List<Part> list, Integer del[]);
    
    //尋找一個商品
    public PdVO findByPk(Integer pd_id);
    
    public PdVO findPdid(Integer pd_smemid, String pd_name, String pd_desc, Timestamp pd_sdate);
   
    //叫出全部商品
    public List<PdVO> getall();
    // 叫出會員個人商品
    public List<PdVO> getSelfAll(Integer pd_smemid);
    
    //更改 預購商品狀態
    public void update_sta(Integer pd_sta , Integer pd_id);
    
    
    //查詢商品的成團人數
    public Integer findNumber(Integer pd_id);
    
    // 叫出會員個人商品 AJAX
    public List<PdVO> findMyPDbyPage(Integer pd_smemid , Integer start , Integer rows);

    //下架商品
    public void offPd(Integer pd_id , Integer pd_sta);
}


