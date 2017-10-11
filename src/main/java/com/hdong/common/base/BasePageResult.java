package com.hdong.common.base;

import java.util.List;

/**
 * 统一查询返回类
 * Created by hdong on 2017/2/18.
 */
public class BasePageResult<T> {
    private int total;
    private List<T> rows;
    public BasePageResult(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
    
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    
    @Override
    public String toString() {
        StringBuffer rowsStr = new StringBuffer();
        int length = rows.size();
        if(rows.size()>=3) {
            length = 3;
        }
        for(int i=0;i<length;i++) {
            rowsStr.append(rows.get(i));
        }
        return "BasePageResult [total=" + total + ", rows=" + rowsStr + "]";
    }
}
