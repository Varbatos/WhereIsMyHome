package com.ssafy.apt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptSeqByPriceDto;
import com.ssafy.apt.model.LcsResult;
import com.ssafy.apt.model.tmpDto;
import com.ssafy.apt.model.service.AptService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/apt")
public class AptController {

	@Autowired
	private AptService aptService;
	
	@GetMapping("/listfull")
	public String showRegist(HttpSession session) {
		System.out.println("여기 들어왔어용");
		return "apt";
	}
	@PostMapping("/searchByCode")
	@ResponseBody
	public List<AptDto> searchByCode(@RequestParam("dong_code") String code) {
	    System.out.println("searchByCode :" + code);
	    List<AptDto> apts = aptService.searchByCode(code);
	 // 중복된 apt_seq를 제거하면서 최신 거래일 기준으로 덮어씀
	    Map<String, AptDto> aptMap = apts.stream()
	        .collect(Collectors.toMap(
	            AptDto::getApt_seq,  // apt_seq를 키로 사용
	            apt -> apt,  // AptDto를 값으로 사용
	            (existing, replacement) -> {
	                int existingDate = existing.getDeal_year() * 10000 + existing.getDeal_month() * 100 + existing.getDeal_day();
	                int replacementDate = replacement.getDeal_year() * 10000 + replacement.getDeal_month() * 100 + replacement.getDeal_day();

	                // 거래일을 비교하여 더 최신 거래일을 가진 객체를 반환
	                return (existingDate > replacementDate) ? existing : replacement;
	            }
	        ));

	    // 중복이 제거된 리스트 반환
	    return new ArrayList<>(aptMap.values());
	}
	
	@PostMapping("/searchByLatAndLng")
	@ResponseBody
	public List<AptDto> searchByLatAndLng(@RequestParam("topLat") String topLat,
										@RequestParam("bottomLat") String bottomLat,
										@RequestParam("leftLng") String leftLng,
										@RequestParam("rightLng") String rightLng) {
	    Map<String, String> params = new HashMap<>();
	    params.put("topLat", topLat);
	    params.put("bottomLat", bottomLat);
	    params.put("leftLng", leftLng);
	    params.put("rightLng", rightLng);
	    System.out.println("topLat"+ topLat);
	    System.out.println("bottomLat"+ bottomLat);
	    System.out.println("leftLng"+ leftLng);
	    System.out.println("rightLng"+ rightLng);
	    List<AptDto> apts = aptService.searchByLatAndLng(params);
	    
	    // 중복된 apt_seq를 제거하면서 최신 거래일 기준으로 덮어씀
	    Map<String, AptDto> aptMap = apts.stream()
		        .collect(Collectors.toMap(
		            AptDto::getApt_seq,  // apt_seq를 키로 사용
		            apt -> apt,  // AptDto를 값으로 사용
		            (existing, replacement) -> {
		                int existingDate = existing.getDeal_year() * 10000 + existing.getDeal_month() * 100 + existing.getDeal_day();
		                int replacementDate = replacement.getDeal_year() * 10000 + replacement.getDeal_month() * 100 + replacement.getDeal_day();

		                // 거래일을 비교하여 더 최신 거래일을 가진 객체를 반환
		                return (existingDate > replacementDate) ? existing : replacement;
		            }
		        ));

		    // 중복이 제거된 리스트 반환
		    return new ArrayList<>(aptMap.values());
	}
	
	@GetMapping("/searchByAptSeq")
	@ResponseBody
	public List<AptDto> searchByAptSeq(@RequestParam("apt_seq") String apt_seq) {
		List<AptDto> apts = aptService.searchByAptSeq(apt_seq);
		
		return apts;
	}
	
	@GetMapping("/convertHangul")
	public void convertHangul() {
		System.out.println("실행합니다.");
		List<tmpDto> apts = aptService.convertHangul();
		for(tmpDto apt:apts) {
			String hangulSplit = aptService.hangulSplit(apt.getApt_nm());
			//System.out.println(apt.getApt_nm()+" "+hangulSplit);
			apt.setApt_eumjeol_nm(hangulSplit);
			aptService.updateHangulEumjeolNm(apt);
		}
		
		System.out.println("완료했습니다.");
	}
	
