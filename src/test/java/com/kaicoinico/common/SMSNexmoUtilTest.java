/**
 * 
 */
package com.kaicoinico.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kaicoinico.service.MypageService;

/**
 * @Project : kaicoin-ico
 * @FileName : SMSNexmoUtilTest.java
 * @Date : 2017. 8. 24.
 * @작성자 : 조성훈
 * @설명 :
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSNexmoUtilTest {

  @Autowired SMSNexmoUtil smsNexmoUtil;
  @Autowired MypageService mypageService;

  @Test
  public void sendSMSCode() {
	  String mobile = "0019097538153";
	  int i = 0;
	  for (i=0; i<3 ; i++) {
		  if(mobile.charAt(i) != '0') {
			  break;
		  }
	  }
	  String ans = mobile.substring(i, mobile.length());
	  System.out.println(ans);
    //String requestId = smsNexmoUtil.sendSMSCode("821049209123");
    // System.out.println(requestId);
    //assertNotNull(requestId);
  }
/*
  @Test
  public void checkSMSCode() {
    Integer status = smsNexmoUtil.checkSMSCode("e73b59200c774fc189655bfb7c63e1e0", "5909");
    assertSame(0, status);
  }
*/
  
  @Test
  public void sendSMS() {
	 String test = mypageService.sendSMSCode("andrew@greenstage.co.kr");
  }
  
  
}
