/**
 * 
 */
package com.kaicoinico.dto;

import lombok.Data;

/**
 * @Project : kaicoin-ico
 * @FileName : MypageDto.java
 * @Date : 2017. 8. 26.
 * @작성자 : 조성훈
 * @설명 :
 **/

public class MypageDto {
  @Data
  public static class ResetPasswordDto {
    private String email;
    private String password;
  }

  @Data
  public static class CheckSMSCodeDto {
    private String requestId;
    private String smsCode;
    private String email;
  }

  @Data
  public static class RegistICODto {
    private int idfAccount;
    private String ethereumAddr;
  }
  
  @Data
  public static class ResultTxDto{
	  private String blockHash;
	  private String contractAddress;
	  private String transactionIndex;
	  private String confirmations;
	  private String nonce;
	  private String timeStamp;
	  private String input;
	  private String gasUsed;
	  private String isError;
	  private String blockNumber;
	  private String gas;
	  private String cumulativeGasUsed;
	  private String from;
	  private String to;
	  private String value;
	  private String hash;
	  private String gasPrice;
	  
  }

}
