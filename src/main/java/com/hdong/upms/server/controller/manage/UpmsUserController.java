package com.hdong.upms.server.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hdong.common.util.MD5Util;
import com.hdong.common.util.SequenceUtil;
import com.hdong.common.util.ValidatorUtil;
import com.hdong.upms.common.constant.UpmsResult;
import com.hdong.upms.common.constant.UpmsResultConstant;
import com.hdong.upms.dao.enums.UserPermissionType;
import com.hdong.upms.dao.model.UpmsRole;
import com.hdong.upms.dao.model.UpmsRoleExample;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.dao.model.UpmsUserExample.Criteria;
import com.hdong.upms.dao.model.UpmsUserOrganization;
import com.hdong.upms.dao.model.UpmsUserOrganizationExample;
import com.hdong.upms.dao.model.UpmsUserRole;
import com.hdong.upms.dao.model.UpmsUserRoleExample;
import com.hdong.upms.rpc.api.UpmsOrganizationService;
import com.hdong.upms.rpc.api.UpmsRoleService;
import com.hdong.upms.rpc.api.UpmsUserOrganizationService;
import com.hdong.upms.rpc.api.UpmsUserPermissionService;
import com.hdong.upms.rpc.api.UpmsUserRoleService;
import com.hdong.upms.rpc.api.UpmsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户controller Created by hdong on 2017/2/6.
 */
