package com.hdong.upms.common.constant;

import com.hdong.common.base.BaseConstants;
import com.hdong.common.util.PropertiesFileUtil;

/**
 * upms系统常量类
 * Created by hdong on 2017/2/18.
 */
public class UpmsConstant extends BaseConstants {
    
    private static final String SYSTEM_NAME = PropertiesFileUtil.getInstance().get("system.name");

    public static final String UPMS_TYPE = PropertiesFileUtil.getInstance().get("hdong.upms.type");
    
    // 会话key
    public final static String SHIRO_SESSION_ID = SYSTEM_NAME + "-shiro-session-id";
    // 全局会话key
    public final static String SERVER_SESSION_ID = SYSTEM_NAME + "-server-session-id";
    // 全局会话列表key
    public final static String SERVER_SESSION_IDS = SYSTEM_NAME + "-server-session-ids";
    // code key
    public final static String SERVER_CODE = SYSTEM_NAME + "-server-code";
    // 局部会话key
    public final static String CLIENT_SESSION_ID = SYSTEM_NAME + "-client-session-id";
    // 单点同一个code所有局部会话key
    public final static String CLIENT_SESSION_IDS = SYSTEM_NAME + "-client-session-ids";
    
    public final static String FORCE_LOGOUT = "FORCE_LOGOUT";

}
