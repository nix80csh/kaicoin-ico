/**
 * 
 */
package com.kaicoinico.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.kaicoinico.dto.TxDto.KaiTxDto;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @Project : kaicoin-ico
 * @FileName : GschainUtil.java
 * @Date : 2017. 8. 24.
 * @작성자 : 조성훈
 * @설명 :
 **/
@Component
public class GSChainUtil {
  @Value("${blockchain.gschain.url}") private String url;
  @Value("${blockchain.gschain.username}") private String username;
  @Value("${blockchain.gschain.password}") private String password;

  /**
   * @작성일 : 2017. 8. 24.
   * @설명 :
   * 
   *     <pre></pre>
   **/
  public String getNetAddress(String email) {
    HttpResponse<JsonNode> response;
    try {
      response =
          Unirest.post(url).basicAuth(username, password).header("accept", "application/json")
              .body("{\"method\":\"getnewaddress\", \"id\":\"" + email + "\"}").asJson();
      String address = (String) response.getBody().getObject().get("result");
      System.out.println(address);
      return address;
    } catch (UnirestException e) {
      return null;
    }
  }
  
  
  
  public String getListTx(String email) {
	  return "";
	  
  }
  
  public List<KaiTxDto> getListAddressTx(String address, int cnt) {
	  HttpResponse<JsonNode> res;
      try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          res = Unirest
                  .post(url).basicAuth(username, password)
                  .header("accept", "application/json")
                  .body("{\"method\":\"listaddresstransactions\", \"params\":[\"" + address + "\","  + cnt +"]}")
                  .asJson();
          
          List<KaiTxDto> resultSet = new ArrayList<KaiTxDto>();
          JSONArray resToList = (JSONArray) res.getBody().getObject().get("result");
          System.out.println(resToList.length());
          for(int i = 0; i < resToList.length(); i++) {
        	  KaiTxDto tsSet = new KaiTxDto();
              long time = (int) resToList.getJSONObject(i).get("time");
              tsSet.setAmount((double) ((JSONObject)(resToList.getJSONObject(i).get("balance"))).get("amount"));
              tsSet.setTime(sdf.format((time)*1000));
              resultSet.add(tsSet);
          }
          return Lists.reverse(resultSet);
          
     } catch (UnirestException e) {
          e.printStackTrace();
          return null;
      }
  }
  public String getAddressBalances(String address) {
	  HttpResponse<JsonNode> res;
	  try {
		res = Unirest
		          .post(url).basicAuth(username, password)
		          .header("accept", "application/json")
		          .body("{\"method\":\"getaddressbalances\", \"params\":[\"" + address + "\"]}")
		          .asJson();
		JSONArray resToList = (JSONArray) res.getBody().getObject().get("result");
		double ans = (double) resToList.getJSONObject(0).get("qty");
		System.out.println(ans);
		DecimalFormat df = new DecimalFormat("#,##0.00000000");
		System.out.println(df.format(ans));	
		String result = df.format(ans);
		return result;
	} catch (UnirestException e) {
		e.printStackTrace();
		return e.getMessage();
	}
  }
}
