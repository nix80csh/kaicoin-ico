/**
 * 
 */
package com.kaicoinico.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Project : freetty-api-customer
 * @FileName : DuplicateEntityExceptionMsg.java
 * @Date : 2017. 3. 17.
 * @작성자 : 조성훈
 * @설명 : 중복 엔티티 타입의 메시지 추상화
 **/

@Getter
@AllArgsConstructor
public enum LogicErrorList {

  DuplicateEntity_Account(101, "DuplicateEntity_Account"), DoesNotExist_Email(201,
      "DoesNotExist_Email"), DoesNotExist_Account(202,
          "DoesNotExist_Account"), DoesNotExist_EtherAddress(801,
              "DoesNotExist_EtherAddress"), NotMatched(901, "NotMatched"), SMSModuleException(902,
                  "SMSModuleException"), MailModuleException(903,
                      "MailModuleException"), KaicoinModuleException(904,
                          "KaicoinModuleException"), KaicoinPermissionDenied(905,
                              "KaicoinPermissionDenied");

  private final int errorCode;
  private final String errorMsg;

}
