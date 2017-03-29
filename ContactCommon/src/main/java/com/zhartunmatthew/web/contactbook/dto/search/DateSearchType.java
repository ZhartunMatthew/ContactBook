package com.zhartunmatthew.web.contactbook.dto.search;

public enum DateSearchType {
    SAME, YOUNGER, OLDER;

    public static DateSearchType getType(int i) {
        if(i == 0) {
            return SAME;
        } else if(i == 1) {
            return YOUNGER;
        } else {
            return OLDER;
        }
    }
}
