package com.hdong.upms.server.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.hdong.upms.dao.model.UpmsRole;
import com.hdong.upms.dao.model.UpmsRoleExample;
import com.hdong.upms.rpc.api.UpmsRolePermissionService;
import com.hdong.upms.rpc.api.UpmsRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色controller
 * Created by hdong on 2017/2/6.
 */
@Controller
@Api(value = "角色管理", description = "角色管理")
@RequestMapping("/manage/role")
public class UpmsRoleController extends BaseController {

    //private static Logger _log = LoggerFactory.getLogger(UpmsRoleController.class);

    @Autowired
    private UpmsRoleService upmsRoleService;

    @Autowired
    private UpmsRolePermissionService upmsRolePermissionService;

    @ApiOperation(value = "角色首页")
    @RequiresPermissions("upms:role:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/role/roleIndex.jsp";
    }

    @ApiOperation(value = "角色权限页面")
    @RequiresPermissions("upms:role:permission")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public String permission(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsRole role = upmsRoleService.selectByPrimaryKey(id);
        modelMap.put("role", role);
        return "/manage/role/rolePermission.jsp";
    }
    
    @ApiOperation(value = "角色权限树")
    @RequiresPermissions("upms:role:permission")
    @RequestMapping(value = "/permissionTree/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray rolePermissionTree(@PathVariable("id") int id) {
        return upmsRolePermissionService.getRolePermissionTreeByRoleId(id);
    }

    @ApiOperation(value = "角色权限保存")
    @RequiresPermissions("upms:role:permission")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult permission(@PathVariable("id") int id, HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int result = upmsRolePermissionService.rolePermissionSave(datas, id);
        return new UpmsResult(UpmsResultConstant.SUCCESS, result);
    }

    @ApiOperation(value = "角色列表")
    @RequiresPermissions("upms:role:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BasePageResult<UpmsRole> list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "name") String name,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UpmsRoleExample upmsRoleExample = new UpmsRoleExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsRoleExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(name)) {
            upmsRoleExample.or().andNameLike("%" + name + "%");
        }
        List<UpmsRole> rows = upmsRoleService.selectByExampleForOffsetPage(upmsRoleExample, offset, limit);
        int total = upmsRoleService.countByExample(upmsRoleExample);
        return new BasePageResult<UpmsRole>(total, rows);
    }

    @ApiOperation(value = "新增角色")
    @RequiresPermissions("upms:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/role/roleCreate.jsp";
    }

    @ApiOperation(value = "新增角色")
    @RequiresPermissions("upms:role:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UpmsResult create(UpmsRole upmsRole) {
        long time = System.currentTimeMillis();
        upmsRole.setCtime(time);
        upmsRole.setOrders(time);
        upmsRole.setRoleId(SequenceUtil.getInt(UpmsRole.class));
        String validStr = ValidatorUtil.validateWithHtml(upmsRole);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        UpmsRoleExample ex = new UpmsRoleExample();
        ex.createCriteria().andNameEqualTo(upmsRole.getName());
        int count = upmsRoleService.countByExample(ex);
        if(count != 0) {
            return new UpmsResult(UpmsResultConstant.FAILED,"该名称已经被使用!");
        }
        count = upmsRoleService.insertSelective(upmsRole);
        return new UpmsResult(count==1);
    }

    @ApiOperation(value = "删除角色")
    @RequiresPermissions("upms:role:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public UpmsResult delete(@PathVariable("ids") String ids) {
        String[] idArr = ids.split("-");
        List<Integer> idList = new ArrayList<Integer>();
        for(String idStr : idArr) {
            idList.add(Integer.parseInt(idStr));
        }
        UpmsRoleExample ex = new UpmsRoleExample();
        ex.createCriteria().andRoleIdIn(idList);
        int count = upmsRoleService.deleteByExample(ex);
        return new UpmsResult(count>=1);
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("upms:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsRole role = upmsRoleService.selectByPrimaryKey(id);
        modelMap.put("role", role);
        return "/manage/role/roleUpdate.jsp";
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("upms:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult update(@PathVariable("id") int id, UpmsRole upmsRole) {
        upmsRole.setRoleId(id);
        String validStr = ValidatorUtil.validateWithHtml(upmsRole);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        UpmsRoleExample ex = new UpmsRoleExample();
        ex.createCriteria().andNameEqualTo(upmsRole.getName()).andRoleIdNotEqualTo(id);
        int count = upmsRoleService.countByExample(ex);
        if(count != 0) {
            return new UpmsResult(UpmsResultConstant.FAILED,"该名称已经被使用!");
        }
        count = upmsRoleService.updateByPrimaryKeySelective(upmsRole);
        return new UpmsResult(count==1);
    }

}
