package com.hdong.upms.server.controller.manage;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hdong.common.base.BaseController;
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
    
    @ApiOperation(value = "序列号初始化页面")
    @RequiresPermissions("upms:sequence:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "/manage/sequence/sequenceIndex.jsp";
    }

    @ApiOperation(value = "序列号初始化")
    @RequiresPermissions("upms:sequence:reset")
    @RequestMapping(value = "/resetAll", method = RequestMethod.GET)
    @ResponseBody
    public UpmsResult reset() {
        Map<String,String> properties = PropertiesFileUtil.getInstance("sequence").getMap();
        //遍历map，查看是否缓存中的序列号都小于初始值
        String value, errItem = null;
        for(Entry<String,String> item :properties.entrySet()) {
            value = RedisUtil.get(item.getKey());
            if(StringUtil.getInt(value)> StringUtil.getInt(item.getValue())) {
                errItem = item.getKey()+"="+value;
                break;
            }
        }
        if(errItem!=null) {
            return new UpmsResult(UpmsResultConstant.FAILED, "存在序列号大于初始值："+errItem);
        }
        _log.warn("reset all sequence");
        for(Entry<String,String> item :properties.entrySet()) {
            _log.warn("reset sequence:{}={}", item.getKey(), item.getValue());
            RedisUtil.set(item.getKey(), item.getValue());
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS);
    }
    
    
    @ApiOperation(value = "设置某个序列号")
    @RequiresPermissions("upms:sequence:reset")
    @RequestMapping(value = "/reset/{key}/{value}", method = RequestMethod.GET)
    @ResponseBody
    public UpmsResult reset(@PathVariable("key") String key, @PathVariable("value") int value) {
        Map<String,String> properties = PropertiesFileUtil.getInstance("sequence").getMap();
        if(properties.containsKey(key)) {
            String redisValue = RedisUtil.get(key);
            if(StringUtil.getInt(redisValue)> value) {
                return new UpmsResult(UpmsResultConstant.FAILED, "序列号当前值大于设置的值："+key+"="+redisValue);
            }
            _log.warn("reset sequence:{}={}", key, value);
            return new UpmsResult(UpmsResultConstant.SUCCESS);
        }else {
            return new UpmsResult(UpmsResultConstant.FAILED,"不存在该序列号!");
        }
    }
    
    
}