package thisistest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {
	public static void main(String [] args) {
	
		
		List<Object> i = new ArrayList<Object>();
		i.add(1);
		i.add(2);
		List<Object> o = new ArrayList<Object>();
		o.add("幹");
		o.add("麻");
		
		List<List<Object>> io= new ArrayList<List<Object>>();
		io.add(i);
		io.add(o);
		System.out.print((Integer)io.get(0).get(1));
		
		
		
	}
}
