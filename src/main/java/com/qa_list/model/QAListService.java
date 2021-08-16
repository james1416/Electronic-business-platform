package com.qa_list.model;

import java.util.Date;
import java.util.List;


public class QAListService {
	private QAListDAO_interface dao;
	public QAListService() {
		dao =new QAListDAO();
	}
	public List<QAListVO> getall(){
		List<QAListVO> list = dao.getall();
		return list;
	}
	public void add(String qcon , String acon , Date tsp) {
		QAListVO qaListVO = new QAListVO();
		qaListVO.setQal_qcon(qcon);
		qaListVO.setQal_acon(acon);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		java.sql.Date time = java.sql.Date.valueOf(sdf.format(tsp));
		qaListVO.setQal_tsp(tsp);
		dao.add(qaListVO);
	}
	public QAListVO findByPKList(Integer QAL_ID){
		QAListVO qaListVO = dao.findQaListVOByPK(QAL_ID);
		return qaListVO;
	}
	public void updata(String qcon , String acon , Integer sta ,Date tsp ,Integer id) {
		QAListVO qaListVO = new QAListVO();
		qaListVO.setQal_qcon(qcon);
		qaListVO.setQal_acon(acon);
		qaListVO.setQal_sta(sta);
		qaListVO.setQal_tsp(tsp);
		qaListVO.setQal_id(id);
		dao.updata(qaListVO);
		
	}
}
