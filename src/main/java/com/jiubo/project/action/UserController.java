package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.github.liangbaika.validate.annations.ValidateParam;
import com.github.liangbaika.validate.annations.ValidateParams;
import com.github.liangbaika.validate.enums.Check;
import com.jiubo.project.bean.UserBean;
import com.jiubo.project.common.Constant;
import com.jiubo.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swd
 * @since 2021-01-19
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/userBean")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取二维码链接")
    @RequestMapping(value = "/getCodeUrl", method = RequestMethod.GET)
    public JSONObject getCodeUrl() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE, Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG, Constant.Result.SUCCESS_MSG);
        jsonObject.put(Constant.Result.RETDATA, userService.getCodeUrl());
        return jsonObject;
    }

    @ApiOperation(value = "通过code查询用户信息")
    @RequestMapping(value = "/getInfoByCode", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionId",value = "后端返回链接的sessionId及state",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code",value = "重定向链接中的code",required = true,dataType = "String",paramType = "query")
    })
    @ValidateParams({
            @ValidateParam(value = Check.NotEmpty, argName = "sessionId", express = "", msg = "sessionId为空或空字符串"),
            @ValidateParam(value = Check.NotEmpty, argName = "code", express = "", msg = "code为空或空字符串")
    })
    public JSONObject getInfoByCode(
            @RequestParam(value = "sessionId") String sessionId,
            @RequestParam(value = "code") String code
    ) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE, Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG, Constant.Result.SUCCESS_MSG);
        jsonObject.put(Constant.Result.RETDATA, userService.getInfoByCode(sessionId,code));
        return jsonObject;
    }

    @ApiOperation(value = "查询是否登录通过sessionId")
    @RequestMapping(value = "/isLogin", method = RequestMethod.GET)
    @ApiImplicitParam(name = "sessionId",value = "后端返回链接的sessionId及state",required = true,dataType = "String",paramType = "query")
    @ValidateParam(value = Check.NotEmpty, argName = "sessionId", express = "", msg = "sessionId为空")
    public JSONObject isLogin(@RequestParam(value = "sessionId") String sessionId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE, Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG, Constant.Result.SUCCESS_MSG);
        jsonObject.put(Constant.Result.RETDATA, userService.isLogin(sessionId));
        return jsonObject;
    }

    @ApiOperation(value = "添加用户信息名称部门等")
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    public JSONObject addUserInfo(@RequestBody UserBean userBean) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE, Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG, Constant.Result.SUCCESS_MSG);
        jsonObject.put(Constant.Result.RETDATA, userService.addUserInfo(userBean));
        return jsonObject;
    }

    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public JSONObject updateUserInfo(@RequestBody UserBean userBean) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE, Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG, Constant.Result.SUCCESS_MSG);
        jsonObject.put(Constant.Result.RETDATA, userService.updateUserInfo(userBean));
        return jsonObject;
    }

}
