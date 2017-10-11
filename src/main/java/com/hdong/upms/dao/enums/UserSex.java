package com.hdong.upms.dao.enums;

import com.hdong.common.db.BaseEnum;

public enum UserSex implements BaseEnum<Integer, String>{
    MALE(1,"男"), FEMALE(2,"女");
    private Integer val;
    private String desc;
    UserSex(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }
    @Override
    public Integer getVal() {
        return val;
    }
    @Override
    public String getDesc() {
        return desc;
    }
}
