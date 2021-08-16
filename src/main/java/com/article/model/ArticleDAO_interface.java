package com.article.model;

import java.util.*;

import com.article.model.ArticleVO;

public interface ArticleDAO_interface {
	
	public void insert(ArticleVO articleVO); //文章新增

	public void update(ArticleVO articleVO); //文章標題 內容修改

	public void delete(Integer atid);  //文章刪除 STA 1改為0
	
	public void atLike(ArticleVO articleVO);//按讚
	
	public ArticleVO getoneAt(Integer atid); //文章PK搜尋
 
	public List<ArticleVO>getSomeAt(String title); //文章標題模糊查詢
	
	public List<ArticleVO> getAll(); //會員查全部文章
	
	public List<ArticleVO> admingetAll(); //管理員文章查詢(包含狀態)
//	public List<ArticleVO> getAll(Map<String, String[]> map); 
	
	public void adminUpdate(Integer atid);
	
	public List<ArticleVO> getall(Integer memid); //會員查詢自己的文章	
	
	public void deletelike(Integer atid); //移除按讚
	
	public List<ArticleVO>getHotAt(); //熱門文章
	
	public ArticleVO getoneHot(); //拿單一熱門文章
	
	public ArticleVO getoneNew(); //拿單一最最新文章
}
