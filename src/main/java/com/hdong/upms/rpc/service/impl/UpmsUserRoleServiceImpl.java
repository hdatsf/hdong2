package com.hdong.upms.rpc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.common.db.DataSource;
import com.hdong.common.db.DataSourceEnum;
import com.hdong.common.util.SequenceUtil;
import com.hdong.upms.dao.mapper.UpmsUserRoleMapper;
import com.hdong.upms.dao.model.UpmsUserRole;
import com.hdong.upms.dao.model.UpmsUserRoleExample;
import com.hdong.upms.rpc.api.UpmsUserRoleService;

/**
 * UpmsUserRoleService实现 Created by hdong on 2017/3/20.
 */
@Service
@BaseService
public class UpmsUserRoleServiceImpl extends BaseServiceImpl<UpmsUserRoleMapper, UpmsUserRole, UpmsUserRoleExample> implements UpmsUserRoleService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserRoleServiceImpl.class);

    @Autowired
    private UpmsUserRoleMapper upmsUserRoleMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }

    @Override
    @DataSource(name = DataSourceEnum.MASTER)
    @Transactional
    public int role(String[] roleIds, int id) {
        int result = 0;
        // 删除旧记录
        UpmsUserRoleExample upmsUserRoleExample = new UpmsUserRoleExample();
        upmsUserRoleExample.createCriteria().andUserIdEqualTo(id);
        upmsUserRoleMapper.deleteByExample(upmsUserRoleExample);
        // 增加新记录
        if (null != roleIds) {
            for (String roleId : roleIds) {
                if (StringUtils.isBlank(roleId)) {
                    continue;
                }
                UpmsUserRole upmsUserRole = new UpmsUserRole();
                upmsUserRole.setUserId(id);
                upmsUserRole.setRoleId(NumberUtils.toInt(roleId));
                upmsUserRole.setUserRoleId(SequenceUtil.getInt(UpmsUserRole.class));
                result = upmsUserRoleMapper.insertSelective(upmsUserRole);
            }
        }
        return result;
    }

}
