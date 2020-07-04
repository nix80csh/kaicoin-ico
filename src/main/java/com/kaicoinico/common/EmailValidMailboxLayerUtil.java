package com.kaicoinico.common;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component
public class EmailValidMailboxLayerUtil {

  @Value("${utils.mailboxLayer.key}")
  private String emailApi_key;

  public boolean checkValid(String email) {
    HttpResponse<JsonNode> response;
    try {
      response = Unirest.get("http://apilayer.net/api/check?access_key=" + emailApi_key + "&email="
          + email + "&smtp=1&format=1").header("Accept", "application/json").asJson();

      Boolean format_valid = (Boolean) response.getBody().getObject().get("format_valid");
      Boolean mx_found = (Boolean) response.getBody().getObject().get("mx_found");
      Boolean smtp_check = (Boolean) response.getBody().getObject().get("smtp_check");

      if (format_valid == true && mx_found == true && smtp_check == true) {
        return true;
      } else {
        return false;
      }
    } catch (UnirestException | JSONException e) {
      return false;
    }
  }

}
