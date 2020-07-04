/**
 * 
 */
package com.kaicoinico.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kaicoinico.dto.AuthDto.SignupDto;
import com.kaicoinico.dto.MypageDto.RegistICODto;
import com.kaicoinico.service.AuthService;
import com.kaicoinico.service.MypageService;

/**
 * @Project : kaicoin-ico
 * @FileName : MailSenderUtilTest.java
 * @Date : 2017. 8. 22.
 * @작성자 : 조성훈
 * @설명 :
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderUtilTest {

  @Autowired MailSenderUtil mailSenderUtil;
  @Autowired MypageService mypageService;
  @Autowired AuthService authService;
   RegistICODto registICODto;
   SignupDto signupDto;
  
  
  @Test
  public void sendMailTest() {
    assertEquals(true, mailSenderUtil.sendMail("andrew@greenstage.co.kr", "test", "<h1>test</h1>"));
  }
  
  
  
/*  @Test
  public void registICO() {
	  RegistICODto registICODto = new RegistICODto();
	  registICODto.setEthereumAddr("0x0a8cca1590513123c551b5a20554ed53aa5b2841");
	  registICODto.setIdfAccount(174);
	  
	  String test = mypageService.registICO(registICODto);
  }*/
	  
/*  @Test
  public void signup() {
	 SignupDto signupDto = new SignupDto();
	  
	 signupDto.setEmail("umanking@naver.com");
	 signupDto.setName1("andrew");
	 signupDto.setName2("han");
	 SignupDto test = authService.signup(signupDto);
	  
  }*/
  
/*  @Test
  public void sendPasswordRestMail() {
	  mypageService.sendPasswordResetMail("andrew@greenstage.co.kr");
  }*/
  
  
}
