package com.hdong.upms.common.constant;

/**
 * upms系统接口结果常量枚举类
 * Created by hdong on 2017/2/18.
 */
public enum UpmsResultConstant {

    FAILED(0, "操作失败"),
    SUCCESS(1, "操作成功"),
    PARAM_VALID_ERROR(10001,"参数格式错误"),
    EMPTY_USERNAME(10101, "用户名不能为空"),
    EMPTY_PASSWORD(10102, "密码不能为空"),
    INVALID_USERNAME(10103, "账号不存在"),
    INVALID_PASSWORD(10104, "密码错误"),
    INVALID_ACCOUNT(10105, "账号被锁"),
    USER_NOEXIST(10106, "账号被锁");
    

    private int code;
    private String msg;

    UpmsResultConstant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
