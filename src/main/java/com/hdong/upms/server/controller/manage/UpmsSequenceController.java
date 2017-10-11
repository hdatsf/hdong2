package com.hdong.upms.server.controller.manage;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hdong.common.base.BaseController;
import com.hdong.common.util.AESUtil;
import com.hdong.common.util.PropertiesFileUtil;
import com.hdong.common.util.RedisUtil;
import com.hdong.common.util.StringUtil;
import com.hdong.upms.common.constant.UpmsResult;
import com.hdong.upms.common.constant.UpmsResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 序列号controller Created by hdong on 2016/12/18.
 */
@Controller
@Api(value = "序列号管理", description = "序列号管理")
@RequestMapping("/manage/sequence")
public class UpmsSequenceController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsSystemController.class);
    private static final String USERNAME_TITLE = "seq_username";
    private static final String PASSWORD_TITLE = "seq_password";
    
    private static final String USERNAME = PropertiesFileUtil.getInstance("sequence").get(USERNAME_TITLE);
    private static final String PASSWORD = AESUtil.AESDecode(PropertiesFileUtil.getInstance("sequence").get(PASSWORD_TITLE));

    @ApiOperation(value = "重置序列号")
    @RequiresPermissions("upms:system:read")
    @RequestMapping(value = "/reset/{userName}/{password}", method = RequestMethod.GET)
    public Object reset(@PathVariable("userName") String userName, @PathVariable("password") String password) {
        if(!USERNAME.equals(userName)) {
            return new UpmsResult(UpmsResultConstant.FAILED, "用户名错误");
        }
        if(!PASSWORD.equals(password)) {
            return new UpmsResult(UpmsResultConstant.FAILED, "密码错误");
        }
        Map<String,String> properties = PropertiesFileUtil.getInstance("sequence").getMap();
        properties.remove(USERNAME_TITLE);
        properties.remove(PASSWORD_TITLE);
        //遍历map，查看是否缓存中的序列号都小于初始值
        String value, errItem = null;
        for(Entry<String,String> item :properties.entrySet()) {
            value = RedisUtil.get(item.getValue());
            if(StringUtil.getInt(value)> StringUtil.getInt(item.getValue())) {
                errItem = item.getKey()+"="+item.getValue();
                break;
            }
        }
        if(errItem!=null) {
            return new UpmsResult(UpmsResultConstant.FAILED, errItem);
        }
        _log.warn("reset all sequence");
        for(Entry<String,String> item :properties.entrySet()) {
            _log.warn("reset sequence:{}={}", item.getKey(), item.getValue());
            RedisUtil.set(item.getKey(), item.getValue());
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS);
    }
    
    
    @ApiOperation(value = "重置某个序列号")
    @RequiresPermissions("upms:system:read")
    @RequestMapping(value = "/reset/{userName}/{password}/{key}/{value}", method = RequestMethod.GET)
    public Object reset(@PathVariable("userName") String userName, @PathVariable("password") String password,
            @PathVariable("key") String key, @PathVariable("value") int value) {
        if(!USERNAME.equals(userName)) {
            return new UpmsResult(UpmsResultConstant.FAILED, "用户名错误");
        }
        if(!PASSWORD.equals(password)) {
            return new UpmsResult(UpmsResultConstant.FAILED, "密码错误");
        }
        String redisValue = RedisUtil.get(key);
        if(StringUtil.getInt(redisValue)> value) {
            return new UpmsResult(UpmsResultConstant.FAILED, key+"="+redisValue);
        }
        _log.warn("reset sequence:{}={}", key, value);
        return new UpmsResult(UpmsResultConstant.SUCCESS);
    }
    
    
}