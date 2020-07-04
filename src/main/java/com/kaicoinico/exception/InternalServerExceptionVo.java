package com.kaicoinico.exception;

import lombok.Data;

@Data
public class InternalServerExceptionVo {
  private int errorCode;
  private String errorMsg;
}
