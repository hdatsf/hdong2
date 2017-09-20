package com.hdong.upms.rpc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.upms.dao.mapper.UpmsApiMapper;
import com.hdong.upms.dao.mapper.UpmsRolePermissionMapper;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.mapper.UpmsUserMapper;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsRole;
import com.hdong.upms.dao.model.UpmsRolePermission;
import com.hdong.upms.dao.model.UpmsRolePermissionExample;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.rpc.api.UpmsApiService;

/**
 * UpmsApiService实现
 * Created by hdong on 2016/01/19.
 */
@Service
@Transactional
public class UpmsApiServiceImpl implements UpmsApiService {

    private static Logger _log = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Autowired
    UpmsUserMapper upmsUserMapper;

    @Autowired
    UpmsApiMapper upmsApiMapper;

    @Autowired
    UpmsRolePermissionMapper upmsRolePermissionMapper;

    @Autowired
    UpmsSystemMapper upmsSystemMapper;
    
    /**
     * 根据username、systemName获取用户角色和权限集合
     * @param username
     * @param systemName
     * @return
     */
    @Override
    public List<Set<String>> selectRolesPermissionsByName(String username, String systemName){
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<UpmsUser> userList = upmsUserMapper.selectByExample(userExample);
        if(userList.size()==0) {
            _log.error("Has not user with name:{1}", username);
            return null;
        }
        UpmsUser upmsUser = userList.get(0);
        // 当前用户所有角色
        List<UpmsRole> upmsRoles = selectUpmsRoleByUpmsUserId(upmsUser.getUserId());
        Set<String> roles = new HashSet<>();
        for (UpmsRole upmsRole : upmsRoles) {
            if (StringUtils.isNotBlank(upmsRole.getName())) {
                roles.add(upmsRole.getName());
            }
        }
        Set<String> permissions = new HashSet<>();
        //获取当前系统编号
        UpmsSystemExample systemExample = new UpmsSystemExample();
        systemExample.createCriteria().andNameEqualTo(systemName);
        List<UpmsSystem> systemList = upmsSystemMapper.selectByExample(systemExample);
        if(systemList.size()==0) {
            _log.error("Has not system with name:{1}", systemName);
            return null;
        }
        UpmsSystem upmsSystem = systemList.get(0);
        // 当前用户所有权限
        List<UpmsPermission> upmsPermissions = selectUpmsPermissionByUpmsUserIdAndSystemId(upmsSystem.getSystemId(), upmsUser.getUserId());
        for (UpmsPermission upmsPermission : upmsPermissions) {
            if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
                permissions.add(upmsPermission.getPermissionValue());
            }
        }
        List<Set<String>> retList = new ArrayList<Set<String>>();
        retList.add(roles);
        retList.add(permissions);
        return retList;
    }
    
    /**
     * 根据username、systemName获取用户角色和权限集合，并缓存
     * @param username
     * @param systemName
     * @return
     */
    @Override
    @Cacheable(value = "hdong-upms-rpc-service-ehcache", key = "'selectRolesPermissionsByName'+ #username + '_SystemName_' + #systemName")
    public List<Set<String>> selectRolesPermissionsByNameByCache(String username, String systemName){
        return selectRolesPermissionsByName(username, systemName);
    }
    
    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    private List<UpmsPermission> selectUpmsPermissionByUpmsUserIdAndSystemId(Integer systemId, Integer upmsUserId) {
        // 用户不存在或锁定状态
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UpmsPermission> upmsPermissions = upmsApiMapper.selectUpmsPermissionByUpmsUserIdBySystemId(systemId, upmsUserId);
        return upmsPermissions;
    }

    /**
     * 根据用户id获取菜单
     * @param upmsUserId
     * @return
     */
    @Override
    public List<UpmsPermission> selectMenuByUpmsUserIdAndSystemId(Integer systemId, Integer upmsUserId){
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UpmsPermission> upmsPermissions = upmsApiMapper.selectMenuByUpmsUserIdAndSystemId(systemId, upmsUserId);
        return upmsPermissions;
    }
    
    /**
     * 根据用户id获取所属的角色
     * @param upmsUserId
     * @return
     */
    private List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        // 用户不存在或锁定状态
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsRoleByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UpmsRole> upmsRoles = upmsApiMapper.selectUpmsRoleByUpmsUserId(upmsUserId);
        return upmsRoles;
    }

    /**
     * 根据角色id获取所拥有的权限
     * @param upmsRoleId
     * @return
     */
    @Override
    public List<UpmsRolePermission> selectUpmsRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
        UpmsRolePermissionExample upmsRolePermissionExample = new UpmsRolePermissionExample();
        upmsRolePermissionExample.createCriteria()
                .andRoleIdEqualTo(upmsRoleId);
        List<UpmsRolePermission> upmsRolePermissions = upmsRolePermissionMapper.selectByExample(upmsRolePermissionExample);
        return upmsRolePermissions;
    }
}