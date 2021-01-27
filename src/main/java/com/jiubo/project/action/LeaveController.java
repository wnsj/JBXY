package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.jiubo.project.bean.LeaveBean;
import com.jiubo.project.common.Constant;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2021-01-21
 */
@Api(tags = "目录结构")
@RestController
@RequestMapping("/leaveBean")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @ApiOperation("目录结构查询")
    @PostMapping("/directoryListing")
    public JSONObject directoryListing(@RequestBody LeaveBean leaveBean) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        jsonObject.put(Constant.Result.RETDATA,leaveService.directoryListing(leaveBean));
        return jsonObject;
    }
}
