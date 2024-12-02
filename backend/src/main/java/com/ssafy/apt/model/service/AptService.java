package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptSeqByPriceDto;
import com.ssafy.apt.model.LcsResult;
import com.ssafy.apt.model.tmpDto;



public interface AptService {
	public List<AptDto> searchByCode(String code);
	public List<AptDto> searchByLatAndLng(Map<String, String> params);
	public List<AptDto> searchByAptSeq(String seq);
	public List<tmpDto> convertHangul();
	public String hangulSplit(String apt_nm);
	public void updateHangulEumjeolNm(tmpDto apt);
	public List<tmpDto> searchByHardName(String searchName);
	public List<AptDto> searchByName(String searchName);
	public LcsResult calculateLCS(String a, String b);
	public List<AptSeqByPriceDto> searchPriceByApt(String apt_seq);
}
