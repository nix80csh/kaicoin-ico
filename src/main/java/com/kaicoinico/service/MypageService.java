/**
 * 
 */
package com.kaicoinico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthTransaction;

import com.kaicoinico.common.EmailValidMailboxLayerUtil;
import com.kaicoinico.common.EncryptionSha256Util;
import com.kaicoinico.common.EtherUtil;
import com.kaicoinico.common.GSChainUtil;
import com.kaicoinico.common.MailSenderUtil;
import com.kaicoinico.common.SMSNexmoUtil;
import com.kaicoinico.common.TokenUtil;
import com.kaicoinico.dto.MypageDto.CheckSMSCodeDto;
import com.kaicoinico.dto.MypageDto.RegistICODto;
import com.kaicoinico.dto.MypageDto.ResetPasswordDto;
import com.kaicoinico.dto.TxDto.KaiTxDto;
import com.kaicoinico.entity.Account;
import com.kaicoinico.exception.LogicErrorList;
import com.kaicoinico.exception.LogicException;
import com.kaicoinico.repository.AccountRepo;

/**
 * @Project : kaicoin-ico
 * @FileName : MypageService.java
 * @Date : 2017. 8. 25.
 * @작성자 : 조성훈
 * @설명 :
 **/
@Service
@Transactional
public class MypageService {
  @Autowired AccountRepo accountRepo;
  @Autowired EmailValidMailboxLayerUtil emailValidMailboxLayerUtil;
  @Autowired AuthenticationManager authenticationManager;
  @Autowired TokenUtil tokenUtil;
  @Autowired MailSenderUtil mailSenderUtil;
  @Autowired SMSNexmoUtil smsNexmoUtil;
  @Autowired GSChainUtil gschainUtil;
  @Autowired EtherUtil etherUtil;
  @Value("${service.domain}") private String serviceDomain;

  /**
   * @작성일 : 2017. 8. 25.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public Boolean sendPasswordResetMail(String email) {
    // DB에서 존재하지 않는 이메일 체크
    if (accountRepo.findByEmail(email) == null)
      throw new LogicException(LogicErrorList.DoesNotExist_Email);

    Account account = accountRepo.findByEmail(email);
    String gencode = EncryptionSha256Util.getGenCode();
    String fullName = account.getName2() + account.getName1();
    String confirmedUrl =
        "http://" + serviceDomain + "/resetPassword/" + account.getEmail() + "/" + gencode;

 /*   String content =
        "<div style='margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;'><div style="
            + "'display:inline-block;padding:60px;background:#fff;'><table style='width:580px;margin:65px"
            + " auto 47px;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,"
            + "Sans-serif,'Apple SD Gothic Neo',Sans-serif;''><tbody><thead><tr><td> <img src='http://res."
            + "cloudinary.com/dhjjs2uz3/image/upload/v1503814966/KaicoinICO/web/mail-title.png' alt='' usemap"
            + "='#title' /><map id='title' name='title'><area shape='rect' coords='0,0,257,77' href='http://"
            + "" + serviceDomain
            + "' target='_blank' alt='kaicoin' /></map></td></tr></thead><tbody><tr style="
            + "'background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo"
            + "-symbol.png) no-repeat 100% 50%;'><td colspan='2' style='padding:56px 0 84px;color:#414141;'> "
            + "<strong style='font-size:40px;'>" + fullName
            + "님 안녕하세요!</strong><p style='margin:0;padding:29px 0 161px;font"
            + "-size:20px;line-height:1.5;'>비밀번호 재설정을 위해 아래의 버튼을 클릭하세요.</p> <a href='"
            + confirmedUrl + "'><img src='http://res"
            + ".cloudinary.com/dhjjs2uz3/image/upload/v1503815381/KaicoinICO/web/btn_FILL_basic_active-1.png' "
            + "alt='비밀번호 재설정' /></a></td></tr><tr><td> <img src='http://res.cloudinary.com/dhjjs2uz3/image/upload"
            + "/v1503814966/KaicoinICO/web/mail-footer.png' alt='' usemap='#footer' /><map id='footer' name='footer'>"
            + "<area shape='rect' coords='0,32,224,53' href='mailto:support@kaicoin.io' target='_blank' alt='"
            + "support@kaicoin.io' /><area shape='rect' coords='475,97,517,140' href='mailto:support@greenstage"
            + ".co.kr' target='_blank' alt='Mail' /><area shape='rect' coords='539,97,581,140' href='https://www.facebo"
            + "ok.com/kaicoin.gs/' target='_blank' alt='Facebook' /></map></td></tr></tbody></table></div></div>";*/

    
    String content_chn ="<body><div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
    		"		<tbody>\r\n" + 
    		"		</tbody><thead>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-chn.png\" alt=\"\" usemap=\"#title\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"title\" name=\"title\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://"+serviceDomain+"\" target=\"_blank\" alt=\"kai coin\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</thead>\r\n" + 
    		"		<tbody>\r\n" + 
    		"			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
    		"				<td colspan=\"2\" style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">尊敬的 "+fullName+"先生/女士，您好！</strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 161px;font-size:20px;line-height:1.5;\">为了重新设置密码,请点击下方按钮。</p>\r\n" + 
    		"					<a href=\""+confirmedUrl+"\"><img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504578175/KaicoinICO/web/button_reset_chn.png\" alt=\"密码重设\"></a>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699143/KaicoinICO/web/mail-footer-chn.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"36,30,201,50\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</tbody>\r\n" + 
    		"		</table>\r\n" + 
    		"	</div>\r\n" + 
    		"</div></body>";
    String content_jpn ="<body><div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
    		"		<tbody>\r\n" + 
    		"		</tbody><thead>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-jpn.png\" alt=\"\" usemap=\"#title\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"title\" name=\"title\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://"+serviceDomain+"\" target=\"_blank\" alt=\"kai coin\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</thead>\r\n" + 
    		"		<tbody>\r\n" + 
    		"			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
    		"				<td colspan=\"2\" style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">"+fullName+"様こんにちは！</strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 161px;font-size:20px;line-height:1.5;\">パスワードをリセットするために、下のボタンをクリックしてください。</p>\r\n" + 
    		"					<a href=\""+confirmedUrl+"\"><img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504578751/KaicoinICO/web/button_reset_jap.png\" alt=\"パスワードリセット\"></a>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699115/KaicoinICO/web/mail-footer-jpn.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,30,169,50\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</tbody>\r\n" + 
    		"		</table>\r\n" + 
    		"	</div>\r\n" + 
    		"</div></body>";
    String content_eng ="<body><div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
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
    		"				<td colspan=\"2\" style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">Dear "+fullName+"! </strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 161px;font-size:20px;line-height:1.5;\">To reset your password, please click the button below.</p>\r\n" + 
    		"					<a href=\""+confirmedUrl+"\"><img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504576313/KaicoinICO/web/button_reset.png\" alt=\"Reset Password\"></a>\r\n" + 
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
    
