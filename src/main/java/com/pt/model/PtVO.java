package com.pt.model;

import java.io.Serializable;
import java.sql.Date;

import org.eclipse.jdt.internal.compiler.batch.Main;

public class PtVO implements Serializable {
	private Integer pt_pdid;
	private Integer pt_memid;

	PtVO(){		
	
	}

	public Integer getPt_pdid() {
		return pt_pdid;
	}

	public void setPt_pdid(Integer pt_pdid) {
		this.pt_pdid = pt_pdid;
	}

	public Integer getPt_memid() {
		return pt_memid;
	}

	public void setPt_memid(Integer pt_memid) {
		this.pt_memid = pt_memid;
	}
	
}
