package com.ssafy.apt.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptSeqByPriceDto;
import com.ssafy.apt.model.tmpDto;

@Mapper
public interface AptMapper {
	public List<AptDto> searchByCode(String code);
	public List<AptDto> searchByLatAndLng(Map<String, String> params);
	public List<AptDto> searchByAptSeq(String seq);
	public List<tmpDto> convertHangul();
	public void updateHangulEumjeolNm(tmpDto apt);
	public List<tmpDto> searchByHardName(List<String> searchName);
	public List<AptDto> searchByName(String searchName);
	public List<AptSeqByPriceDto> searchPriceByApt(String apt_seq);
}
