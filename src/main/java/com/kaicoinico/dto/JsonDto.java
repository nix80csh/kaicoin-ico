package com.kaicoinico.dto;

import java.util.List;

import lombok.Data;



@Data
public class JsonDto<entityType> {

  private entityType dataObject;
  private List<entityType> dataList;

}
