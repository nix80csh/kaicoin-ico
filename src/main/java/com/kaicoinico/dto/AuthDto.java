/**
 * 
 */
package com.kaicoinico.dto;

import lombok.Data;

/**
 * @Project : kaicoin-ico
 * @FileName : AuthVO.java
 * @Date : 2017. 8. 21.
 * @작성자 : 조성훈
 * @설명 :
 **/



public class AuthDto {

  @Data
  public static class SignupDto {
    private int idfAccount;
    private String email;
    private String name1;
    private String name2;
    private String password;
    private String kaicoinAddr;
    private String state;
  }

  @Data
  public static class SigninDto {
    private int idfAccount;
    private String email;
    private String password;
    private String authToken;
    private String kaicoinAddr;
    private String ethereumAddr;
    private String mobile;
    private String state;

  }

  @Data
  public static class EncryptionDto {
    private String beforeEncryt;
    private String afterEncryt;
  }

  @Data
  public static class CheckSMSCodeDto {
    private String mobile;
    private String requestId;
    private String smsCode;
    private String email;
  }
}
