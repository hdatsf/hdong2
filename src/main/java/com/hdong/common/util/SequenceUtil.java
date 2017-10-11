package com.hdong.common.util;

/**
 * 
 * 序列号获取类
 * @author hdong
 * @creation 2017年9月21日
 */
public class SequenceUtil {
    /**
     * 获取序列号，不会回滚
     * @param tableName
     * @return
     */
    public static long getLong(Class<?> tableName) {
        return RedisUtil.incr(tableName.getSimpleName());
    }
    
    /**
     * 获取序列号，到MaxValue会自动回滚
     * @param tableName 推荐使用表对应的类的Class
     * @return
     */
    public static int getInt(Class<?> tableName) {
        long seq = RedisUtil.incr(tableName.getSimpleName());
        if(seq > Integer.MAX_VALUE) {
            //这里乘以10000是为了防止高并发场景，+1是为了防止出现0
            int ret = (int) ((seq)%Integer.MAX_VALUE)*10000+1;
            set(tableName, ret);
            return ret;
        }else {
            return (int) seq;
        }
    }
    
    /**
     * 重置序列号
     * @param tableName
     * @param num
     */
    public static void set(Class<?> tableName, long num) {
        RedisUtil.set("sequence:"+tableName.getSimpleName(), String.valueOf(num));
    }
}
