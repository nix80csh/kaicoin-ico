package com.kaicoinico.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

  @Value("${kaicoin.token.secret}")
  private String secret;
  @Value("${kaicoin.token.expiration}")
  private Long expiration;


  public String createToken(String userId, String password) {
    /* Expires in one hour */
    long expires = System.currentTimeMillis() + 1000L * expiration;

    StringBuilder tokenBuilder = new StringBuilder();
    tokenBuilder.append(userId);
    tokenBuilder.append("=");
    tokenBuilder.append(expires);
    tokenBuilder.append("=");
    tokenBuilder.append(computeSignature(userId, password, expires));

    return tokenBuilder.toString();
  }

  public String computeSignature(String userId, String password, long expires) {
    StringBuilder signatureBuilder = new StringBuilder();
    signatureBuilder.append(userId);
    signatureBuilder.append("=");
    signatureBuilder.append(expires);
    signatureBuilder.append("=");
    signatureBuilder.append(password);
    signatureBuilder.append("=");
    signatureBuilder.append(secret);

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("No MD5 algorithm available!");
    }

    return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
  }

  public String getUserIdFromToken(String authToken) {
    if (null == authToken) {
      return null;
    }

    String[] parts = authToken.split("=");
    return parts[0];
  }

  public boolean validateToken(String authToken, String userId, String password) {
    String[] parts = authToken.split("=");
    long expires = Long.parseLong(parts[1]);
    String signature = parts[2];

    if (expires < System.currentTimeMillis()) {
      return false;
    }

    return signature.equals(computeSignature(userId, password, expires));
  }
}
