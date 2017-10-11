package com.hdong.common.base;

import java.util.List;

/**
 * 统一返回结果类
 * Created by hdong on 2017/2/18.
 */
public class BaseResult {

    // 状态码：1成功，其他为失败
    private int code = 1;

    // 成功为success，其他为失败原因
    private String msg = "success";

    // 数据结果集
    private Object data = null;
    
    public BaseResult() {
    }

    public BaseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuffer dataStr = new StringBuffer();
        if(data instanceof List) {
            List<?> dataList = (List<?>)data;
            int length = dataList.size();
            if(dataList.size()>=3) {
                length = 3;
            }
            for(int i=0;i<length;i++) {
                dataStr.append(dataList.get(i));
            }
        }
        return "BaseResult [code=" + code + ", msg=" + msg + ", data=" + dataStr + "]";
    }

}
