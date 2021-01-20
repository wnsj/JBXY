package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.jiubo.project.bean.ClassInforBean;
import com.jiubo.project.common.Constant;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.ClassInforService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    @ApiImplicitParam(name = "leavetwo", value = "课程类型",required=true,dataType = "String", paramType = "query")
    public JSONObject courseQueries(@RequestBody ClassInforBean classInforBean) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.RETCODE);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        jsonObject.put(Constant.Result.RETDATA,classInforService.courseQueriesByLeavetwo(classInforBean));
        return jsonObject;
    }
}
