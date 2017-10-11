package com.hdong.upms.dao.enums;

import com.hdong.common.db.BaseEnum;

public enum UserLocked implements BaseEnum<Integer, String>{
    NORMAL(1,"正常"), LOCKED(-1,"锁定");
    private Integer val;
    private String desc;
    UserLocked(Integer val, String desc) {
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