@Controller
@Api(value = "用户管理", description = "用户管理")
@RequestMapping("/manage/user")
public class UpmsUserController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserController.class);

    @Autowired
    private UpmsUserService upmsUserService;

    @Autowired
    private UpmsRoleService upmsRoleService;

    @Autowired
    private UpmsOrganizationService upmsOrganizationService;

    @Autowired
    private UpmsUserOrganizationService upmsUserOrganizationService;

    @Autowired
    private UpmsUserPermissionService upmsUserPermissionService;

    @Autowired
    private UpmsUserRoleService upmsUserRoleService;

    @ApiOperation(value = "用户首页")
    @RequiresPermissions("upms:user:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/user/userIndex.jsp";
    }

    @ApiOperation(value = "用户组织页面")
    @RequiresPermissions("upms:user:organization")
    @RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
    public String organization(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.put("userId", id);
        return "/manage/user/userOrganization.jsp";
    }

    @ApiOperation(value = "用户组织树")
    @RequiresPermissions("upms:user:organization")
    @RequestMapping(value = "/organizationTree/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray organizationTreeData(@PathVariable("id") int id) {
        return upmsOrganizationService.getUserOrgTreeByUserId(id);
    }

    @ApiOperation(value = "用户组织保存")
    @RequiresPermissions("upms:user:organization")
    @RequestMapping(value = "/organization/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult organizationTreeDataSave(@PathVariable("id") int id, HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int result = upmsUserOrganizationService.userOrgizationSave(datas, id);
        return new UpmsResult(UpmsResultConstant.SUCCESS, result);
    }
    
    @ApiOperation(value = "用户权限页面")
    @RequiresPermissions("upms:user:permission")
    @RequestMapping(value = "/permission/{type}/{id}", method = RequestMethod.GET)
    public String subPermission(@PathVariable("id") int id, @PathVariable("type") UserPermissionType type,
            ModelMap modelMap) {
        modelMap.put("userId", id);
        modelMap.put("type", type);
        return "/manage/user/userPermission.jsp";
    }

    @ApiOperation(value = "用户权限树")
    @RequiresPermissions("upms:user:permission")
    @RequestMapping(value = "/permissionTree/{type}/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray permissionAddTreeData(@PathVariable("id") int id, @PathVariable("type") UserPermissionType type) {
        return upmsUserPermissionService.getUserPermissionTreeByUserId(id, type);
    }

    @ApiOperation(value = "用户权限保存")
    @RequiresPermissions("upms:user:permission")
    @RequestMapping(value = "/permission/{type}/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult permissionTreeDataSave(@PathVariable("id") int id, @PathVariable("type") UserPermissionType userPermissionType,
            HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int result = upmsUserPermissionService.userPermissionSave(datas, id, userPermissionType);
        return new UpmsResult(UpmsResultConstant.SUCCESS, result);
    }


    @ApiOperation(value = "用户角色")
    @RequiresPermissions("upms:user:role")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public String role(@PathVariable("id") int id, ModelMap modelMap) {
        // 所有角色
        List<UpmsRole> upmsRoles = upmsRoleService.selectByExample(new UpmsRoleExample());
        // 用户拥有角色
        UpmsUserRoleExample upmsUserRoleExample = new UpmsUserRoleExample();
        upmsUserRoleExample.createCriteria().andUserIdEqualTo(id);
        List<UpmsUserRole> upmsUserRoles = upmsUserRoleService.selectByExample(upmsUserRoleExample);
        
        List<HashMap<String,Object>> nodes = new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> node;
        HashSet<Integer> hasRoles = new HashSet<Integer>();
        for(UpmsUserRole userRole : upmsUserRoles) {
            hasRoles.add(userRole.getRoleId());
        }
        for(UpmsRole role : upmsRoles) {
            node = new HashMap<String,Object>();
            node.put("id", role.getRoleId());
            node.put("name", role.getName());
            if(hasRoles.contains(role.getRoleId())) {
                node.put("selected", "selected=\"selected\"");
            }else {
                node.put("selected", "");
            }
            nodes.add(node);
        }
        modelMap.put("userId", id);
        modelMap.put("upmsRoles", nodes);
        return "/manage/user/userRole.jsp";
    }

    @ApiOperation(value = "用户角色")
    @RequiresPermissions("upms:user:role")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult role(@PathVariable("id") int id, HttpServletRequest request) {
        String[] roleIds = request.getParameterValues("roleId");
        int count =upmsUserRoleService.role(roleIds, id);
        return new UpmsResult(count>0);
    }

    @ApiOperation(value = "用户列表")
    @RequiresPermissions("upms:user:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BasePageResult<UpmsUser> list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "organizationId") Integer organizationId,
            @RequestParam(required = false, value = "sort") String sort, @RequestParam(required = false, value = "order") String order) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsUserExample.setOrderByClause(sort + " " + order);
        }
        Criteria criteria = upmsUserExample.createCriteria();
        if (organizationId != null) {
            UpmsUserOrganizationExample ex = new UpmsUserOrganizationExample();
            ex.createCriteria().andOrganizationIdEqualTo(organizationId);
            List<UpmsUserOrganization> orgList = upmsUserOrganizationService.selectByExample(ex);
            List<Integer> userIdList = new ArrayList<Integer>();
            for (UpmsUserOrganization item : orgList) {
                userIdList.add(item.getUserId());
            }
            if (userIdList.size() == 0) {
                criteria.andUserIdIsNull();
            } else {
                criteria.andUserIdIn(userIdList);
            }
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andRealnameLike("%" + name + "%");
        }
        List<UpmsUser> rows = upmsUserService.selectByExampleForOffsetPage(upmsUserExample, offset, limit);
        int total = upmsUserService.countByExample(upmsUserExample);
        return new BasePageResult<UpmsUser>(total, rows);
    }

    @ApiOperation(value = "新增用户")
    @RequiresPermissions("upms:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(value = "organizationId", required = false) String organizationId,
            @RequestParam(value = "organizationName", required = false) String organizationName, ModelMap modelMap) {
        modelMap.put("organizationId", organizationId);
        modelMap.put("organizationName", organizationName);
        return "/manage/user/userCreate.jsp";
    }

    @ApiOperation(value = "新增用户")
    @RequiresPermissions("upms:user:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UpmsUser upmsUser, Integer organizationId) {
        long time = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        upmsUser.setSalt(salt);
        upmsUser.setPassword(MD5Util.MD5(upmsUser.getPassword() + upmsUser.getSalt()));
        upmsUser.setCtime(time);
        upmsUser.setUserId(SequenceUtil.getInt(UpmsUser.class));
        String validStr = ValidatorUtil.validateWithHtml(upmsUser);
        if (StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        upmsUser = upmsUserService.createUser(upmsUser, organizationId);
        if (null == upmsUser) {
            return new UpmsResult(UpmsResultConstant.FAILED, "帐号已存在！");
        }
        _log.info("新增用户，主键：userId={}", upmsUser.getUserId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除用户")
    @RequiresPermissions("upms:user:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        String[] idArr = ids.split("-");
        List<Integer> idList = new ArrayList<Integer>();
        for (String idStr : idArr) {
            idList.add(Integer.parseInt(idStr));
        }
        int count = upmsUserService.deleteUser(idList);
        return new UpmsResult(count > 0);
    }

    @ApiOperation(value = "修改用户")
    @RequiresPermissions("upms:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsUser user = upmsUserService.selectByPrimaryKey(id);
        modelMap.put("user", user);
        return "/manage/user/userUpdate.jsp";
    }

    @ApiOperation(value = "修改用户")
    @RequiresPermissions("upms:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UpmsUser upmsUser) {
        upmsUser.setUserId(id);
        String validStr = ValidatorUtil.validateWithHtml(upmsUser);
        if (StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        // 不允许直接改密码
        upmsUser.setPassword(null);
        int count = upmsUserService.updateByPrimaryKeySelective(upmsUser);
        return new UpmsResult(count == 1);
    }
    
    @ApiOperation(value = "修改自身信息")
    @RequestMapping(value = "/updateSelf", method = RequestMethod.GET)
    public String updateSelf(ModelMap modelMap) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample ex = new UpmsUserExample();
        ex.createCriteria().andUsernameEqualTo(username);
        UpmsUser user = upmsUserService.selectFirstByExample(ex);
        modelMap.put("user", user);
        return "/manage/user/userUpdateSelf.jsp";
    }

    @ApiOperation(value = "修改自身信息")
    @RequestMapping(value = "/updateSelf", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSelf(UpmsUser upmsUser) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample ex = new UpmsUserExample();
        ex.createCriteria().andUsernameEqualTo(username);
        UpmsUser user = upmsUserService.selectFirstByExample(ex);
        if(user == null) {
            return new UpmsResult(UpmsResultConstant.USER_NOEXIST);
        }
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        upmsUser.setSalt(salt);
        upmsUser.setPassword(MD5Util.MD5(upmsUser.getPassword() + upmsUser.getSalt()));
        upmsUser.setUserId(user.getUserId());
        String validStr = ValidatorUtil.validateWithHtml(upmsUser);
        if (StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        int count = upmsUserService.updateByPrimaryKeySelective(upmsUser);
        return new UpmsResult(count == 1);
    }

}
