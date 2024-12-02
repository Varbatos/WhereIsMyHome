package com.ssafy.apt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AptDto {

	private String apt_seq; 			// 아파트 단지 일련번호
	private String sgg_cd; 			// 법정동 시구군 코드
	private String umd_cd; 			// 법정동 읍면동 코드
	private String umd_nm; 			// 법정동 읍면동
	private String jibun; 			// 지번
	private String apt_nm; 			// 아파트 단지명
	private int build_year; 			// 건축년도
	private String lat; 		// 위도
	private String lng; 		// 경도
	
	private int no; 				// 일련번호
	private String floor; 		// 층
	private int deal_year; 		// 거래년도
	private int deal_month; 		// 거래월
	private int deal_day; 		// 거래일
	private double exclu_use_ar; 	// 전용면적
	private String deal_amount; 	// 거래금액
	
	@Override
	public String toString() {
		return "AptDto [apt_seq=" + apt_seq + ", sgg_cd=" + sgg_cd + ", umd_cd=" + umd_cd + ", umd_nm=" + umd_nm
				+ ", jibun=" + jibun + ", apt_nm=" + apt_nm + ", build_year=" + build_year + ", lat=" + lat + ", lng="
				+ lng + ", no=" + no + ", floor=" + floor + ", deal_year=" + deal_year + ", deal_month=" + deal_month
				+ ", deal_day=" + deal_day + ", exclu_use_ar=" + exclu_use_ar + ", deal_amount=" + deal_amount
				+ ", apt_eumjeol_nm=" + "]";
	}

}
