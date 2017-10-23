package com.hdong.upms.dao.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsRole;
import com.hdong.upms.dao.model.UpmsSystem;

/**
 * 用户VOMapper
 * Created by hdong on 2017/01/07.
 */
public interface UpmsApiMapper {

    // 根据用户id获取所拥有的权限
    List<UpmsPermission> selectUpmsPermissionByUpmsUserId(@Param("upmsUserId")Integer upmsUserId);
    
    // 根据用户id获取菜单
    List<UpmsPermission> selectMenuByUpmsUserId(@Param("upmsUserId")Integer upmsUserId);

	// 根据用户id获取所属的角色
	List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
	
	// 获取用户有权限的系统集合
	List<UpmsSystem> selectSystemsByUserId(Integer upmsUserId);
	
}