package com.hdong.upms.client.shiro.realm;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hdong.common.util.MD5Util;
import com.hdong.common.util.PropertiesFileUtil;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.rpc.api.UpmsApiService;
import com.hdong.upms.rpc.api.UpmsUserService;

/**
 * 用户认证和授权
 * Created by hdong on 2017/1/20.
 */
public class UpmsRealm extends AuthorizingRealm {

    //private static Logger _log = LoggerFactory.getLogger(UpmsRealm.class);

    @Autowired
    private UpmsApiService upmsApiService;
    
    @Autowired
    private UpmsUserService upmsUserService;
    
    private static final String SYSTEM_NAME = PropertiesFileUtil.getInstance().get("system.name");

    /**
     * 授权：验证权限时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        List<Set<String>> setList = upmsApiService.selectRolesPermissionsByName(username, SYSTEM_NAME);
        
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(setList !=null && setList.size()==2) {
            simpleAuthorizationInfo.setRoles(setList.get(0));
            simpleAuthorizationInfo.setStringPermissions(setList.get(1));
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证：登录时调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        // client无密认证
        String upmsType = PropertiesFileUtil.getInstance().get("hdong.upms.type");
        if ("client".equals(upmsType)) {
            return new SimpleAuthenticationInfo(username, password, getName());
        }

        // 查询用户信息
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser = upmsUserService.selectFirstByExample(userExample);

        if (null == upmsUser) {
            throw new UnknownAccountException();
        }
        if (!upmsUser.getPassword().equals(MD5Util.MD5(password + upmsUser.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
        if (upmsUser.getLocked() == 1) {
            throw new LockedAccountException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
