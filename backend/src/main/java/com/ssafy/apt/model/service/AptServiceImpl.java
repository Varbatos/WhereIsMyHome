package com.ssafy.apt.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptSeqByPriceDto;
import com.ssafy.apt.model.LcsResult;
import com.ssafy.apt.model.tmpDto;
import com.ssafy.apt.model.mapper.AptMapper;


@Service
public class AptServiceImpl implements AptService {

	private final AptMapper aptMapper;
	
	public AptServiceImpl(AptMapper aptMapper) {
		super();
		this.aptMapper = aptMapper;
	}

	@Override
	public List<AptDto> searchByCode(String code) {
		// TODO Auto-generated method stub
		return aptMapper.searchByCode(code);
	}

	@Override
    public List<AptDto> searchByLatAndLng(Map<String, String> params) {
        // 위경도를 이용한 아파트 검색
        return aptMapper.searchByLatAndLng(params);
    }

	@Override
	public List<AptDto> searchByAptSeq(String seq) {
		// TODO Auto-generated method stub
		return aptMapper.searchByAptSeq(seq);
	}

	@Override
	public List<tmpDto> convertHangul() {
		return aptMapper.convertHangul();
	}

	@Override
	public String hangulSplit(String apt_nm) {
        List<String> decomposed = decomposeKoreanCharacter(apt_nm);
        String hangulSplit = String.join("", decomposed);
        return hangulSplit;
	}

