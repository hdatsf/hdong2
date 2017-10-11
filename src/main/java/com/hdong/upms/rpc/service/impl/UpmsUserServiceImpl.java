package com.hdong.upms.rpc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.common.util.SequenceUtil;
import com.hdong.upms.dao.mapper.UpmsOrganizationMapper;
import com.hdong.upms.dao.mapper.UpmsPermissionMapper;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.mapper.UpmsUserMapper;
import com.hdong.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.hdong.upms.dao.mapper.UpmsUserPermissionMapper;
import com.hdong.upms.dao.mapper.UpmsUserRoleMapper;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.dao.model.UpmsUserOrganization;
import com.hdong.upms.dao.model.UpmsUserOrganizationExample;
import com.hdong.upms.dao.model.UpmsUserPermissionExample;
import com.hdong.upms.dao.model.UpmsUserRoleExample;
import com.hdong.upms.rpc.api.UpmsUserService;

/**
 * UpmsUserService实现 Created by hdong on 2017/8/15.
 */
@Service
@Transactional
@BaseService
public class UpmsUserServiceImpl extends BaseServiceImpl<UpmsUserMapper, UpmsUser, UpmsUserExample> implements UpmsUserService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserServiceImpl.class);

    @Autowired
    UpmsUserMapper upmsUserMapper;

    @Autowired
    UpmsUserOrganizationMapper upmsUserOrganizationMapper;
    
    @Autowired
    UpmsUserRoleMapper upmsUserRoleMapper;
    
    @Autowired
    UpmsUserPermissionMapper upmsUserPermissionMapper;
    
    @Autowired
    UpmsOrganizationMapper upmsOrganizationMapper;
    
    @Autowired
    UpmsSystemMapper upmsSystemMapper;
    
    @Autowired
    UpmsPermissionMapper upmsPermissionMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }

    @Override
    public UpmsUser createUser(UpmsUser upmsUser, Integer organizationId) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(upmsUser.getUsername());
        long count = upmsUserMapper.countByExample(upmsUserExample);
        if (count > 0) {
            _log.info("id ={1} is exist!", upmsUser.getUserId());
            return null;
        }
        upmsUserMapper.insert(upmsUser);
        if (organizationId != null) {
            UpmsUserOrganization userOrg = new UpmsUserOrganization();
            userOrg.setUserId(upmsUser.getUserId());
            userOrg.setOrganizationId(organizationId);
            userOrg.setUserOrganizationId(SequenceUtil.getInt(UpmsUserOrganization.class));
            upmsUserOrganizationMapper.insert(userOrg);
        }
        return upmsUser;
    }

    @Override
    public int deleteUser(List<Integer> ids) {
        //删用户
        UpmsUserExample ex = new UpmsUserExample();
        ex.createCriteria().andUserIdIn(ids);
        int count = upmsUserMapper.deleteByExample(ex);
        //删用户组织
        UpmsUserOrganizationExample ex2 = new UpmsUserOrganizationExample();
        ex2.createCriteria().andUserIdIn(ids);
        upmsUserOrganizationMapper.deleteByExample(ex2);
        //删用户角色
        UpmsUserRoleExample ex3 = new UpmsUserRoleExample();
        ex3.createCriteria().andUserIdIn(ids);
        upmsUserRoleMapper.deleteByExample(ex3);
        
        //删用户角色
        UpmsUserPermissionExample ex4 = new UpmsUserPermissionExample();
        ex4.createCriteria().andUserIdIn(ids);
        upmsUserPermissionMapper.deleteByExample(ex4);
        
        return count;
    }
}
