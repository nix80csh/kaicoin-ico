package com.kaicoinico.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kaicoinico.entity.Account;

public class QRCord {
	
	@Value("${cloudinary.apikey}") private String apikey;
	@Value("${cloudinary.apisecret}") private String apisecret;
	@Value("${cloudinary.cloudname}") private String cloudname;
	@Autowired Account account;
	public void getQRCord() {
	 try {
//         File file = null;
//         // 큐알이미지를 저장할 디렉토리 지정
//         file = new File("D:\\qrtest");
//         if(!file.exists()) {
//             file.mkdirs();
//         }
         
		 // 코드인식시 링크걸 URL주소
         String codeurl = new String(account.getKaicoinAddr().getBytes("UTF-8"), "ISO-8859-1");
         // 큐알코드 바코드 생상값
         int qrcodeColor =   0xFF2e4e96;
         // 큐알코드 배경색상값
         int backgroundColor = 0xFFFFFFFF;
          
         
         QRCodeWriter qrCodeWriter = new QRCodeWriter();
         // 3,4번째 parameter값 : width/height값 지정
         BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE,200, 200);
         MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);
         BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
         // ImageIO를 사용한 바코드 파일쓰기
         ImageIO.write(bufferedImage, "png", new File("D:\\qrtest\\qrcode.png"));
//         ImageIO.write(bufferedImage, "png", output);
          
     } catch (Exception e) {
         e.printStackTrace();
     }  
	}
	
	
	
	
	public Map uploadToCloudinary() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cloudname", cloudname);
		map.put("apikey", apikey);
		map.put("apisecret", apisecret);
		Cloudinary cloudinary = new Cloudinary(map);
		Map uploadResult = cloudinary.uploader().upload("http://nick.mtvnimages.com/nick/polls/polls-desktop/nick-likes/what-friendship-is-most-like-yours/SpongeBob-2.jpg?quality=0.75", ObjectUtils.emptyMap());
		System.out.println(uploadResult);
		String publicId = (String) uploadResult.get("public_id");
		System.out.println(publicId);
		return uploadResult;
	}
}
