package com.hdong.upms.dao.mapper;

import com.hdong.upms.dao.model.SysDict;
import com.hdong.upms.dao.model.SysDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDictMapper {
    long countByExample(SysDictExample example);

    int deleteByExample(SysDictExample example);

    int deleteByPrimaryKey(@Param("app") String app, @Param("type") String type, @Param("val") String val);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    List<SysDict> selectByExample(SysDictExample example);

    SysDict selectByPrimaryKey(@Param("app") String app, @Param("type") String type, @Param("val") String val);

    int updateByExampleSelective(@Param("record") SysDict record, @Param("example") SysDictExample example);

    int updateByExample(@Param("record") SysDict record, @Param("example") SysDictExample example);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
}