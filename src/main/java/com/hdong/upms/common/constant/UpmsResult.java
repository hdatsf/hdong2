package com.hdong.upms.common.constant;

import com.hdong.common.base.BaseResult;

/**
 * upms系统常量枚举类
 * Created by hdong on 2017/2/18.
 */
public class UpmsResult extends BaseResult {
    
    public UpmsResult(UpmsResultConstant upmsResultConstant) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMsg(), null);
    }
    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMsg(), data);
    }

}
