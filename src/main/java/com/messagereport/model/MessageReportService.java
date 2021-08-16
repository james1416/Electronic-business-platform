package com.messagereport.model;

import java.util.List;

public class MessageReportService {

	private MessageReportDAO_interface dao;
	
	public MessageReportService() {
		dao = new MessageReportDAO();
	}
	//會員留言檢舉清單
	public List<MessageReportVO>getAll(Integer memid){
	 return	dao.getAll(memid);
	}
	//會員文章留言檢舉
	public MessageReportVO addMessageRep(Integer msgid , Integer memid, String msrtext) {
		MessageReportVO messageVO = new MessageReportVO();
		messageVO.setMsgid(msgid);
		messageVO.setMemid(memid);
		messageVO.setMsrtext(msrtext);
		dao.insert(messageVO);
		return messageVO;
	}

	public void reportPass(Integer repid) {
		dao.reportPass(repid);
	}
	public void reportReject(Integer repid) {
		dao.reportReturn(repid);
	}
	public List<MessageReportVO>admingetAll(){
		return dao.admingetAll();
	}
}
