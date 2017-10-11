package com.hdong.upms.dao.enums;

import com.hdong.common.db.BaseEnum;

public enum UserPermissionType implements BaseEnum<Integer, String>{
    ADD(1,"加权限"), SUB(-1,"减权限");
    private Integer val;
    private String desc;
    UserPermissionType(Integer val, String desc) {
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
