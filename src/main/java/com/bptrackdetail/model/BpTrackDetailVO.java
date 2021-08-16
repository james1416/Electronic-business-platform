package com.bptrackdetail.model;

import java.io.Serializable;

public class BpTrackDetailVO implements Serializable {
	private Integer tr_bpid;
	private Integer tr_memid;

	public Integer getTr_bpid() {
		return tr_bpid;
	}

	public void setTr_bpid(Integer tr_bpid) {
		this.tr_bpid = tr_bpid;
	}

	public Integer getTr_memid() {
		return tr_memid;
	}

	public void setTr_memid(Integer tr_memid) {
		this.tr_memid = tr_memid;
	}

}
