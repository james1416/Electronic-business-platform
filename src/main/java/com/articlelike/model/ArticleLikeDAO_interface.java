package com.articlelike.model;

import java.util.List;

public interface ArticleLikeDAO_interface  {

	public List<ArticleLikeVO> getall();
	
	public void insert(ArticleLikeVO articleLikeVO);
	
	public void delete(Integer atid, Integer memid);
	
	public List<ArticleLikeVO> getAllmylike(Integer memid);
	
	public ArticleLikeVO getonelike(Integer memid , Integer atid);
}
	