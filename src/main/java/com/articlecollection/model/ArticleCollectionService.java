package com.articlecollection.model;

import java.util.List;
import java.util.Set;

public class ArticleCollectionService {
	
	private ArticleCollection_interface dao;
	
	public ArticleCollectionService() {
		dao = new ArticleCollectionDAO();
	}
	
	//會員查詢收藏的文章
	public List<ArticleCollectionVO> getAll(Integer memid){
		return dao.getAll(memid);
	}
	//會員移除收藏的文章
	public void deleteforCol(Integer atid, Integer memid) {
			dao.delete(atid, memid);
	}
	//會員新增收藏
	public ArticleCollectionVO addCol(Integer atid, Integer memid) {
	ArticleCollectionVO articleCollectionVO = new ArticleCollectionVO();
	articleCollectionVO.setAtid(atid);
	articleCollectionVO.setMemid(memid);
	dao.insert(articleCollectionVO);
	return articleCollectionVO;
	}
	public List<ArticleCollectionVO> getAll(){
		return dao.getAll();
	}
	public ArticleCollectionVO checkCol(Integer memid,Integer atid) {
		return dao.findMemByPK(memid, atid);
	}
}