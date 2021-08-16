package com.articlemessage.model;

import java.util.List;

public class ArticleMessageService {
	
	private ArticleMessageDAO_interface dao;

	public ArticleMessageService(){
		dao = new ArticleMessageDAO();
	}
//	留言查全部
	public List<ArticleMessageVO> getAll(){
		return dao.getAll();
	}
//	會員單一留言資訊
	public ArticleMessageVO findmessagePK(Integer msgid) {
		return dao.findByPrimaryKey(msgid);
	}
	//新增留言
	public ArticleMessageVO addArtMsg(Integer atid, Integer memid, String msgtext) {
		ArticleMessageVO articleMessageVO = new ArticleMessageVO();
		articleMessageVO.setAtid(atid);
		articleMessageVO.setMemid(memid);
		articleMessageVO.setMsgtext(msgtext);
		dao.insertAtmessage(articleMessageVO);
		return articleMessageVO;
	}
	//刪除留言
	public void deleteMsg(Integer msgid) {
		dao.delete(msgid);
	}
	//留言修改
	public void updateMsg(Integer memid,Integer msgid, String msgtext) {
		ArticleMessageVO artMsg = new ArticleMessageVO();
		artMsg.setMsgtext(msgtext);
		artMsg.setMsgid(msgid);
		artMsg.setMemid(memid);
		dao.update(artMsg);
	}
	
	
	
	
	
	
}
