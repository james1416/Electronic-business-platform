package com.category.model;

import java.util.List;

public class CategoryService {
	public CategoryDAO_interface dao;
	
	public CategoryService() {
		dao = new CategoryDAO();
	}
	
	public List<CategoryVO> getall(){
		return dao.getall();
	}
}
