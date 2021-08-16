package com.admin.model;

import java.util.Map;
import java.util.Set;

public class AdminSearch {
	public static String check(String col , String value) {
		String con = null;
		
		if("admin_id".equals(col) ) {
			con = col + "=" + value;
		}else if ("admin_acc".equals(col) || "admin_pw".equals(col)) {
			con = col + " like '%" + value + "%'";
		}
		return con;
	}
	
	
	
	
	public static String get(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for(String key : keys) {
			String value = map.get(key)[0];
			if(value != null && value.trim().length() != 0 && !"action".equals(key)) {
				i++;
				String con = check(key, value.trim());
				
				if(i == 1)
					sb.append(" where " + con);
				else
					sb.append(" and " + con);
			}
		}
		
		return sb.toString();
	}
}
