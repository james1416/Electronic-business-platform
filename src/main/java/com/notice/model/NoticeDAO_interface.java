package com.notice.model;

import java.sql.Connection;
import java.util.List;

public interface NoticeDAO_interface {
	
	public List<NoticeVO> getall(Integer nt_memid);
	
	public Integer getcount(Integer mem_id);
	
	public int deleteNotice(Integer mem_id);
	//發給賣家
	void add(Integer sta , Integer notifyOther , Integer Self ,Connection con);
	//發給買家
	void toB_org(Integer sta, Integer notifyOther, Integer SelforODID , Connection con);

	
	void StoB_PRE(Integer sta , Integer notifyOther , Integer SelforODID ,Connection con);
	
	void BtoS_PRE(Integer sta , Integer notifyOther , Integer SelforODID ,Connection con);
	
	void toB_BP(Integer sta , Integer notifyOther , Integer SelforODID ,Connection con);
	
	void toS_BP(Integer sta , Integer notifyOther , Integer SelforODID ,Connection con);
	
	void changeViewTo1(Integer nt_id);
}
