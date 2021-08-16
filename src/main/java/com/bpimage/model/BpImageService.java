package com.bpimage.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

public class BpImageService {
	private BpImageDAO_interface dao;
	
	public BpImageService() {
		dao = new BpImageDAO();
	}
	
	//拿單張
	public String get(Integer bpi_bpid) {
		byte[] img = dao.getOne(bpi_bpid);
		String data;
		if (img == null) {
			data = showDefault();
		}else {
			data = Base64.getEncoder().encodeToString(img);
		}
		return data;
	}
	
	//拿全部
	public List<BpImageVO> getall(Integer bpi_bpid){
		return dao.getAll(bpi_bpid);
	}
	
	public String out(byte[] bpi_img) {
		String data;
		data = Base64.getEncoder().encodeToString(bpi_img);
		return data;
	}
	
	public boolean check(Integer bpi_bpid) {
		return dao.check(bpi_bpid);
	}
	public String showDefault() {
		String data = null;
		FileInputStream fis = null;
		try {
//			fis = new FileInputStream("D:/CFA101_Webapp/CFA101G4/WebContent/Img/noPDIMG.png");
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
