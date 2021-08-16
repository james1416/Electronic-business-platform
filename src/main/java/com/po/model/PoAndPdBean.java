package com.po.model;

import com.pd.model.PdVO;

public class PoAndPdBean{
	private PoVO poVO;
	private PdVO pdVO;
	public PoVO getPoVO() {
		return poVO;
	}
	public void setPoVO(PoVO poVO) {
		this.poVO = poVO;
	}
	public PdVO getPdVO() {
		return pdVO;
	}
	public void setPdVO(PdVO pdVO) {
		this.pdVO = pdVO;
	}
	public PoAndPdBean(PoVO poVO, PdVO pdVO) {
		super();
		this.poVO = poVO;
		this.pdVO = pdVO;
	}
	public PoAndPdBean() {
		super();
	}
	
}