    boolean isSend;
    // send mail

//    boolean isSend = mailSenderUtil.sendMail(email, "[카이코인] 비밀번호 재설정", content);
    
    
	  String nation = account.getMobile().substring(0, 3);
	  if(nation.equals("086")) {
		  //중국
		   isSend = mailSenderUtil.sendMail(account.getEmail(), "密码重设  ", content_chn);
	  }else if(nation.equals("081")) {
		  //일본
		   isSend = mailSenderUtil.sendMail(account.getEmail(), "パスワードのリセット ", content_jpn);
	  }else {
		  //영어
		   isSend = mailSenderUtil.sendMail(account.getEmail(), "Password Reset", content_eng);
	  }
    
    account.setGencode(gencode);
    accountRepo.save(account);
    return isSend;
  }

  /**
   * @작성일 : 2017. 8. 25.
   * @설명 :
   * 
   *     <pre>
   *     비밀번호 재설정메일 인증
   *     </pre>
   **/
  public Boolean checkPasswordResetMail(String email, String gencode) {
    if (accountRepo.existsByEmailAndGencode(email, gencode)) {
      Account account = accountRepo.findByEmail(email);
      account.setGencode(EncryptionSha256Util.getGenCode());
      accountRepo.save(account);
      return true;
    } else {
      return false;
    }
  }

  /**
   * @작성일 : 2017. 8. 26.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public Boolean resetPassword(ResetPasswordDto resetPasswordDto) {
    Account account = accountRepo.findByEmail(resetPasswordDto.getEmail());
    if (account == null)
      throw new LogicException(LogicErrorList.DoesNotExist_Email);
    account.setPassword(resetPasswordDto.getPassword());
    accountRepo.save(account);
    return true;
  }

  /**
   * @작성일 : 2017. 8. 24.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public String sendSMSCode(String email) {
    Account account = accountRepo.findByEmail(email);
    String requestId = smsNexmoUtil.sendSMSCode(account.getMobile());
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
      return true;
    } else {
      return false;
    }
  }

  /**
   * @작성일 : 2017. 8. 26.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public String registICO(RegistICODto registICODto) {
    if (!accountRepo.exists(registICODto.getIdfAccount()))
      throw new LogicException(LogicErrorList.DoesNotExist_Account);
    if (!etherUtil.isAddress(registICODto.getEthereumAddr()))
      throw new LogicException(LogicErrorList.DoesNotExist_EtherAddress);
    Account account = accountRepo.findOne(registICODto.getIdfAccount());
    account.setEthereumAddr(registICODto.getEthereumAddr());
    accountRepo.save(account);

    String fullName = account.getName2() + account.getName1();
   /* String content =
        "<div style='margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;'>"
            + "<div style='display:inline-block;padding:60px;background:#fff;'><table style='width:580px;"
            + "border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,"
            + "'Apple SD Gothic Neo',Sans-serif;'><tbody><thead><tr><td> <img src='http://res.cloudinary.com"
            + "/dhjjs2uz3/image/upload/v1503814966/KaicoinICO/web/mail-title.png' alt='' usemap='#title' />"
            + "<map id='title' name='title'><area shape='rect' coords='0,0,257,77' href='http://"
            + serviceDomain + "' "
            + "target='_blank' alt='kaicoin' /></map></td></tr></thead><tbody><tr style='background:url(http://"
            + "res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat "
            + "100% 50%;'><td colspan='2' style='padding:56px 0 84px;color:#414141;'> <strong style='font-size:40px;'>"
            + fullName
            + "님 안녕하세요!</strong><p style='margin:0;padding:29px 0 37px;font-size:20px;line-height:1.5;'>"
            + "카이코인 ICO 에 신청해주셔서 감사드립니다.<br /> 기재하신 이더리움 주소에서 이더리움을 보내주시면 참여가 완료됩니다.</p><div style="
            + "'border-top:1px dashed #c1c3c3;border-bottom:1px dashed #c1c3c3;'><dl style='margin:39px 0 0;'><"
            + "dt style='margin:0;padding:0;font-size:20px;color:#414141;'>기재하신 이더리움 주소 :</dt><dd style='margin:"
            + "0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;'>"
            + account.getEthereumAddr()
            + "</dd></dl><dl style='margin:47px 0 35px;'><dt style='margin:0;padding:0;font-size:20px;color"
            + ":#414141;'>카이코인 구매 주소 :</dt><dd style='margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color"
            + ":#414141;'>" + "0x23D92146A8c90609A8EA9649b9ee471e75d9D0B4"
            + "</dd></dl></div> <span style='display:inline-block"
            + ";margin:13px 0 0;font-size:16px;color:#ec9c00;'>※ 코인 구매시 최소금액 150KAI (0.1ETH)</span></td></tr><tr><td>"
            + " <img src='http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503814966/KaicoinICO/web/mail-footer.png'"
            + " alt='' usemap='#footer' /><map id='footer' name='footer'><area shape='rect' coords='0,32,224,53' "
            + "href='mailto:support@kaicoin.io' target='_blank' alt='support@kaicoin.io' /><area shape="
            + "'rect' coords='475,97,517,140' href='mailto:support@kaicoin.io' target='_blank' alt='Mail' />"
            + "<area shape='rect' coords='539,97,581,140' href='https://www.facebook.com/kaicoin.gs/' target='_blank'"
            + " alt='Facebook' /></map></td></tr></tbody></table></div></div>";
    */
    
    String content_chn = "<div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
    		"		<tbody>\r\n" + 
    		"		</tbody><thead>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-chn.png\" alt=\"\" usemap=\"#title\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"title\" name=\"title\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://"+serviceDomain+" target=\"_blank\" alt=\"kai coin\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</thead>\r\n" + 
    		"		<tbody>\r\n" + 
    		"			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
    		"				<td colspan=\"2\" style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">"+ fullName + "先生/女士！</strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 37px;font-size:20px;line-height:1.5;\">感谢您申请KAICOIN ICO。<br>\r\n" + 
    		"					由所记载的ETH地址发送ETH即可完成参与。</p>\r\n" + 
    		"					<div style=\"border-top:1px dashed #c1c3c3;border-bottom:1px dashed #c1c3c3;\">\r\n" + 
    		"						<dl style=\"margin:39px 0 0;\">\r\n" + 
    		"							<dt style=\"margin:0;padding:0;font-size:20px;color:#414141;\">所记载的ETH地址 : </dt>\r\n" + 
    		"							<dd style=\"margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;\">"+ account.getEthereumAddr() +"</dd>\r\n" + 
    		"						</dl>\r\n" + 
    		"						<dl style=\"margin:47px 0 35px;\">\r\n" + 
    		"							<dt style=\"margin:0;padding:0;font-size:20px;color:#414141;\">KAICOIN购买地址 : </dt>\r\n" + 
    		"							<dd style=\"margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;\">0x23D92146A8c90609A8EA9649b9ee471e75d9D0B4</dd>\r\n" +
    		"						</dl>\r\n" + 
    		"					</div>\r\n" + 
    		"					<span style=\"display:inline-block;margin:13px 0 0;font-size:16px;color:#ec9c00;\">※ 代币购买金额下限150KAI（0.1ETH）</span>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699143/KaicoinICO/web/mail-footer-chn.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"36,30,259,50\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"475,97,517,140\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"Mail\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"539,97,581,140\" href=\"https://www.facebook.com/kaicoin.gs/\" target=\"_blank\" alt=\"Facebook\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</tbody>\r\n" + 
    		"		</table>\r\n" + 
    		"	</div>\r\n" + 
    		"</div>";
    
    String content_jpn = "<div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
    		"		<tbody>\r\n" + 
    		"		</tbody><thead>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504573500/KaicoinICO/web/mail-title-jpn.png\" alt=\"\" usemap=\"#title\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"title\" name=\"title\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,0,257,77\" href=\"http://"+serviceDomain+"\" target=\"_blank\" alt=\"kai coin\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</thead>\r\n" + 
    		"		<tbody>\r\n" + 
    		"			<tr style=\"background:url(http://res.cloudinary.com/dhjjs2uz3/image/upload/v1503462311/KaicoinICO/web/logo-symbol.png) no-repeat 100% 50%;\">\r\n" + 
    		"				<td colspan=\"2\" style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">"+fullName+"様！</strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 37px;font-size:20px;line-height:1.5;\">カイコイン ICO にご申請いただき、ありがとうございます。<br>\r\n" + 
    		"					記載された ETH アドレスまで ETH をお送りいただければ、参加が完了となります</p>\r\n" + 
    		"					<div style=\"border-top:1px dashed #c1c3c3;border-bottom:1px dashed #c1c3c3;\">\r\n" + 
    		"						<dl style=\"margin:39px 0 0;\">\r\n" + 
    		"							<dt style=\"margin:0;padding:0;font-size:20px;color:#414141;\">記載された ETH アドレス : </dt>\r\n" + 
    		"							<dd style=\"margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;\">  "+account.getEthereumAddr()+" </dd>\r\n" + 
    		"						</dl>\r\n" + 
    		"						<dl style=\"margin:47px 0 35px;\">\r\n" + 
    		"							<dt style=\"margin:0;padding:0;font-size:20px;color:#414141;\">カイコイン購入アドレス : </dt>\r\n" + 
    		"							<dd style=\"margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;\">0x23D92146A8c90609A8EA9649b9ee471e75d9D0B4</dd>\r\n" + 
    		"						</dl>\r\n" + 
    		"					</div>\r\n" + 
    		"					<span style=\"display:inline-block;margin:13px 0 0;font-size:16px;color:#ec9c00;\">※ コイン購入時の最低額 150KAI(0.1ETH)</span>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699115/KaicoinICO/web/mail-footer-jpn.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"0,30,223,50\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"475,97,517,140\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"Mail\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"539,97,581,140\" href=\"https://www.facebook.com/kaicoin.gs/\" target=\"_blank\" alt=\"Facebook\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</tbody>\r\n" + 
    		"		</table>\r\n" + 
    		"	</div>\r\n" + 
    		"</div>";
    
    String content_eng = "<div style=\"margin:0 auto;padding:120px 0;background:#f5f5f5;text-align:center;\">\r\n" + 
    		"	<div style=\"display:inline-block;padding:60px;background:#fff;text-align:left;\">\r\n" + 
    		"		<table style=\"width:580px;margin:0 auto;border-collapse:collapse;font-family:'나눔바른고딕','Nanum Barun Gothic','돋움',Dotum,Sans-serif,'Apple SD Gothic Neo',Sans-serif;\">\r\n" + 
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
    		"				<td colspan=\"2\" style=\"padding:56px 0 84px;color:#414141;\">\r\n" + 
    		"					<strong style=\"font-size:40px;\">Dear "+ fullName +"!</strong>\r\n" + 
    		"					<p style=\"margin:0;padding:29px 0 37px;font-size:20px;line-height:1.5;\">Thank you for your application for KAICOIN ICO.<br>\r\n" + 
    		"					Please send your ETH from the ETH address that you entered to complete your application.</p>\r\n" + 
    		"					<div style=\"border-top:1px dashed #c1c3c3;border-bottom:1px dashed #c1c3c3;\">\r\n" + 
    		"						<dl style=\"margin:39px 0 0;\">\r\n" + 
    		"							<dt style=\"margin:0;padding:0;font-size:20px;color:#414141;\">ETH address : </dt>\r\n" + 
    		"							<dd style=\"margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;\"> "+account.getEthereumAddr()+"</dd>\r\n" + 
    		"						</dl>\r\n" + 
    		"						<dl style=\"margin:47px 0 35px;\">\r\n" + 
    		"							<dt style=\"margin:0;padding:0;font-size:20px;color:#414141;\">KAICOIN Purchase Address : </dt>\r\n" + 
    		"							<dd style=\"margin:0;padding:10px 0 0;font-size:20px;font-weight:bold;color:#414141;\">0x23D92146A8c90609A8EA9649b9ee471e75d9D0B4</dd>\r\n" + 
    		"						</dl>\r\n" + 
    		"					</div>\r\n" + 
    		"					<span style=\"display:inline-block;margin:13px 0 0;font-size:16px;color:#ec9c00;\">※ Minimum purchase unit: KAI 150 (ETH 0.1)</span>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"			<tr>\r\n" + 
    		"				<td>\r\n" + 
    		"					<img src=\"http://res.cloudinary.com/dhjjs2uz3/image/upload/v1504699112/KaicoinICO/web/mail-footer-eng.png\" alt=\"\" usemap=\"#footer\">\r\n" + 
    		"\r\n" + 
    		"					<map id=\"footer\" name=\"footer\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"356,32,579,53\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"support@kaicoin.io\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"475,97,517,140\" href=\"mailto:support@kaicoin.io\" target=\"_blank\" alt=\"Mail\">\r\n" + 
    		"						<area shape=\"rect\" coords=\"539,97,581,140\" href=\"https://www.facebook.com/kaicoin.gs/\" target=\"_blank\" alt=\"Facebook\">\r\n" + 
    		"					</map>\r\n" + 
    		"				</td>\r\n" + 
    		"			</tr>\r\n" + 
    		"		</tbody>\r\n" + 
    		"		</table>\r\n" + 
    		"	</div>\r\n" + 
    		"</div>";
    

    
    
    // send mail
    boolean isSend;
    
    Account accountTemp = accountRepo.findByIdfAccount(registICODto.getIdfAccount());
	  String nation = accountTemp.getMobile().substring(0, 3);
	  if(nation.equals("086")) {
		  //중국
		   isSend = mailSenderUtil.sendMail(account.getEmail(), "KAICOIN ICO参与 ", content_chn);
	  }else if(nation.equals("081")) {
		  //일본
		   isSend = mailSenderUtil.sendMail(account.getEmail(), "カイコイン ICO 参加 チェ・ギョンウン様！ ", content_jpn);
	  }else {
		  //영어
		   isSend = mailSenderUtil.sendMail(account.getEmail(), "Participate in a KAICOIN ICO", content_eng);
	  }

    if (!isSend)
      throw new LogicException(LogicErrorList.MailModuleException);
    return account.getEthereumAddr();
  }

  /**
   * @작성일 : 2017. 8. 29.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public String validateEmailForPasswordReset(String email) {
    Account account = accountRepo.findByEmail(email);
    if (account == null)
      throw new LogicException(LogicErrorList.DoesNotExist_Email);

    return account.getState();

  }

  /**
   * @작성일 : 2017. 8. 30.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public EthTransaction searchByTxHash(String txHash) {
    return etherUtil.getTxByHash(txHash);
  }

  
  /**
   * @작성일 : 2017. 9. 5.
   * @설명 :  getBalance for one of the dashBoard
   * 
   *     <pre></pre>
   **/
  
  /*public CompletableFuture<PersonalUnlockAccount> unlockEthCoinbase(String ETH_ADDRESS, String ETH_PW) throws IOException, ExecutionException, Exception{
	  return etherUtil.unlockAccount(ETH_ADDRESS, ETH_PW);
  }*/

  public String ethGetBalance() throws Exception {
	  return etherUtil.ethGetBalance();
  }
 

  public int getListTx() throws Exception{
	  return etherUtil.getListTx();
  }
  
  public List<KaiTxDto> getListAddressTx(String address, int cnt) {
	  return gschainUtil.getListAddressTx(address, cnt);
  }
  
  public String getAddressBalances(String address) {
	  return gschainUtil.getAddressBalances(address);
  }
  
}
