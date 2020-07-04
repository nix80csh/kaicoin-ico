/**
 * 
 */
package com.kaicoinico.common;

import java.io.IOException;
import java.math.BigInteger;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.parity.Parity;
import org.web3j.utils.Numeric;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * @Project : kaicoin-ico
 * @FileName : EtherUtil.java
 * @Date : 2017. 8. 30.
 * @작성자 : 조성훈
 * @설명 :
 **/

@Component
public class EtherUtil {

  private static final Pattern ignoreCaseAddrPattern = Pattern.compile("(?i)^(0x)?[0-9a-f]{40}$");
  private static final Pattern lowerCaseAddrPattern = Pattern.compile("^(0x)?[0-9a-f]{40}$");
  private static final Pattern upperCaseAddrPattern = Pattern.compile("^(0x)?[0-9A-F]{40}$");
//  private String ETH_ADDRESS = "0x23D92146A8c90609A8EA9649b9ee471e75d9D0B4";
//  private String ETH_PW = "";
  
  @Autowired Web3j web3j;
  @Autowired Parity parity;

	@Value("${ethereum.addr}") private String ethereum_addr;
	@Value("${ethereum.apikey}") private String ethereum_apikey;
  
  /**
   * Verify that a hex account string is a valid Ethereum address.
   *
   * @param address given address in HEX
   * @return is this a valid address
   */
  public boolean isAddress(String address) {
    /*
     * check basic address requirements, i.e. is not empty and contains the
     * valid number and type of characters
     */
    if (address.isEmpty() || !ignoreCaseAddrPattern.matcher(address).find()) {
      return false;
    } else if (lowerCaseAddrPattern.matcher(address).find()
        || upperCaseAddrPattern.matcher(address).find()) {
      // if it's all small caps or caps return true
      return true;
    } else {
      // if it is mixed caps it is a checksum address and needs to be validated
      return validateChecksumAddress(address);
    }
  }

  public EthTransaction getTxByHash(String txHash) {
    EthTransaction ethTransaction = null;
    try {
      ethTransaction = web3j.ethGetTransactionByHash(txHash).send();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ethTransaction;
  }

  private static Boolean validateChecksumAddress(String address) {
    String formattedAddress = address.replace("0x", "").toLowerCase();
    String hash = Numeric.toHexStringNoPrefix(Hash.sha3(formattedAddress.getBytes()));
    for (int i = 0; i < 40; i++) {
      if (Character.isLetter(address.charAt(i))) {
        // each uppercase letter should correlate with a first bit of 1 in the
        // hash
        // char with the same index, and each lowercase letter with a 0 bit
        int charInt = Integer.parseInt(Character.toString(hash.charAt(i)), 16);
        if ((Character.isUpperCase(address.charAt(i)) && charInt <= 7)
            || (Character.isLowerCase(address.charAt(i)) && charInt > 7)) {
          return false;
        }
      }
    }
    return true;
  }
  
/*  public CompletableFuture<PersonalUnlockAccount> unlockAccount(String ETH_ADDRESS, String ETH_PW) throws IOException, Exception, ExecutionException {
	  CompletableFuture<PersonalUnlockAccount> personalUnlockAccount = parity.personalUnlockAccount(ETH_ADDRESS, ETH_PW).sendAsync();
		return personalUnlockAccount;
	}
  */
  
	public String ethGetBalance() throws Exception {
		EthGetBalance ethGetBalance = web3j.ethGetBalance(ethereum_addr, DefaultBlockParameterName.LATEST).sendAsync().get();
		BigInteger temp = BigInteger.valueOf((long)(Math.pow(10.0, 18.0)));
		BigInteger[] i = ethGetBalance.getBalance().divideAndRemainder(temp);
		String ans = i[0]+"."+i[1];
		return ans;
	} 

	public int getListTx() throws Exception{
		  String uniRestString = 
				  "http://api.etherscan.io/api?module=account&action=txlist&address="+ ethereum_addr +"&startblock=0&endblock=99999999&sort=asc&apikey="+ethereum_apikey;
		  HttpResponse<JsonNode> response = Unirest.post(uniRestString).asJson();
		  int txCnt = ((JSONArray) response.getBody().getObject().get("result")).length();
		  return txCnt;
	  }

}
