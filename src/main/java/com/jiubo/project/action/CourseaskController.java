package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.jiubo.project.bean.CourseaskBean;
import com.jiubo.project.common.Constant;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.CourseaskService;
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
@Api(tags = "评价评分")
@RestController
@RequestMapping("/courseaskBean")
public class CourseaskController {
    @Autowired
    private CourseaskService courseaskService;
    @ApiOperation("新增评价信息")
    @PostMapping("/insertCourseas")
    public JSONObject insertCourseas(@RequestBody CourseaskBean courseaskBean) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        courseaskService.insert(courseaskBean);
        return jsonObject;
    }

    @ApiOperation("查询评分统计，课程名和授课老师必填")
    @PostMapping("/queryCountCourseas")
    public JSONObject queryCountCourseas(@RequestBody CourseaskBean courseaskBean) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        jsonObject.put(Constant.Result.RETDATA,courseaskService.queryCountCourseas(courseaskBean));
        return jsonObject;
    }
}
