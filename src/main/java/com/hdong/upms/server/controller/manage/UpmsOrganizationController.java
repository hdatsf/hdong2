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
import com.hdong.upms.dao.model.UpmsOrganization;
import com.hdong.upms.dao.model.UpmsOrganizationExample;
import com.hdong.upms.dao.model.UpmsOrganizationExample.Criteria;
import com.hdong.upms.rpc.api.UpmsOrganizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 组织controller
 * Created by hdong on 2017/2/6.
 */
@Controller
@Api(value = "组织管理", description = "组织管理")
@RequestMapping("/manage/organization")
public class UpmsOrganizationController extends BaseController {

    //private static Logger _log = LoggerFactory.getLogger(UpmsOrganizationController.class);

    @Autowired
    private UpmsOrganizationService upmsOrganizationService;

    @ApiOperation(value = "组织首页")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "/manage/organization/organizationIndex.jsp";
    }
    
    @ApiOperation(value = "组织列表")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BasePageResult<UpmsOrganization> list(
            @RequestParam(required = true, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = true, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "pid") Integer pid,
            @RequestParam(required = false, defaultValue = "organization_id", value = "sort") String sort,
            @RequestParam(required = false, defaultValue = "asc", value = "order") String order) {
        UpmsOrganizationExample upmsOrganizationExample = new UpmsOrganizationExample();
        upmsOrganizationExample.setOrderByClause(sort + " " + order);
        Criteria criteria = upmsOrganizationExample.createCriteria();
        if(pid != null) {
            criteria.andPidEqualTo(pid);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        List<UpmsOrganization> rows = upmsOrganizationService.selectByExampleForOffsetPage(upmsOrganizationExample, offset, limit);
        int total = upmsOrganizationService.countByExample(upmsOrganizationExample);
        return new BasePageResult<UpmsOrganization>(total, rows);
    }

    @ApiOperation(value = "新增组织")
    @RequiresPermissions("upms:organization:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(
            @RequestParam(value = "pid" ,required=false) String pid, 
            @RequestParam(value = "pname" ,required=false) String pname, ModelMap modelMap) {
        modelMap.put("pid", pid);
        modelMap.put("pname", pname);
        return "/manage/organization/organizationCreate.jsp";
    }
    
    @ApiOperation(value = "获取组织树")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/treeData", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray organizationTree() {
        return upmsOrganizationService.getOrgTree();
    }
    
    @ApiOperation(value = "组织树")
    @RequiresPermissions("upms:organization:read")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String tree(
            @RequestParam(value = "id" ,required=false) String id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "/manage/organization/organizationTree.jsp";
    }

    @ApiOperation(value = "新增组织")
    @RequiresPermissions("upms:organization:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UpmsResult create(UpmsOrganization upmsOrganization) {
        long time = System.currentTimeMillis();
        upmsOrganization.setCtime(time);
        upmsOrganization.setOrganizationId(SequenceUtil.getInt(UpmsOrganization.class));
        String validStr = ValidatorUtil.validateWithHtml(upmsOrganization);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        UpmsOrganizationExample ex = new UpmsOrganizationExample();
        ex.createCriteria().andNameEqualTo(upmsOrganization.getName()).andPidEqualTo(upmsOrganization.getPid());
        int count = upmsOrganizationService.countByExample(ex);
        if(count != 0) {
            return new UpmsResult(UpmsResultConstant.FAILED,"该名称已经被使用!");
        }
        count = upmsOrganizationService.insertSelective(upmsOrganization);
        return new UpmsResult(count==1);
    }

    @ApiOperation(value = "删除组织")
    @RequiresPermissions("upms:organization:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public UpmsResult delete(@PathVariable("ids") String ids) {
        String[] idArr = ids.split("-");
        List<Integer> idList = new ArrayList<Integer>();
        for(String idStr : idArr) {
            idList.add(Integer.parseInt(idStr));
        }
        UpmsOrganizationExample ex = new UpmsOrganizationExample();
        ex.createCriteria().andOrganizationIdIn(idList);
        int count = upmsOrganizationService.deleteByExample(ex);
        return new UpmsResult(count>=1);
    }

    @ApiOperation(value = "修改组织")
    @RequiresPermissions("upms:organization:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsOrganization organization = upmsOrganizationService.selectByPrimaryKey(id);
        modelMap.put("organization", organization);
        UpmsOrganization pOrg = upmsOrganizationService.selectByPrimaryKey(organization.getPid());
        modelMap.put("pname", pOrg == null?"":pOrg.getName());
        return "/manage/organization/organizationUpdate.jsp";
    }

    @ApiOperation(value = "修改组织")
    @RequiresPermissions("upms:organization:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult update(@PathVariable("id") int id, UpmsOrganization upmsOrganization) {
        upmsOrganization.setOrganizationId(id);
        String validStr = ValidatorUtil.validateWithHtml(upmsOrganization);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        //查找是否有同名的
        UpmsOrganizationExample ex = new UpmsOrganizationExample();
        ex.createCriteria().andOrganizationIdNotEqualTo(id).andPidEqualTo(upmsOrganization.getPid()).andNameEqualTo(upmsOrganization.getName());
        int count = upmsOrganizationService.countByExample(ex);
        if(count != 0) {
            return new UpmsResult(UpmsResultConstant.FAILED,"该名称已经被使用!");
        }
        count = upmsOrganizationService.updateByPrimaryKeySelective(upmsOrganization);
        return new UpmsResult(count==1);
    }

}
