package com.hdong.upms.rpc.api;

import java.util.List;
import java.util.Set;

import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsUser;

/**
 * upms系统接口
 * Created by hdong on 2017/2/11.
 */
public interface UpmsApiService {
    
    /**
     * 根据username获取用户角色和权限集合
     * @param username
     * @return
     */
    List<Set<String>> selectRolesPermissionsByName(String username);
    
    /**
     * 根据username获取用户角色和权限集合，并缓存
     * @param username
     * @return
     */
    List<Set<String>> selectRolesPermissionsByNameByCache(String username);
    
    /**
     * 根据用户id获取菜单
     * @param upmsUserId
     * @return
     */
    List<UpmsPermission> selectMenuByUpmsUserName(String username);
    
    /**
     * 根据用户名称获取用户信息
     * @param username
     * @return
     */
    UpmsUser selectUserByUsername(String username);
    
    /**
     * 获取用户有权限的系统集合
     * @param upmsUserId
     * @return
     */
    List<UpmsSystem> selectSystemsByUserId(Integer upmsUserId);
    
}