	public static char combinedJungSung(int choIndex,int jungIndex){
        return (char) (44032 + (choIndex * 21 * 28) + (jungIndex * 28));
    }
    public static char combinedJongSung(int choIndex,int jungIndex,int jongIndex){
        return (char) (44032 + (choIndex * 21 * 28) + (jungIndex * 28)+ jongIndex);
    }
    public static List<String> decomposeKoreanCharacter(String character) {
        List<String> result = new ArrayList<>();

        final int BASE_CODE = 44032; // "가"
        final int END_CODE = 55203; // "힣"

        final char[] CHO_SUNG = {
                'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        };
        final char[] JUNG_SUNG = {
                'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
        };
        final char[] JONG_SUNG = {
                ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        };

        for (int i = 0; i < character.length(); i++) {
            int charCode = character.charAt(i);

            if (charCode < BASE_CODE || charCode > END_CODE) {
                if (String.valueOf(character.charAt(i)).equals(" ")) {
                    continue;
                }
                result.add(String.valueOf(character.charAt(i)));
                continue;
            }
            int offset = charCode - BASE_CODE;
            int choIndex = offset / (21 * 28);
            int jungIndex = (offset % (21 * 28)) / 28;
            int jongIndex = offset % 28;

            result.add(String.valueOf(CHO_SUNG[choIndex])); // 초성
            switch (jungIndex) {//중성 처리
                case 9,10,11:
                    result.add(String.valueOf(combinedJungSung(choIndex, 8)));
                    break;
                case 14,15,16:
                    result.add(String.valueOf(combinedJungSung(choIndex, 13)));
                    break;
                case 19:
                    result.add(String.valueOf(combinedJungSung(choIndex, 18)));
                    break;
            }
            result.add(String.valueOf(combinedJungSung(choIndex,jungIndex)));
            switch (jongIndex) {//종성 처리,2개있을떄
                case 3:
                    result.add(String.valueOf(combinedJongSung(choIndex, jungIndex,1)));
                    break;
                case 5, 6:
                    result.add(String.valueOf(combinedJongSung(choIndex, jungIndex,4)));
                    break;
                case 9, 10, 11, 12, 13, 14, 15:
                    result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 8)));
                    break;
                case 18:
                    result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 17)));
                    break;
            }

            if (jongIndex == 0) {//다음 초성 처리
                if(i!=character.length()-1){//마지막 글자가 아니면
                    charCode = character.charAt(i+1);
                    offset = charCode - BASE_CODE;
                    int nextChoIndex = offset / (21 * 28);
                    switch (nextChoIndex){
                        case 0:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 1)));
                            break;
                        case 2:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 4)));
                            break;
                        case 3:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 7)));
                            break;
                        case 5:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 8)));
                            break;
                        case 6,7:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, nextChoIndex+10)));
                            break;
                        case 9:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 19)));
                            break;
                        case 11:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 21)));
                            break;
                        case 12:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 22)));
                            break;
                        case 14,15,16,17,18:
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, nextChoIndex+9)));
                            break;
                    }
                }
            }
            else{//종성이 있어!
                switch (jongIndex) {//종성이 'ㄳ','ㄵ'처럼 2개가 합쳐져있을때
                    case 3://ㄳ
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 1)));
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, jongIndex)));
                        break;
                    case 5, 6://ㄵ,ㄶ
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 4)));
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, jongIndex)));
                        break;
                    case 9, 10, 11, 12, 13, 14, 15://ㄺㄻㄼㄽㄾㄿㅀ
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 8)));
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, jongIndex)));
                        break;
                    case 18://ㅄ
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 17)));
                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, jongIndex)));
                        break;
                    default://종성이 1개일떄
                        if(i!=character.length()-1) {//마지막 글자가 아니면
                            charCode = character.charAt(i + 1);
                            offset = charCode - BASE_CODE;
                            int nextChoIndex = offset / (21 * 28);
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, jongIndex)));
                            switch (jongIndex) {//종성이 1개인데 합쳐질떄
                                case 1://ㄱ
                                    if (nextChoIndex == 9){//ㄳ
                                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 3)));
                                    }
                                    break;
                                case 4://ㄴ
                                    if(nextChoIndex==12){//ㄵ
                                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 5)));
                                    }
                                    else if (nextChoIndex==18) {//ㄶ
                                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 6)));
                                    }
                                    break;
                                case 8://ㄹ
                                    switch (nextChoIndex){
                                        case 0:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 9)));
                                            break;
                                        case 6:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 10)));
                                            break;
                                        case 7:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 11)));
                                            break;
                                        case 9:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 12)));
                                            break;
                                        case 16:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 13)));
                                            break;
                                        case 17:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 14)));
                                            break;
                                        case 18:
                                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 15)));
                                            break;
                                    }
                                    break;
                                case 17://ㅂ
                                    if (nextChoIndex == 9){//ㅄ
                                        result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, 18)));
                                    }
                                    break;
                            }
                        }
                        else{
                            result.add(String.valueOf(combinedJongSung(choIndex, jungIndex, jongIndex)));
                        }
                }
            }
        }
        return result;
    }

	@Override
	public void updateHangulEumjeolNm(tmpDto apt) {
		aptMapper.updateHangulEumjeolNm(apt); // 업데이트 실행
	}

	@Override
	public List<tmpDto> searchByHardName(String searchName) {
		List<String> searchCharacters = new ArrayList<>();
	    for (char c : searchName.toCharArray()) {
	        searchCharacters.add(String.valueOf(c));
	    }
		return aptMapper.searchByHardName(searchCharacters);
	}
	@Override
	public List<AptDto> searchByName(String searchName) {
		return aptMapper.searchByName(searchName);
	}

	@Override
	// LCS 길이와 범위를 계산하는 메서드
    public LcsResult calculateLCS(String a, String b) {
        int[][] counting = new int[a.length() + 1][b.length() + 1];
        StringBuilder sb = new StringBuilder();

        // LCS 길이 계산
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    counting[i][j] = counting[i - 1][j - 1] + 1;
                } else {
                    counting[i][j] = Math.max(counting[i - 1][j], counting[i][j - 1]);
                }
            }
        }
        int lcsLength = counting[a.length()][b.length()]; // LCS 길이
        int[] lcsIndex = new int[lcsLength];
        int curIndex=lcsLength-1;
        // LCS 문자열의 인덱스 추출
        int i = a.length();
        int j = b.length();
        while (i >= 1 && j >= 1) {
            int cur = counting[i][j];
            if (counting[i - 1][j] == cur) {
                i--;
            } else if (counting[i][j - 1] == cur) {
                j--;
            } else {
            	sb.append(j - 1+" "); // 인덱스를 저장
            	lcsIndex[curIndex]=j-1;
                curIndex--;
                i--;
                j--;
            }
        }

        
        int range = Integer.MAX_VALUE; // 초기 범위 값
        int first = Integer.MAX_VALUE; // 초기 범위 값
        if (sb.length() > 0) {
            sb.reverse(); // 인덱스 순서를 정렬
            first = lcsIndex[0];
            int last = lcsIndex[lcsLength-1];
            range = last - first; // 범위 계산
        }
        return new LcsResult(lcsLength, range, first);
    }

	@Override
	public List<AptSeqByPriceDto> searchPriceByApt(String apt_seq) {
		return aptMapper.searchPriceByApt(apt_seq); // 업데이트 실행
	}

}
