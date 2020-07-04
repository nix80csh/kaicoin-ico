package com.kaicoinico.dto;

import lombok.Data;

public class TxDto {

	@Data
	public static class KaiTxDto{
		private double amount;
		private String time;
		
	}
	
}
