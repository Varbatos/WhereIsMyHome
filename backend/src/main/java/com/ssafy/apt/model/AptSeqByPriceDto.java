package com.ssafy.apt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AptSeqByPriceDto {
	private String name; 		// 거래월
	
	@JsonProperty("3.3㎡당 가격")
	private int value;
	
	@JsonProperty("거래 개수")
	private int count;
}
