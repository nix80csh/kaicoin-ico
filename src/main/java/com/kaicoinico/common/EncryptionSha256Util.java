package com.kaicoinico.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;



public class EncryptionSha256Util {

  public static String getEncSHA256(String txt) {
    StringBuffer sbuf = new StringBuffer();

    MessageDigest mDigest;
    try {
      mDigest = MessageDigest.getInstance("SHA-256");
      mDigest.update(txt.getBytes());

      byte[] msgStr = mDigest.digest();

      for (int i = 0; i < msgStr.length; i++) {
        byte tmpStrByte = msgStr[i];
        String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
        sbuf.append(tmpEncTxt);
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return sbuf.toString();
  }

  public static String getGenCode() {
    Random rnd = new Random();
    StringBuffer buf = new StringBuffer();
    // 10자리 영문숫자 조합 임시 비빌번호 생성
    for (int i = 0; i < 10; i++) {
      if (rnd.nextBoolean()) {
        buf.append((char) ((rnd.nextInt(26)) + 65));
      } else {
        buf.append((rnd.nextInt(10)));
      }
    }
    return buf.toString();
  }
}
