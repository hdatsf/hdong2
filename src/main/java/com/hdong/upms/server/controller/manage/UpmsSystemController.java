package com.hdong.upms.server.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hdong.common.base.BaseController;
import com.hdong.common.util.SequenceUtil;
import com.hdong.common.util.ValidatorUtil;
import com.hdong.upms.common.constant.UpmsResult;
import com.hdong.upms.common.constant.UpmsResultConstant;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.rpc.api.UpmsSystemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统controller Created by hdong on 2016/12/18.
 */
@Controller
@Api(value = "系统管理", description = "系统管理")
@RequestMapping("/manage/system")
public class UpmsSystemController extends BaseController {

    // private static Logger _log = LoggerFactory.getLogger(UpmsSystemController.class);

    @Autowired
    private UpmsSystemService upmsSystemService;

    @ApiOperation(value = "系统首页")
    @RequiresPermissions("upms:system:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/system/systemIndex.jsp";
    }

    @ApiOperation(value = "系统列表")
    @RequiresPermissions("upms:system:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "systemName") String systemName,
            @RequestParam(required = false, value = "sort") String sort, @RequestParam(required = false, value = "order") String order) {
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        
        if (StringUtils.isNotBlank(systemName)) {
            upmsSystemExample.or().andTitleLike("%" + systemName + "%");
        }
        long total = upmsSystemService.countByExample(upmsSystemExample);
        
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsSystemExample.setOrderByClause(sort + " " + order);
        }
        List<UpmsSystem> rows = upmsSystemService.selectByExampleForOffsetPage(upmsSystemExample, offset, limit);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增系统")
    @RequiresPermissions("upms:system:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/system/systemCreate.jsp";
    }

    @ApiOperation(value = "新增系统")
    @RequiresPermissions("upms:system:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object create(UpmsSystem upmsSystem) {
        long time = System.currentTimeMillis();
        upmsSystem.setCtime(time);
        upmsSystem.setOrders(time);
        upmsSystem.setSystemId(SequenceUtil.getInt(UpmsSystem.class));
        String validStr = ValidatorUtil.validateWithHtml(upmsSystem);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        int count = upmsSystemService.insertSelective(upmsSystem);
        if(count ==1) {
            return new UpmsResult(UpmsResultConstant.SUCCESS);
        }else {
            return new UpmsResult(UpmsResultConstant.FAILED);
        }
    }

    @ApiOperation(value = "删除系统")
    @RequiresPermissions("upms:system:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public Object delete(@PathVariable("ids") String ids) {
        int count = upmsSystemService.deleteByPrimaryKeys(ids);
        if(count ==1) {
            return new UpmsResult(UpmsResultConstant.SUCCESS);
        }else {
            return new UpmsResult(UpmsResultConstant.FAILED);
        }
    }

    @ApiOperation(value = "修改系统")
    @RequiresPermissions("upms:system:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsSystem system = upmsSystemService.selectByPrimaryKey(id);
        modelMap.put("system", system);
        return "/manage/system/systemUpdate.jsp";
    }

    @ApiOperation(value = "修改系统")
    @RequiresPermissions("upms:system:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object update(@PathVariable("id") int id, UpmsSystem upmsSystem) {
        upmsSystem.setSystemId(id);
        String validStr = ValidatorUtil.validateWithHtml(upmsSystem);
        if(StringUtils.isNotBlank(validStr)) {
            return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
        }
        int count = upmsSystemService.updateByPrimaryKeySelective(upmsSystem);
        if(count ==1) {
            return new UpmsResult(UpmsResultConstant.SUCCESS);
        }else {
            return new UpmsResult(UpmsResultConstant.FAILED);
        }
    }

}
