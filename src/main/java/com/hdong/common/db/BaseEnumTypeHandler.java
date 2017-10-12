package com.hdong.common.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
/**
 * mybatis枚举类型转换
 * @author hdong
 *
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public class BaseEnumTypeHandler<E extends Enum<?> & BaseEnum> extends BaseTypeHandler<BaseEnum> {

    private Class<E> type;

    public BaseEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter.getVal());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object i = rs.getObject(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return codeOf(type, i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object i = rs.getObject(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return codeOf(type, i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object i = cs.getObject(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return codeOf(type, i);
        }
    }
    
    private E codeOf(Class<E> enumClass, Object value) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getVal().equals(value)) {
                return e;
            }
        }
        return null;
    }
}