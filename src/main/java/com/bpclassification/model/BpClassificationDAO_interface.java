package com.bpclassification.model;

import java.util.*;

public interface BpClassificationDAO_interface {
	public void insert(BpClassificationVO bpcVO);
	public void update(BpClassificationVO bpcVO);
	public void delete(Integer bpc_id);
	public BpClassificationVO findByPrimaryKey(Integer bpc_id);
	public List<BpClassificationVO> getAll();
}
