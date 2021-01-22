package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.jiubo.project.common.Constant;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.ClassInforService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swd
 * @since 2021-01-20
 */
@Api(tags = "课程类型")
@RestController
@RequestMapping("/classInforBean")
public class ClassInforController {
    @Autowired
    private ClassInforService classInforService;

    /*
     * @param leavetwo:
     * @return: com.alibaba.fastjson.JSONObject
     * @AUTHOR: wuxiaoguang
     * @DATE: 2021/1/20 9:43
     * @DESCRIPTION:查询课程
     */
    @PostMapping("/courseQueries")
    @ApiOperation("课程查询")
    @ApiImplicitParams({ @ApiImplicitParam(name = "leavetwo", value = "课程编号", required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间",dataType = "LocalDate", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "LocalDate", paramType = "query"),
            @ApiImplicitParam(name = "department", value = "部门",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "授课老师",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码",dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "条数",dataType = "Integer", paramType = "query")})
    public JSONObject courseQueries(@RequestParam String leavetwo,
                                    @RequestParam Date startTime,
                                    @RequestParam Date endTime,
                                    @RequestParam String department,
                                    @RequestParam String name,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer pageSize) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        jsonObject.put(Constant.Result.RETDATA,classInforService.courseQueriesByLeavetwo(leavetwo,startTime,endTime,department,name,page,pageSize));
        return jsonObject;
    }
    /**
     * @param id:
     * @return: com.alibaba.fastjson.JSONObject
     * @AUTHOR: wuxiaoguang
     * @DATE: 2021/1/20 15:56
     * @DESCRIPTION: 视频观看校验
     */
    @PostMapping("/checkedVideo")
    @ApiOperation("视频观看校验")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "课程id",required = true,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "classPwd", value = "课程密码",required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "openid", value = "微信登录生成码",required = true,dataType = "String", paramType = "query")})
    public JSONObject checkedVideo(@RequestParam Integer id,@RequestParam String classPwd,@RequestParam String openid) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.RETCODE);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        jsonObject.put(Constant.Result.RETDATA,classInforService.checkedVideo(id,classPwd,openid));
        return jsonObject;
    }
}
