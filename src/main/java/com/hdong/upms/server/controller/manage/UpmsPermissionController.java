package com.hdong.upms.server.controller.manage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hdong.common.base.BaseController;
import com.hdong.common.base.BasePageResult;
import com.hdong.common.util.SequenceUtil;
import com.hdong.common.util.ValidatorUtil;
import com.hdong.upms.common.constant.UpmsResult;
import com.hdong.upms.common.constant.UpmsResultConstant;
import com.hdong.upms.dao.enums.SystemStatus;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsPermissionExample;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.rpc.api.UpmsPermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限controller
 * Created by hdong on 2017/2/6.
 */
@Controller
@Api(value = "权限管理", description = "权限管理")
@RequestMapping("/manage/permission")
public class UpmsPermissionController extends BaseController {

    //private static Logger _log = LoggerFactory.getLogger(UpmsPermissionController.class);

    @Autowired
    private UpmsPermissionService upmsPermissionService;

    @ApiOperation(value = "权限首页")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/permission/permissionIndex.jsp";
    }

    @ApiOperation(value = "权限列表")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BasePageResult<UpmsPermission> list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "pid") Integer pid,
            @RequestParam(required = false, value = "systemId") Integer systemId,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UpmsPermissionExample upmsPermissionExample = new UpmsPermissionExample();
        UpmsPermissionExample.Criteria criteria = upmsPermissionExample.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (pid!=null) {
            criteria.andPidEqualTo(pid);
        }
        if(systemId !=null) {
            criteria.andSystemIdEqualTo(systemId);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsPermissionExample.setOrderByClause(sort + " " + order);
        }
        List<UpmsPermission> rows = upmsPermissionService.selectByExampleForOffsetPage(upmsPermissionExample, offset, limit);
        int total = upmsPermissionService.countByExample(upmsPermissionExample);
        return new BasePageResult<UpmsPermission>(total, rows);
    }

    @ApiOperation(value = "权限树页面")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String permissionTree(
            @RequestParam(value = "id" ,required=false) String id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "/manage/permission/permissionTree.jsp";
    }
    
    @ApiOperation(value = "权限树数据")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/treeData", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray permissionTreeData() {
        return upmsPermissionService.getPermissionTree();
    }

    @ApiOperation(value = "新增权限")
    @RequiresPermissions("upms:permission:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(
            @RequestParam(value = "pid" ,required=false) String pid, 
            @RequestParam(value = "pname" ,required=false) String pname,
            @RequestParam(value = "systemId" ,required=false) String systemId,
            ModelMap modelMap) {
        modelMap.put("pid", pid);
        modelMap.put("pname", pname);
        modelMap.put("systemId", systemId);
        return "/manage/permission/permissionCreate.jsp";
    }

    @ApiOperation(value = "新增权限")
    @RequiresPermissions("upms:permission:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UpmsResult create(UpmsPermission upmsPermission) {
        long time = System.currentTimeMillis();
        upmsPermission.setCtime(time);
        upmsPermission.setOrders(time);
        upmsPermission.setPermissionId(SequenceUtil.getInt(UpmsPermission.class));
        String validStr = ValidatorUtil.validateWithHtml(upmsPermission);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        //名称是否被使用
        UpmsPermissionExample ex = new UpmsPermissionExample();
        ex.createCriteria().andNameEqualTo(upmsPermission.getName()).andPidEqualTo(upmsPermission.getPid());
        int count = upmsPermissionService.countByExample(ex);
        if(count != 0) {
            return new UpmsResult(UpmsResultConstant.FAILED,"该名称已经被使用!");
        }
        //权限值是否已经被使用
        if(StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
            ex = new UpmsPermissionExample();
            ex.createCriteria().andPermissionValueEqualTo(upmsPermission.getPermissionValue());
            count = upmsPermissionService.countByExample(ex);
            if(count != 0) {
                return new UpmsResult(UpmsResultConstant.FAILED,"该权限值已经被使用!");
            }
        }
        count = upmsPermissionService.insertSelective(upmsPermission);
        return new UpmsResult(count==1);
    }

    @ApiOperation(value = "删除权限")
    @RequiresPermissions("upms:permission:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public UpmsResult delete(@PathVariable("ids") String ids) {
        String[] idArr = ids.split("-");
        List<Integer> idList = new ArrayList<Integer>();
        for(String idStr : idArr) {
            idList.add(Integer.parseInt(idStr));
        }
        int count = upmsPermissionService.deleteByPermissionIds(idList);
        return new UpmsResult(count>=1);
    }

    @ApiOperation(value = "修改权限")
    @RequiresPermissions("upms:permission:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria().andStatusEqualTo(SystemStatus.NORMAL);
        UpmsPermission permission = upmsPermissionService.selectByPrimaryKey(id);
        modelMap.put("permission", permission);
        
        UpmsPermission pOrg = upmsPermissionService.selectByPrimaryKey(permission.getPid());
        modelMap.put("pname", pOrg == null?"":pOrg.getName());
        return "/manage/permission/permissionUpdate.jsp";
    }

    @ApiOperation(value = "修改权限")
    @RequiresPermissions("upms:permission:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult update(@PathVariable("id") int id, UpmsPermission upmsPermission) {
        upmsPermission.setPermissionId(id);
        String validStr = ValidatorUtil.validateWithHtml(upmsPermission);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        //名称是否被使用
        UpmsPermissionExample ex = new UpmsPermissionExample();
        ex.createCriteria().andNameEqualTo(upmsPermission.getName()).andPidEqualTo(upmsPermission.getPid()).andPermissionIdNotEqualTo(id);
        int count = upmsPermissionService.countByExample(ex);
        if(count != 0) {
            return new UpmsResult(UpmsResultConstant.FAILED,"该名称已经被使用!");
        }
        //权限值是否已经被使用
        if(StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
            ex = new UpmsPermissionExample();
            ex.createCriteria().andPermissionValueEqualTo(upmsPermission.getPermissionValue()).andPermissionIdNotEqualTo(id);
            count = upmsPermissionService.countByExample(ex);
            if(count != 0) {
                return new UpmsResult(UpmsResultConstant.FAILED,"该权限值已经被使用!");
            }
        }
        count = upmsPermissionService.updateByPrimaryKeySelective(upmsPermission);
        return new UpmsResult(count==1);
    }

}
