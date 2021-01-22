package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.jiubo.project.bean.CheckloginBean;
import com.jiubo.project.bean.LeaveBean;
import com.jiubo.project.common.Constant;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.CheckloginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2021-01-22
 */
@Api(tags = "公司部门")
@RestController
@RequestMapping("/checkloginBean")
public class CheckloginController {

    @Autowired
    private CheckloginService checkloginService;

    @ApiOperation("公司部门结构查询")
    @RequestMapping(value = "departmentFind", method = RequestMethod.POST)
    public JSONObject departmentFind(@RequestBody CheckloginBean checkloginBean) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        jsonObject.put(Constant.Result.RETDATA,checkloginService.departmentFind(checkloginBean));
        return jsonObject;
    }

}
