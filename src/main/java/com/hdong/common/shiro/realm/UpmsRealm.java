package com.hdong.common.shiro.realm;

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

import com.hdong.common.util.MD5Util;
import com.hdong.common.util.SpringContextUtil;
import com.hdong.upms.common.constant.UpmsConstant;
import com.hdong.upms.dao.enums.UserLocked;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.rpc.api.UpmsApiService;

/**
 * 用户认证和授权
 * 此处不能用autoWired
 * 因为此处的bean加载一般注解更早，和shiro有关
 * Created by hdong on 2017/1/20.
 */
public class UpmsRealm extends AuthorizingRealm {

    //private static Logger _log = LoggerFactory.getLogger(UpmsRealm.class);
    
    
    /**
     * 授权：验证权限时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        UpmsApiService upmsApiService = SpringContextUtil.getBean(UpmsApiService.class);
        List<Set<String>> setList = upmsApiService.selectRolesPermissionsByNameByCache(username);
        
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
        // 查询用户信息
        UpmsApiService upmsApiService = SpringContextUtil.getBean(UpmsApiService.class);
        UpmsUser upmsUser = upmsApiService.selectUserByUsername(username);

        if (null == upmsUser) {
            throw new UnknownAccountException();
        }
        if (upmsUser.getLocked() == UserLocked.LOCKED) {
            throw new LockedAccountException();
        }
        if ("client".equals(UpmsConstant.UPMS_TYPE)) {
            return new SimpleAuthenticationInfo(username, password, getName());
        }
        if (!upmsUser.getPassword().equals(MD5Util.MD5(password + upmsUser.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, password, upmsUser.getRealname());
    }

}
