package com.pi.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;


public class PiService {
	private PiDAO_interface dao;
	
	public PiService() {
		dao = new PiDAO();
	}
	
	
//	拿單張
	public String get(Integer pi_pdid) {
		
		byte[] img = dao.getone(pi_pdid);		
		String data ;

		if(img == null) {
			data = showDefaultImg();
		}else {
			data = Base64.getEncoder().encodeToString(img);
		}
		
		return data;
	}
	
// 拿全部
	public List<PiVO> getall(Integer pi_pdid){
		return dao.getall(pi_pdid);
	}
	
//  將PiVO的 pi_desc的陣列化做字串才可以輸出
	public String out(byte[] pi_desc) {
		String data;
		data = Base64.getEncoder().encodeToString(pi_desc);
		return data;
	}
	
	public boolean check(Integer pd_id) {
		return dao.check(pd_id);
	}
// 若商品新增時 沒有圖片會給予一張預設的圖片
	public String showDefaultImg() {
		String data = null;
		FileInputStream fis = null;
		try {
//			fis = new FileInputStream("C:/CFA101_Workspace/CFA101G4/src/main/webapp/front-end/images/noPDIMG.png");
			fis = new FileInputStream("C:/CFA101G4_workspace/CFA101G4/WebContent/Img/noPDIMG.png");
			
			try {
				byte[] noPDIMG = new byte[fis.available()];
				fis.read(noPDIMG);
				fis.close();
				data = Base64.getEncoder().encodeToString(noPDIMG);
			} catch (IOException e) {
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
}

