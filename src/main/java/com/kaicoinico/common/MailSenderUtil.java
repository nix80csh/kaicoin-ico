/**
 * 
 */
package com.kaicoinico.common;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Project : kaicoin-ico
 * @FileName : MailSenderUtil.java
 * @Date : 2017. 8. 22.
 * @작성자 : 조성훈
 * @설명 :
 **/
@Component
public class MailSenderUtil {
  Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);

  @Autowired
  JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public boolean sendMail(String toEmail, String subject, String content) {
    MimeMessage msg = javaMailSender.createMimeMessage();
    try {
      msg.setSubject(subject);
      msg.setText(content, "UTF-8", "html");
      msg.addRecipient(RecipientType.TO, new InternetAddress(toEmail));
      msg.setFrom(new InternetAddress(fromEmail));

      javaMailSender.send(msg);
      return true;
    } catch (Exception e) {
      logger.error("MailException", e);
      return false;
    }

  }
}
