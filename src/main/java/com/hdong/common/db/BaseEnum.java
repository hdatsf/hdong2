package com.hdong.common.db;

public interface BaseEnum<V, D> {  
    public V getVal();  
    public D getDesc();
    
//    public static BaseEnum getEnum(Class clazz,  Object val) {
//        if(clazz.isEnum() && BaseEnum.class.isAssignableFrom(clazz)) {
//            for(Object status : clazz.getEnumConstants()) {
//                if(((BaseEnum)status).getVal().equals(val)) {
//                    return (BaseEnum)status;
//                }
//            }
//        }
//        return null;
//    }
}  
