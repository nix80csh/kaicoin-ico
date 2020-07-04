/**
 * 
 */
package com.kaicoinico.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaicoinico.common.BeanUtil;
import com.kaicoinico.common.EmailValidMailboxLayerUtil;
import com.kaicoinico.common.EncryptionSha256Util;
import com.kaicoinico.common.GSChainUtil;
import com.kaicoinico.common.MailSenderUtil;
import com.kaicoinico.common.SMSNexmoUtil;
import com.kaicoinico.common.TokenUtil;
import com.kaicoinico.dto.AuthDto.CheckSMSCodeDto;
import com.kaicoinico.dto.AuthDto.SigninDto;
import com.kaicoinico.dto.AuthDto.SignupDto;
import com.kaicoinico.entity.Account;
import com.kaicoinico.exception.LogicErrorList;
import com.kaicoinico.exception.LogicException;
import com.kaicoinico.repository.AccountRepo;

/**
 * @Project : kaicoin-ico
 * @FileName : AuthService.java
 * @Date : 2017. 8. 21.
 * @작성자 : 조성훈
 * @설명 :
 **/

@Service
@Transactional
public class AuthService {
  @Autowired AccountRepo accountRepo;
  @Autowired EmailValidMailboxLayerUtil emailValidMailboxLayerUtil;
  @Autowired AuthenticationManager authenticationManager;
  @Autowired TokenUtil tokenUtil;
  @Autowired MailSenderUtil mailSenderUtil;
  @Autowired SMSNexmoUtil smsNexmoUtil;
  @Autowired GSChainUtil gschainUtil;

  @Value("${kaicoin.token.header}") private String tokenHeader;
  @Value("${service.domain}") private String serviceDomain;

