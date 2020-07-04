/**
 * 
 */
package com.kaicoinico.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthTransaction;

import com.kaicoinico.common.QRCord;
import com.kaicoinico.dto.MypageDto.CheckSMSCodeDto;
import com.kaicoinico.dto.MypageDto.RegistICODto;
import com.kaicoinico.dto.MypageDto.ResetPasswordDto;
import com.kaicoinico.dto.TxDto.KaiTxDto;
import com.kaicoinico.service.MypageService;

/**
 * @Project : kaicoin-ico
 * @FileName : MypageController.java
 * @Date : 2017. 8. 25.
 * @작성자 : 조성훈
 * @설명 :
 **/
@RestController
@RequestMapping("/mypage")
public class MypageController {
	

	
	
  @Autowired MypageService mypageService;

  @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
  public Map<String, Boolean> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isReset", mypageService.resetPassword(resetPasswordDto));
    return map;
  }

  @RequestMapping(value = "/sendPasswordResetMail/{email}", method = RequestMethod.GET)
  public Map<String, Boolean> sendPasswordResetMail(@PathVariable String email) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isSend", mypageService.sendPasswordResetMail(email));
    return map;
  }

  @RequestMapping(value = "/validateEmailForPasswordReset/{email}", method = RequestMethod.GET)
  public Map<String, String> validateEmailForPasswordReset(@PathVariable String email) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("state", mypageService.validateEmailForPasswordReset(email));
    return map;
  }


  @RequestMapping(value = "/checkPasswordResetMail/{email}/{gencode}", method = RequestMethod.GET)
  public Map<String, Boolean> checkPasswordResetMail(@PathVariable String email,
      @PathVariable String gencode) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isCheck", mypageService.checkPasswordResetMail(email, gencode));
    return map;
  }

  @RequestMapping(value = "/sendSMSCode/{email}", method = RequestMethod.GET)
  public Map<String, String> sendSMSCode(@PathVariable String email) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("requestId", mypageService.sendSMSCode(email));
    return map;
  }

  @RequestMapping(value = "/checkSMSCode", method = RequestMethod.POST)
  public Map<String, Boolean> checkSMSCode(@RequestBody CheckSMSCodeDto checkSMSCodeDto) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("isCheck", mypageService.checkSMSCode(checkSMSCodeDto));
    return map;
  }

  @Secured("IS_AUTHENTICATED_FULLY")
  @RequestMapping(value = "/registICO", method = RequestMethod.POST)
  public Map<String, String> registICO(@RequestBody RegistICODto registICO) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("ethereumAddr", mypageService.registICO(registICO));
    return map;
  }

  @Secured("IS_AUTHENTICATED_FULLY")
  @RequestMapping(value = "/searchByTxHash/{txHash}", method = RequestMethod.GET)
  public EthTransaction searchByTxHash(@PathVariable String txHash) {
    EthTransaction ethTransaction = mypageService.searchByTxHash(txHash);
    return ethTransaction;
  }

  @RequestMapping(value="/ethGetBalance", method=RequestMethod.GET)
  public String ethGetBalance() throws Exception {
	  String eth = mypageService.ethGetBalance();
	  return eth;
	  
  }
  
  @RequestMapping(value="/getListTx", method=RequestMethod.GET)
  public int getListTx() throws Exception{
	  int txCnt = mypageService.getListTx();
	  return txCnt;
  }
  
  
  /*@RequestMapping(value="/getListAddressTx/{address}/{cnt}", method=RequestMethod.GET)
  public List<KaiTxDto> getListAddressTx(@PathVariable String address, @PathVariable int cnt) {
	  List<KaiTxDto> result = mypageService.getListAddressTx(address, cnt);
	  return result;
  }*/
  
  @RequestMapping(value="/getListAddressTx", method=RequestMethod.POST)
  public List<KaiTxDto> getListAddressTx(
		  @RequestParam ("address") String address,
		  @RequestParam ("cnt") int cnt) {
	  List<KaiTxDto> result = mypageService.getListAddressTx(address, cnt);
	  return result;
  }
  
  @RequestMapping(value="/getAddressBalances", method=RequestMethod.POST)
  public String getAddressBalances(
		  @RequestParam ("address") String address){
	  return mypageService.getAddressBalances(address);
  }
  
  //test
  @RequestMapping(value = "/getQRCord", method = RequestMethod.GET)
  public Map getQRCorde(Object model) throws IOException {
	  QRCord qrCord = new QRCord();
	  
	  return qrCord.uploadToCloudinary();
  }
}
