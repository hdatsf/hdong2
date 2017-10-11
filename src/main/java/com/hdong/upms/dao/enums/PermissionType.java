package com.hdong.upms.dao.enums;

import com.hdong.common.db.BaseEnum;

public enum PermissionType implements BaseEnum<Integer, String>{
    SYSTEM(0,"系统"), CATALOG(1,"目录"), MENU(2,"菜单"), BUTTON(3,"按钮");
    private Integer val;
    private String desc;
    PermissionType(Integer val, String desc) {
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