	@PostMapping("/searchByHardName")
	@ResponseBody
	public List<AptDto> searchByHardName(@RequestParam("searchName") String searchName,
	                                      @RequestParam("centerLat") String centerLat,
	                                      @RequestParam("centerLng") String centerLng) {
	    String trimmedSearchName = searchName.replace("아파트", "").replace(" ",""); // "아파트" 제거 후 트림 처리
	    if(trimmedSearchName.length() == 0) {
	        return new ArrayList<>(); // 빈 리스트 반환
	    }
	    
	    char firstChar = trimmedSearchName.charAt(0);
	    boolean isChosung = false;
	    
	    if (firstChar >= 'ㄱ' && firstChar <= 'ㅎ') {
	        isChosung = true;
	    }
	    
	    List<tmpDto> apts = aptService.searchByHardName(trimmedSearchName);

	    // isChosung이 true일 경우, LCS 길이가 trimmedSearchName의 길이와 일치하는 값만 필터링
	    if (isChosung) {
	        apts = apts.stream()
	                   .filter(dto -> {
	                       LcsResult result = aptService.calculateLCS(trimmedSearchName, dto.getApt_eumjeol_nm());
	                       return result.getLength() == trimmedSearchName.length(); // 길이가 일치하는지 확인
	                   })
	                   .collect(Collectors.toList());
	    }

	    // 정렬 처리
	    apts.sort((dto1, dto2) -> {
	        LcsResult result1 = aptService.calculateLCS(trimmedSearchName, dto1.getApt_eumjeol_nm());
	        LcsResult result2 = aptService.calculateLCS(trimmedSearchName, dto2.getApt_eumjeol_nm());

	        // 1차 정렬 기준: LCS 길이 (큰 값 우선)
	        if (result1.getLength() != result2.getLength()) {
	            return Integer.compare(result2.getLength(), result1.getLength());
	        }

	        // 2차 정렬 기준: 범위 [마지막 값 - 첫 번째 값] (작은 값 우선)
	        if (result1.getRange() != result2.getRange()) {
	            return Integer.compare(result1.getRange(), result2.getRange());
	        }

	        // 3차 정렬 기준: 첫 번째 값 (작은 값 우선)
	        if (result1.getFirstIndex() != result2.getFirstIndex()) {
	            return Integer.compare(result1.getFirstIndex(), result2.getFirstIndex());
	        }

	        // 4차 정렬 기준: 거리 순
	        double centerLatDouble;
	        double centerLngDouble;
	        try {
	            centerLatDouble = Double.parseDouble(centerLat);
	            centerLngDouble = Double.parseDouble(centerLng);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Invalid center coordinates. Please provide valid numbers.");
	        }

	        double lat1 = getLat(dto1.getLat());
	        double lng1 = getLng(dto1.getLng());
	        double lat2 = getLat(dto2.getLat());
	        double lng2 = getLng(dto2.getLng());

	        double dist1 = Math.pow(lat1 - centerLatDouble, 2) + Math.pow(lng1 - centerLngDouble, 2);
	        double dist2 = Math.pow(lat2 - centerLatDouble, 2) + Math.pow(lng2 - centerLngDouble, 2);

	        return Double.compare(dist1, dist2);
	    });
	    // tmpDto 리스트를 AptDto로 변환
	    List<AptDto> aptDtos = apts.stream()
	                               .map(dto -> {
	                                   AptDto aptDto = new AptDto();
	                                   aptDto.setApt_seq(dto.getApt_seq());
	                                   aptDto.setSgg_cd(dto.getSgg_cd());
	                                   aptDto.setUmd_cd(dto.getUmd_cd());
	                                   aptDto.setUmd_nm(dto.getUmd_nm());
	                                   aptDto.setJibun(dto.getJibun());
	                                   aptDto.setApt_nm(dto.getApt_nm());
	                                   aptDto.setBuild_year(dto.getBuild_year());
	                                   aptDto.setLat(dto.getLat());
	                                   aptDto.setLng(dto.getLng());
	                                   aptDto.setNo(dto.getNo());
	                                   aptDto.setFloor(dto.getFloor());
	                                   aptDto.setDeal_year(dto.getDeal_year());
	                                   aptDto.setDeal_month(dto.getDeal_month());
	                                   aptDto.setDeal_day(dto.getDeal_day());
	                                   aptDto.setExclu_use_ar(dto.getExclu_use_ar());
	                                   aptDto.setDeal_amount(dto.getDeal_amount());
	                                   return aptDto;
	                               })
	                               .collect(Collectors.toList());
	 // 10개 이상이면 10개만 반환
	    return aptDtos.size() > 10 ? aptDtos.subList(0, 10) : aptDtos;
	}

	    
	@PostMapping("/searchByName")
	@ResponseBody
	public List<AptDto> searchByName(@RequestParam("searchName") String searchName,
	                                 @RequestParam("centerLat") String centerLat,
	                                 @RequestParam("centerLng") String centerLng) {
	    // 검색어 정리
	    searchName = searchName.replace("아파트", "").trim();

	    // 중심 좌표를 double로 변환
	    double centerLatDouble;
	    double centerLngDouble;

	    try {
	        centerLatDouble = Double.parseDouble(centerLat);
	        centerLngDouble = Double.parseDouble(centerLng);
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Invalid center coordinates. Please provide valid numbers.");
	    }

	    // 아파트 목록 검색
	    List<AptDto> apts = aptService.searchByName(searchName);

	    // DTO를 거리 계산 후 정렬
	    return apts.stream()
	               // lat 또는 lng가 null인 항목 제외
	               .filter(apt -> isValidDouble(apt.getLat()) && isValidDouble(apt.getLng()))
	               .sorted((a1, a2) -> {
	                   double lat1 = Double.parseDouble(a1.getLat());
	                   double lng1 = Double.parseDouble(a1.getLng());
	                   double lat2 = Double.parseDouble(a2.getLat());
	                   double lng2 = Double.parseDouble(a2.getLng());

	                   double dist1 = Math.pow(lat1 - centerLatDouble, 2) + Math.pow(lng1 - centerLngDouble, 2);
	                   double dist2 = Math.pow(lat2 - centerLatDouble, 2) + Math.pow(lng2 - centerLngDouble, 2);

	                   return Double.compare(dist1, dist2);
	               })
	               .limit(10) // 가장 가까운 10개만 선택
	               .toList(); // 결과를 리스트로 변환하여 반환
	}

	/**
	 * 문자열이 유효한 double 값인지 확인하는 메서드.
	 */
	private boolean isValidDouble(String value) {
	    if (value == null || value.isEmpty()) {
	        return false;
	    }
	    try {
	        Double.parseDouble(value);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	private double getLat(String lat) {
	    if (lat == null || lat.isEmpty()) {
	        return 0.0; // 또는 다른 기본값을 설정
	    }
	    return Double.parseDouble(lat);
	}

	/**
	 * longitude 값이 null일 경우 기본값을 반환하거나 계산을 하지 않도록 처리
	 */
	private double getLng(String lng) {
	    if (lng == null || lng.isEmpty()) {
	        return 0.0; // 또는 다른 기본값을 설정
	    }
	    return Double.parseDouble(lng);
	}

	@PostMapping("/searchPriceByApt")
	@ResponseBody
	public List<AptSeqByPriceDto> searchPriceByApt(@RequestParam("apt_seq") String apt_seq) {
	    System.out.println("searchPriceByApt: " + apt_seq);
	    List<AptSeqByPriceDto> apts = aptService.searchPriceByApt(apt_seq);

	    return apts;
	}




}
