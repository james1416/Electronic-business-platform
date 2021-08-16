package com.productImg.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class ProductImgService {
	ProductImgDAO_interface dao ;
	public ProductImgService() {
		dao = new ProductImgDAO();
	}
	
	
	// 用不到了 因為跟新增商品一起囉
//	public void insert(Integer pi_pid , byte[] pi_image) {
//		ProductImgVO productImgVO =  new ProductImgVO();
//		productImgVO.setPi_pid(pi_pid);
//		productImgVO.setPi_image(pi_image);
//		dao.addPIMG(p_id, imgs);(productImgVO);
//	}
	
	
//	拿單張
	public String get(Integer pi_pid) {
		byte[] img = dao.getone(pi_pid);
		String data ;
		if( img == null) {
			data = showDefault();
		}else {
			data = Base64.getEncoder().encodeToString(img);
		}
		return data;
	}
	
// 拿全部
	public List<ProductImgVO> getall(Integer pi_pid){
		return dao.getall(pi_pid);
	}
	
// 這個是一個小方法,將ProductImgVO的 pi_image的陣列化做字串才可以輸出
	public String out(byte[] pi_image) {
		String data;
		if(pi_image == null) {
			data=showDefault();
		}else {
			data = Base64.getEncoder().encodeToString(pi_image);
		}
		return data;
	}
	
	public boolean check(Integer pi_pid) {
		return dao.check(pi_pid);
	}
	
	public String showDefault() {
		String data = null;
		FileInputStream fis = null;
		try {
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
