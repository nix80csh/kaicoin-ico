/**
 * 
 */
package com.kaicoinico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaicoinico.entity.Account;

/**
 * @Project : kaicoin-ico
 * @FileName : AccountRepo.java
 * @Date : 2017. 8. 17.
 * @작성자 : 조성훈
 * @설명 :
 **/

public interface AccountRepo extends JpaRepository<Account, Integer> {
  Account findByEmail(String email);

  boolean existsByEmailAndGencode(String email, String gencode);
  
  boolean findByEthereumAddr(String ethereumAddr);

  Account findByIdfAccount(int idfAccount);
}
