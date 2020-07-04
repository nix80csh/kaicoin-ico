/**
 * 
 */
package com.kaicoinico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kaicoinico.common.GSChainUtil;

/**
 * @Project : kaicoin-ico
 * @FileName : HomeController.java
 * @Date : 2017. 8. 22.
 * @작성자 : 조성훈
 * @설명 :
 **/

@RestController
@RequestMapping("/home")
public class HomeController {

	
	@Autowired GSChainUtil gsChainUtil;
	
  @RequestMapping("/test")
  public String test() {
    return "test";
  }
  
 
}