  /**
   * @작성일 : 2017. 8. 21.
   * @설명 :
   * 
   *     <pre>
   *     회원가입
   *     중복된 이메일이 있는지 체크 후 본인인증 확인메일 전송
   *     Sent Mail의 의미로 "S"를 state에 저장하고  DB에 저장
   *     </pre>
   **/
  public SignupDto signup(SignupDto signupDto) {

    // duplicate exception
    if (accountRepo.findByEmail(signupDto.getEmail()) != null)
      throw new LogicException(LogicErrorList.DuplicateEntity_Account);

    // 존재하지 않는 이메일 체크
    if (!emailValidMailboxLayerUtil.checkValid(signupDto.getEmail()))
      throw new LogicException(LogicErrorList.DoesNotExist_Email);

    String fullName = signupDto.getName2() + signupDto.getName1();
    String gencode = EncryptionSha256Util.getGenCode();
    String confirmedUrl =

        "http://" + serviceDomain + "/mailConfirm/" + gencode + "/" + signupDto.getEmail() + "/";

   /* String content1 =
        "<div style='margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;'>"
            + "<div style='display:inline-block;padding:60px;background:#fff;'><table style='width:580px;ma"
            + "rgin:0 auto;background:#fff;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Goth"
            + "ic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;'><tbody><thead><tr><td> <img src="
            + "'http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503814966/KaicoinICO/web/mail-title.png' "
            + "alt='' usemap='#title' /><map id='title' name='title'><area shape='rect' coords='0,0,257,77' "
            + "href='http://" + serviceDomain
            + "' target='_blank' alt='kaicoin' /></map></td></tr></thead><tbody>"
            + "<tr style='background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/"
            + "web/logo-symbol.png) no-repeat 100% 50%;'><td style='padding:56px 0 84px;color:#414141;'> "
            + "<strong style='font-size:40px;'>" + fullName
            + "님 안녕하세요!</strong><p style='margin:0;padding:29px 0 "
            + "161px;font-size:20px;line-height:1.5;'>블록체인과 문화의 결합, 카이코인에 오신 것을 환영합니다.<br />"
            + "아래의 버튼을 클릭하시면 회원가입이 완료됩니다.</p> <a href='" + confirmedUrl
            + "'><img src='http://res.cloudinary.com/dhjjs2uz3/image/"
            + "upload/v1503815381/KaicoinICO/web/btn_FILL_basic_active.png' alt='메인 확인' /></a></td></tr><tr><td>"
            + " <img src='http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503814966/KaicoinICO/web/mail-footer"
            + ".png' alt='' usemap='#footer' /><map id='footer' name='footer'><area shape='rect' coords='0,32,224"
            + ",53' href='mailto:support@greenstage.co.kr' target='_blank' alt='support@greenstage.co.kr' /><area"
            + " shape='rect' coords='475,97,517,140' href='mailto:support@greenstage.co.kr' target='_blank' "
            + "alt='Mail' /><area shape='rect' coords='539,97,581,140' href='https://www.facebook.com/kaicoin.gs/' "
            + "target='_blank' alt='Facebook' /></map></td></tr></tbody></table></div></div>";
    */
    String content = 
    		"<body><div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;background:#fff;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
    		"		<tbody>\r\n" + 
    		"		</tbody><thead>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-eng.png\" alt=\"\" usemap=\"#title\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"title\" name=\"title\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://"+serviceDomain+"\" target=\"_blank\" alt=\"kai coin\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</thead>\r\n" + 
    		"		<tbody>\r\n" + 
    		"			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
    		"				<td style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">Dear "+fullName+"!</strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 161px;font-size:20px;line-height:1.5;\">We welcome you to KAICOIN, which connects Blockchain and Culture. Please click the button below to complete your membership application.</p>\r\n" + 
    		"					<a href=\""+confirmedUrl+"\"><img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504576313/KaicoinICO/web/button_email_confirm.png\" alt=\"E-mail Confirmation\"></a>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699112/KaicoinICO/web/mail-footer-eng.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"356,32,517,54\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</tbody>\r\n" + 
    		"		</table>\r\n" + 
    		"	</div>\r\n" + 
    		"</div></body>";
    

    	
    
    
    
    // send mail
    boolean isSend = mailSenderUtil.sendMail(signupDto.getEmail(), "Membership Confirmation", content);

    if (!isSend)
      throw new LogicException(LogicErrorList.MailModuleException);

    try {

    } catch (Exception e) {
      // TODO: handle exception
    }
    // save
    Account account = new Account();
    BeanUtil.copyProperties(signupDto, account);
    account.setState("R");
    account.setGencode(gencode);

    // generate kaicoin address
    String kaicoinAddr = gschainUtil.getNetAddress(signupDto.getEmail());

    if (kaicoinAddr == null)
      throw new LogicException(LogicErrorList.KaicoinModuleException);

    account.setKaicoinAddr(kaicoinAddr);
    accountRepo.save(account);
    BeanUtil.copyProperties(account, signupDto);
    signupDto.setPassword(null);

    return signupDto;
  }

