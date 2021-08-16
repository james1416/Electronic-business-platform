package com.articlemessage.model;

import java.util.List;


public interface ArticleMessageDAO_interface {

	public void insertAtmessage(ArticleMessageVO articleMessageVO);
	
	public void deleteAtmessage(ArticleMessageVO articleMessageVO);
	
	public List<ArticleMessageVO> getAll();
	
	public void delete(Integer msgid);
	
	public ArticleMessageVO findByPrimaryKey(Integer msgid);
	
	public void update(ArticleMessageVO articleMessageVO);
	
}
