/**
 * 
 */
package com.kaicoinico.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.verify.CheckResult;
import com.nexmo.client.verify.VerifyResult;

/**
 * @Project : kaicoin-ico
 * @FileName : SMSNexmoUtil.java
 * @Date : 2017. 8. 24.
 * @작성자 : 조성훈
 * @설명 :
 **/

@Component
public class SMSNexmoUtil {
  Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);
  @Value("${sms.nexmo.key}") private String key;
  @Value("${sms.nexmo.secret}") private String secret;


  public String sendSMSCode(String mobile) {
	  int i = 0;
	  for (i=0; i<3 ; i++) {
		  if(mobile.charAt(i) != '0') {
			  break;
		  }
	  }
	  String ans = mobile.substring(i, mobile.length());
	  System.out.println(ans);
    AuthMethod auth = new TokenAuthMethod(key, secret);
    NexmoClient client = new NexmoClient(auth);
    try {
      VerifyResult request = client.getVerifyClient().verify(ans, "Kaicoin");
      System.out.println(request.getErrorText());
      return request.getRequestId();
    } catch (IOException | NexmoClientException e) {
      logger.error("NexmoException", e);
      e.printStackTrace();
      return null;
    }
  }

  public Integer checkSMSCode(String requestId, String smsCode) {
    AuthMethod auth = new TokenAuthMethod(key, secret);
    NexmoClient client = new NexmoClient(auth);
    try {
      CheckResult result = client.getVerifyClient().check(requestId, smsCode);
      return result.getStatus();
    } catch (IOException | NexmoClientException e) {
      logger.error("NexmoException", e);
      e.printStackTrace();
      return null;
    }
  }


}
