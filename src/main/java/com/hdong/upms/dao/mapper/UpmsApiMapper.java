package com.hdong.upms.dao.mapper;


import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsRole;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 用户VOMapper
 * Created by hdong on 2017/01/07.
 */
public interface UpmsApiMapper {

    // 根据用户id获取所拥有的权限
    List<UpmsPermission> selectUpmsPermissionByUpmsUserIdBySystemId(@Param("systemId")Integer systemId, @Param("upmsUserId")Integer upmsUserId);
    
    // 根据用户id获取菜单
    List<UpmsPermission> selectMenuByUpmsUserIdAndSystemId(@Param("systemId")Integer systemId, @Param("upmsUserId")Integer upmsUserId);

	// 根据用户id获取所属的角色
	List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
	
}