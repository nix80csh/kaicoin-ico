/**-
 * 
 */
package com.kaicoinico.common;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Project : kaicoin-ico
 * @FileName : GSChainUtilTest.java
 * @Date : 2017. 8. 24.
 * @작성자 : 조성훈
 * @설명 :
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GSChainUtilTest {

  @Autowired GSChainUtil gschainUtil;

  @Test
  public void getNetAddress() {
    String address = gschainUtil.getNetAddress("umanking@naver.com");
    assertNotNull(address);
  }

}
