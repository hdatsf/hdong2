package com.hdong.upms.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hdong.common.base.BaseController;
import com.hdong.upms.dao.model.SysDict;
import com.hdong.upms.dao.model.SysDictExample;
import com.hdong.upms.rpc.api.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 字典项controller Created by hdong on 2017/01/19.
 */
@Controller
@RequestMapping("/sys")
@Api(value = "字典项", description = "字典项")
public class SysDictController extends BaseController {

    //private static Logger _log = LoggerFactory.getLogger(SysDictController.class);
    
    private Map<String, String> dictMap;//{"app-type-name","desc"}
    private Map<String,List<Map<String,String>>> dictArr;//{"app-type",[{name:"name",desc:"desc"}]}

    @Autowired
    private SysDictService sysDictService;
    
    @ApiOperation(value = "获取全部字典项")
    @RequestMapping(value = "/dicts", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> dicts() {
        if(dictMap == null) {
            getDict();
        }
        Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("dictMap", dictMap);
        retMap.put("dictArr", dictArr);
        return retMap;
    }
    /**
     * 
     * @param app
     * @param type
     * @param name
     * @return desc
     */
    public String getDictDesc(String app, String type, String name) {
        if(dictMap == null) {
            getDict();
        }
        return dictMap.get(getName(app, type, name));
    }
    
    /**
     * 
     * @param app
     * @param type
     * @return [{name:"name",desc:"desc"}]
     */
    public List<Map<String,String>> getDictList(String app, String type) {
        if(dictArr == null) {
            getDict();
        }
        return dictArr.get(getName(app, type));
    }
    
    private void getDict() {
        SysDictExample example = new SysDictExample();
        example.setOrderByClause(" dict_app , dict_type, dict_name desc ");
        List<SysDict> dicts = sysDictService.selectByExample(example);
        if(dicts.size()!= 0) {
            //组装 key value，用于找名称{"app-type-name","desc"}
            dictMap = new HashMap<String,String>();
            for(SysDict dict : dicts) {
                dictMap.put(getName(dict.getDictApp(), dict.getDictType(), dict.getDictName()), dict.getDictDesc());
            }
            //组装key list，用于下拉框{"app-type",[{name:"name",desc:"desc"}]}
            dictArr = new HashMap<String,List<Map<String,String>>>();
            List<Map<String,String>> list = new ArrayList<Map<String,String>>();
            Map<String,String> item3;
            String oldKey = null, newKey = null;
            for(SysDict dict : dicts) {
                newKey = getName(dict.getDictApp(), dict.getDictType());
                if(oldKey == null) {
                    oldKey = newKey;
                }
                if(oldKey != newKey) {
                    dictArr.put(oldKey, list);
                    list = new ArrayList<Map<String,String>>();
                    oldKey = newKey;
                }
                item3 = new HashMap<String, String>();
                item3.put("name", dict.getDictName());
                item3.put("desc", dict.getDictDesc());
                list.add(item3);
            }
            if(oldKey!=null) {
                dictArr.put(newKey, list);
            }
        }
    }
    
    private String getName(String app, String type) {
        return app+"-"+type;
    }
    private String getName(String app, String type, String name) {
        return app+"-"+type+"-"+name;
    }

}
