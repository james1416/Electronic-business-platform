package com.articlecollection.model;

import java.util.List;
import java.util.Set;

public interface ArticleCollection_interface {
	
	public void insert(ArticleCollectionVO articleCollectionVO); //會員文章收藏
	
	public ArticleCollectionVO findMemByPK(Integer mem_id , Integer atid);
	
	public List<ArticleCollectionVO> getAll();
	
	public void delete(Integer atid, Integer memid); //會員移除收藏文章
	
	public List<ArticleCollectionVO> getAll(Integer memid); //會員收藏文章查詢
	
}
