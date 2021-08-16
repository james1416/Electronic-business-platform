package com.bpclassification.model;

import java.util.List;

public class BpClassificationService {

	private BpClassificationDAO_interface dao;

	public BpClassificationService() {
		dao = new BpClassificationDAO();
	}

	public BpClassificationVO addBpc(String bpc_cgname) {
		BpClassificationVO bpcVO = new BpClassificationVO();
		bpcVO.setBpc_cgname(bpc_cgname);
		dao.insert(bpcVO);
		return bpcVO;
	}

	public BpClassificationVO updateBpc(Integer bpc_id, String bpc_cgname) {
		BpClassificationVO bpcVO = new BpClassificationVO();
		bpcVO.setBpc_id(bpc_id);
		bpcVO.setBpc_cgname(bpc_cgname);
		dao.update(bpcVO);
		return bpcVO;
	}

	public void deleteBpc(Integer bpc_id) {
		BpClassificationVO bpcVO = new BpClassificationVO();
		dao.delete(bpc_id);
	}

	public BpClassificationVO getOneBpc(Integer bpc_id) {
		return dao.findByPrimaryKey(bpc_id);
	}

	public List<BpClassificationVO> getAll() {
		return dao.getAll();
	}
}
