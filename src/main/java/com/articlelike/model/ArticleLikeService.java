package com.articlelike.model;

import java.util.List;

public class ArticleLikeService {
	private ArticleLikeDAO_interface dao;
	
	public ArticleLikeService() {
		dao = new ArticleLikeDAO();
	}
	
	public List<ArticleLikeVO> getAll() {
		return dao.getall();
	}
	
	public void deleteforlike(Integer atid,Integer memid) {
			dao.delete(atid, memid);
	}
	
	public ArticleLikeVO addlike(Integer atid, Integer memid) {
		ArticleLikeVO articlelikeVO = new ArticleLikeVO();
		articlelikeVO.setAtid(atid);
		articlelikeVO.setMemid(memid);
		dao.insert(articlelikeVO);
		return articlelikeVO;
	}
	
	public List<ArticleLikeVO> getAllmylike(Integer memid) {
		return dao.getAllmylike(memid);
	}
	public ArticleLikeVO getoneLK(Integer memid,Integer atid) {
		return dao.getonelike(memid, atid);
	}
}