  /**
   * @작성일 : 2017. 8. 21.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public SigninDto signin(SigninDto signinDto, HttpServletResponse res) {

    // 인증
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(signinDto.getEmail(), signinDto.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 토큰생성
    Account account = accountRepo.findByEmail(signinDto.getEmail());

    // 쿠키에서 토큰을 받지 못하는 상황으로 임시적으로 토큰값을 조절해서 Response Body에 찍는다
    // 이에 간결하고 필요한 부분만 추려서 보낸다
    String xAuthToken = tokenUtil.createToken(account.getEmail(), account.getPassword())
        .replace(account.getEmail() + "=", "");

    // 쿠키생성 << 테스트를 위해 일단 찍어 보냄
    Cookie cookie = new Cookie(this.tokenHeader, xAuthToken);
    res.addCookie(cookie);

    BeanUtil.copyProperties(account, signinDto);

    signinDto.setAuthToken(xAuthToken);
    signinDto.setPassword(null);

    return signinDto;
  }

  /**
   * @작성일 : 2017. 8. 22.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public String validateEmail(String email) {
    // 존재하고 있는 이메일
    if (accountRepo.findByEmail(email) != null)
      throw new LogicException(LogicErrorList.DuplicateEntity_Account);

    if (!emailValidMailboxLayerUtil.checkValid(email))
      throw new LogicException(LogicErrorList.DoesNotExist_Email);

    return email;
  }

  /**
   * @작성일 : 2017. 8. 22.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public boolean confirmMail(String gencode, String email) {

    if (!accountRepo.existsByEmailAndGencode(email, gencode))
      throw new LogicException(LogicErrorList.NotMatched);

    Account account = accountRepo.findByEmail(email);
    account.setGencode(EncryptionSha256Util.getGenCode());
    account.setState("E");
    accountRepo.save(account);
    return true;
  }



  /**
   * @작성일 : 2017. 8. 24.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public String sendSMSCode(String mobile) {
	    String requestId = smsNexmoUtil.sendSMSCode(mobile);
    if (requestId == null)
      throw new LogicException(LogicErrorList.SMSModuleException);
    return requestId;
  }

  /**
   * @작성일 : 2017. 8. 24.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public boolean checkSMSCode(CheckSMSCodeDto checkSMSCodeDto) {
	  

    Account account = accountRepo.findByEmail(checkSMSCodeDto.getEmail());
    if (account == null)
      throw new LogicException(LogicErrorList.DoesNotExist_Email);

    int status =
        smsNexmoUtil.checkSMSCode(checkSMSCodeDto.getRequestId(), checkSMSCodeDto.getSmsCode());
    if (status == 0) {

      // 전화번호 저장
      account.setMobile(checkSMSCodeDto.getMobile());
  	System.out.println(account.getMobile());

      account.setState("M");
      accountRepo.save(account);
      return true;
      
    } else {
      return false;
    }
  }

  /**
   * @작성일 : 2017. 8. 28.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public boolean sendSignupMail(String email) {
    Account account = accountRepo.findByEmail(email);
    if (account == null)
      throw new LogicException(LogicErrorList.DoesNotExist_Account);

    String fullName = account.getName2() + account.getName1();
    String gencode = EncryptionSha256Util.getGenCode();
    String confirmedUrl = "http://" + serviceDomain + "/mailConfirm/" + gencode + "/" + email + "/";

    String content =
    		"<body><div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    	    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    	    		"		<table style=\"width:580px;margin:0 auto;background:#fff;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
    	    		"		<tbody>\r\n" + 
    	    		"		</tbody><thead>\r\n" + 
    	    		"			<tr>\r\n" + 
    	    		"				<td>\r\n" + 
    	    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-eng.png\" alt=\"\" usemap=\"#title\">\r\n" + 
    	    		"\r\n" + 
    	    		"					<map id=\"title\" name=\"title\">\r\n" + 
    	    		"						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://"+serviceDomain+"\" target=\"_blank\" alt=\"kai coin\">\r\n" + 
    	    		"					</map>\r\n" + 
    	    		"				</td>\r\n" + 
    	    		"			</tr>\r\n" + 
    	    		"		</thead>\r\n" + 
    	    		"		<tbody>\r\n" + 
    	    		"			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
    	    		"				<td style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    	    		"					<strong style=\"font-size:40px;\">Dear "+fullName+"!</strong>\r\n" + 
    	    		"					<p style=\"margin:0;padding:29px 0 161px;font-size:20px;line-height:1.5;\">We welcome you to KAICOIN, which connects Blockchain and Culture. Please click the button below to complete your membership application.</p>\r\n" + 
    	    		"					<a href=\""+confirmedUrl+"\"><img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504576313/KaicoinICO/web/button_email_confirm.png\" alt=\"E-mail Confirmation\"></a>\r\n" + 
    	    		"				</td>\r\n" + 
    	    		"			</tr>\r\n" + 
    	    		"			<tr>\r\n" + 
    	    		"				<td>\r\n" + 
    	    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699112/KaicoinICO/web/mail-footer-eng.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    	    		"\r\n" + 
    	    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    	    		"						<area shape=\"rect\" coords=\"356,32,517,54\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    	    		"					</map>\r\n" + 
    	    		"				</td>\r\n" + 
    	    		"			</tr>\r\n" + 
    	    		"		</tbody>\r\n" + 
    	    		"		</table>\r\n" + 
    	    		"	</div>\r\n" + 
    	    		"</div></body>";
    
    
    

    // send mail
    boolean isSend = mailSenderUtil.sendMail(email, "Membership Confirmation", content);

    if (!isSend)
      throw new LogicException(LogicErrorList.MailModuleException);

    account.setGencode(gencode);
    accountRepo.save(account);


    return isSend;
  }

  
  public boolean sendSignupMail_chn(String email) {
	    Account account = accountRepo.findByEmail(email);
	    if (account == null)
	      throw new LogicException(LogicErrorList.DoesNotExist_Account);

	    String fullName = account.getName2() + account.getName1();
	    String gencode = EncryptionSha256Util.getGenCode();
	    String confirmedUrl = "http://" + serviceDomain + "/mailConfirm/" + gencode + "/" + email + "/";

	    String content =
	        "<body><div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
	        "	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
	        "		<table style=\"width:580px;margin:0 auto;background:#fff;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
	        "		<tbody>\r\n" + 
	        "		</tbody><thead>\r\n" + 
	        "			<tr>\r\n" + 
	        "				<td>\r\n" + 
	        "					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-chn.png\" alt=\"\" usemap=\"#title\">\r\n" + 
	        "\r\n" + 
	        "					<map id=\"title\" name=\"title\">\r\n" + 
	        "						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://www.kaicoin.io\" target=\"_blank\" alt=\"kai coin\">\r\n" + 
	        "					</map>\r\n" + 
	        "				</td>\r\n" + 
	        "			</tr>\r\n" + 
	        "		</thead>\r\n" + 
	        "		<tbody>\r\n" + 
	        "			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
	        "				<td style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
	        "					<strong style=\"font-size:40px;\">尊敬的Chio先生/女士，您好！</strong>\r\n" + 
	        "					<p style=\"margin:0;padding:29px 0 161px;font-size:20px;line-height:1.5;\">欢迎您访问区块链与文化的结合，KAICOIN。点击下方按钮即可完成会员注册。 </p>\r\n" + 
	        "					<a href=\"\"><img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504577961/KaicoinICO/web/button_emailconfirmation_chn.png\" alt=\"确认邮件\"></a>\r\n" + 
	        "				</td>\r\n" + 
	        "			</tr>\r\n" + 
	        "			<tr>\r\n" + 
	        "				<td>\r\n" + 
	        "					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-footer-chn.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
	        "\r\n" + 
	        "					<map id=\"footer\" name=\"footer\">\r\n" + 
	        "						<area shape=\"rect\" coords=\"36,30,259,50\" href=\"mailto:support@greenstage.co.kr\" target=\"_blank\" alt=\"support@greenstage.co.kr\">\r\n" + 
	        "						<area shape=\"rect\" coords=\"475,97,517,140\" href=\"mailto:support@greenstage.co.kr\" target=\"_blank\" alt=\"Mail\">\r\n" + 
	        "						<area shape=\"rect\" coords=\"539,97,581,140\" href=\"https://www.facebook.com/kaicoin.gs/\" target=\"_blank\" alt=\"Facebook\">\r\n" + 
	        "					</map>\r\n" + 
	        "				</td>\r\n" + 
	        "			</tr>\r\n" + 
	        "		</tbody>\r\n" + 
	        "		</table>\r\n" + 
	        "	</div>\r\n" + 
	        "</div></body>";
	    
	    
	    

	    // send mail
	    boolean isSend = mailSenderUtil.sendMail(email, "[카이코인] 회원가입 확인", content);

	    if (!isSend)
	      throw new LogicException(LogicErrorList.MailModuleException);

	    account.setGencode(gencode);
	    accountRepo.save(account);


	    return isSend;
	  }
}
