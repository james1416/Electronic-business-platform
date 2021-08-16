package com.qa_list.model;

import java.util.List;

public interface QAListDAO_interface {
	List<QAListVO> getall();
	void add(QAListVO qaListVO);
	void updata(QAListVO qaListVO);
	QAListVO findQaListVOByPK(Integer QAL_ID);
}
