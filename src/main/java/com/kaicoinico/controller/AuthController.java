/**
 * 
 */
package com.kaicoinico.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kaicoinico.common.EncryptionSha256Util;
import com.kaicoinico.dto.AuthDto.CheckSMSCodeDto;
import com.kaicoinico.dto.AuthDto.EncryptionDto;
import com.kaicoinico.dto.AuthDto.SigninDto;
import com.kaicoinico.dto.AuthDto.SignupDto;
import com.kaicoinico.service.AuthService;

/**
 * @Project : kaicoin-ico
 * @FileName : AuthController.java
 * @Date : 2017. 8. 17.
 * @작성자 : 조성훈
 * @설명 :
 **/

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired AuthService authService;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public SignupDto signup(@RequestBody SignupDto signupDto) {
    return authService.signup(signupDto);
  }

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public SigninDto signin(@RequestBody SigninDto signinDto, HttpServletResponse res) {
    return authService.signin(signinDto, res);
  }

  @RequestMapping(value = "/encryptionSha256", method = RequestMethod.POST)
  public EncryptionDto encryptionSha256(@RequestBody EncryptionDto encryptionDto) {
    String encStr = EncryptionSha256Util.getEncSHA256(encryptionDto.getBeforeEncryt());
    encryptionDto.setAfterEncryt(encStr);
    return encryptionDto;
  }

  @RequestMapping(value = "/validateEmail/{email}", method = RequestMethod.GET)
  public Map<String, String> validateEmail(@PathVariable String email) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("email", authService.validateEmail(email));
    return map;
  }

  @RequestMapping(value = "/sendSMSCode/{mobile}", method = RequestMethod.GET)
  public Map<String, String> sendSMSCode(@PathVariable String mobile) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("requestId", authService.sendSMSCode(mobile));
    return map;
  }

  @RequestMapping(value = "/checkSMSCode", method = RequestMethod.POST)
  public Map<String, Boolean> checkSMSCode(@RequestBody CheckSMSCodeDto checkSMSCodeDto) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isCheck", authService.checkSMSCode(checkSMSCodeDto));
    return map;
  }

  @RequestMapping(value = "/sendSignupMail/{email}", method = RequestMethod.GET)
  public Map<String, Boolean> sendSignupMail(@PathVariable String email) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isEmail", authService.sendSignupMail(email));
    return map;
  }

  @RequestMapping(value = "/confirmMail/{gencode}/{email}", method = RequestMethod.GET)
  public Map<String, Boolean> confirmedMail(@PathVariable String gencode,
      @PathVariable String email) {
    boolean isConfirm = authService.confirmMail(gencode, email);
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isConfirm", isConfirm);
    return map;
  }

}
