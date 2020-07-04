/**
 * 
 */
package com.kaicoinico.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.core.methods.response.EthTransaction;

import com.kaicoinico.service.MypageService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * @Project : kaicoin-ico
 * @FileName : EtherUtilTest.java
 * @Date : 2017. 8. 30.
 * @작성자 : 조성훈
 * @설명 :
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class EtherUtilTest {
  @Autowired EtherUtil etherUtil;
  @Autowired MypageService mypageService;
  
  @Test
  public void isAddress() {
    @SuppressWarnings("static-access")
    boolean isAddr = etherUtil.isAddress("0x70faa28a6b8d6829a4b1e649d26ec9a2a39ba413");
    assertTrue(isAddr);
  }

  @Test
  public void getTxByHash() {
    EthTransaction ethTransaction =
        etherUtil.getTxByHash("0x382c35291473369c8a33e7cfa4ba88fe837057af20f8f5472438f9d10bfa8ca2");
    assertNotNull(ethTransaction);
  }

/*  @Test
  public void unlockAddress() throws IOException, ExecutionException, Exception {
	CompletableFuture<PersonalUnlockAccount> unlock = etherUtil.unlockAccount("0x3f654d1c10dc1dfb226588c7b58e19f4cc20eddf", "1234");
	assertNotNull(unlock);
  }*/
  
  @Test
  public void getBalance() throws Exception{
	  String bal = mypageService.ethGetBalance();
	  System.out.println(bal);
	  assertNotNull(bal);
  }
  @Test
  public void getListTx() throws Exception{
	  HttpResponse<JsonNode> response = Unirest.post("http://api.etherscan.io/api?module=account&action=txlist&address=0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a&startblock=0&endblock=99999999&sort=asc&apikey=277X5C7MXJ8A9NTF6FXGIXYUN53JQYYNN3").asJson();
	  assertNotNull(response);
	  JSONArray resultList = (JSONArray) response.getBody().getObject().get("result");
	  System.out.println(resultList.length());
	  
  }

  }
