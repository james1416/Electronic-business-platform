package com.article.model;

import java.util.List;

public class ArticleService {

		private ArticleDAO_interface dao;

		public ArticleService() {
			dao = new ArticleDAO();
		}
		//文章新增
		public  ArticleVO addArt(Integer memid, String title , String text) {
			
			ArticleVO articleVO = new ArticleVO();
			articleVO.setMemid(memid);
			articleVO.setTitle(title);
			articleVO.setText(text);
			dao.insert(articleVO);
			return articleVO;
		}
		//文章標題模糊查詢
		public List<ArticleVO> getSomeAt(String title) {
			return dao.getSomeAt(title);
		}
		//刪除文章
		public void deleteArticle(Integer atid) {
			dao.delete(atid);
		}
		//拿單一文章資訊
		public ArticleVO getOneAt(Integer atid) {
			return dao.getoneAt(atid);
		}
		//查全部
		public List<ArticleVO> getAll() {
			return dao.getAll();
		}
		//管理員拿全部文章
		public List<ArticleVO> admingetAll(){
			return dao.admingetAll();
		}
		//文章按讚
		public void atLike(Integer atid) {
			ArticleVO articleVO = new ArticleVO();
			articleVO.setAtid(atid);
			dao.atLike(articleVO);
		}
		//修改文章
		public void updateArt(Integer atid, String title,String text) {
			ArticleVO articleVO = new ArticleVO();
			articleVO.setAtid(atid);
			articleVO.setTitle(title);
			articleVO.setText(text);
			dao.update(articleVO);	
		}	
		//恢復文章(暫無使用)
		public void adminUpdate(Integer atid) {
			dao.adminUpdate(atid);
		}
		//會員查自己的文章
		public List<ArticleVO> getAll(Integer memid){
			return dao.getall(memid);
		}
		//移除按讚
		public void deletelike(Integer atid) {
			dao.deletelike(atid);;
		}
//		熱門文章
		public List<ArticleVO>getHotAT(){
			return dao.getHotAt();
		}
		//單一熱門文章
		public ArticleVO getoneHot() {
			return dao.getoneHot();
		}
		//單一最新文章
		public ArticleVO getoneNew() {
			return dao.getoneNew();
		}
}
