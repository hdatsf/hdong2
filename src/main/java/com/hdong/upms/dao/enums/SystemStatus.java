package com.hdong.upms.dao.enums;

import com.hdong.common.db.BaseEnum;

public enum SystemStatus implements BaseEnum<Integer, String>{
    NORMAL(1,"正常"), ABNORMAL(-1,"异常");
    private Integer val;
    private String desc;
    SystemStatus(Integer val, String desc) {
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
