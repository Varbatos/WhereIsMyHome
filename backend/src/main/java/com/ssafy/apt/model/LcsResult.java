package com.ssafy.apt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LcsResult {
    private final int length;  // LCS 길이
    private final int range;   // 범위 [마지막 값 - 첫 번째 값]
    private final int FirstIndex;

    public LcsResult(int length, int range,int FirstIndex) {
        this.length = length;
        this.range = range;
        this.FirstIndex = FirstIndex;
    }


}
