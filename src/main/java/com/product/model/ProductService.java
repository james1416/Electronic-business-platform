package com.product.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

import com.Bean.PageBean;

public class ProductService {
	private ProductDAO_interface dao;
	public ProductService() {
		dao = new ProductDAO();
	}
	public List<ProductVO> getall(){
		return dao.getall();
	}
	
	public List<ProductVO> getSelf(Integer p_memid){
		return dao.getSelf(p_memid);
	}
	public ProductVO findPD(Integer p_id) {
		return dao.findPD(p_id);
	}
	
	public void updataPD(Integer p_id,String p_name ,Integer p_price , Integer p_stock , Integer p_cgid , String p_desc ,Integer p_sta ,List<Part> list , String needDel[]) {
		ProductVO productVO = new ProductVO();
		List<Part> imgs = new ArrayList<Part>();
		productVO.setP_id(p_id);
		productVO.setP_name(p_name);
		productVO.setP_price(p_price);
		productVO.setP_stock(p_stock);
		productVO.setP_desc(p_desc);
		productVO.setP_cgid(p_cgid);
		productVO.setP_sta(p_sta);
		for(Part part :list) {
			if("piimage".equals(part.getName())) {
				imgs.add(part);
			}
		}
		Integer pi_id[] ;
		if(needDel == null) 
			pi_id = new Integer[0];
		else 
			pi_id = new Integer[needDel.length];
		
		for (int i = 0; i < pi_id.length; i++) {
			pi_id[i] = new Integer(needDel[i]);
		}
		
		
		dao.updataPD(productVO , imgs , pi_id);
	}
	
	public void addPD(Integer p_memid ,Integer p_cgid  ,String p_name , Integer p_price , Integer p_stock , String p_desc ,List<Part> list) {
		ProductVO productVO = new ProductVO();
		List<Part> imgs = new ArrayList<Part>();
		productVO.setP_memid(p_memid);
		productVO.setP_cgid(p_cgid);
		productVO.setP_name(p_name);
		productVO.setP_price(p_price);
		productVO.setP_stock(p_stock);
		productVO.setP_desc(p_desc);
		// 去除除了圖片以外的東西
		for(Part img : list) {
			if("piimage".equals(img.getName())){
				imgs.add(img);
				System.out.println("拿到圖片了");
			}
		}
		
		dao.addPD(productVO, imgs);
	}
	
	public Integer findPDforAJAX(Integer p_id) {
		return dao.findPDforAJAX(p_id);
	}
	
	public PageBean<ProductVO> getAllByPage(String _currentPage,String _rows){
		Integer currentPage = new Integer(_currentPage);
		Integer rows = new Integer(_rows);
		PageBean<ProductVO> pb = new PageBean<ProductVO>();
		
		pb.setCurrentPage(currentPage);
		pb.setRows(rows);
		Integer totalCount = dao.findTotalCount();
		pb.setTotalCount(totalCount);
		
		Integer start = (currentPage -1 ) * rows;
		List<ProductVO> list= dao.findByPage(start, rows);
		pb.setList(list);
		
		Integer totalPage = totalCount % rows  == 0 ?  totalCount/rows : (totalCount/rows) + 1 ;
		pb.setTotalPage(totalPage);
		
		return pb;
	}
	
	public List<ProductVO> findMyPDbyPage(Integer mem_id ,Integer start,Integer rows){
		return dao.findMyPDbyPage(mem_id, start, rows);
	}
	
	public PageBean<ProductVO> searchMyPDbyPage(String search, Integer p_cgid,String  _currentPage, String _rows) {
		Integer currentPage = new Integer(_currentPage);
		Integer rows = new Integer(_rows);
		PageBean<ProductVO> pb = new PageBean<ProductVO>();
		
		pb.setCurrentPage(currentPage);
		pb.setRows(rows);
		Integer totalCount = dao.searchCount(search, p_cgid);
		pb.setTotalCount(totalCount);
		
		Integer start = (currentPage -1 ) * rows;
		System.out.println(start);
		List<ProductVO> list= dao.searchMyPDbyPage(search, p_cgid, start, rows);
		pb.setList(list);
		
		Integer totalPage = totalCount % rows  == 0 ?  totalCount/rows : (totalCount/rows) + 1 ;
		pb.setTotalPage(totalPage);
		return pb;
	}
	
	public Set<String> inputPoint(String input , Integer p_cgid){
		return dao.inputPoint(input, p_cgid);
	}
	
}
