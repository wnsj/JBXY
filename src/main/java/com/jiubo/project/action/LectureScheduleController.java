package com.jiubo.project.action;


import com.alibaba.fastjson.JSONObject;
import com.jiubo.project.bean.LectureScheduleBean;
import com.jiubo.project.common.Constant;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.LectureScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swd
 * @since 2021-01-21
 */
@Api(tags = "观看记录")
@RestController
@RequestMapping("/lectureScheduleBean")
public class LectureScheduleController {

    @Autowired
    private LectureScheduleService lectureScheduleService;

    /**
     * @param lectureScheduleBean:
     * @return: com.alibaba.fastjson.JSONObject
     * @AUTHOR: wuxiaoguang
     * @DATE: 2021/1/21 13:39
     * @DESCRIPTION:完整观看视频后，对观看记录进行更新
     */
    @ApiOperation("观看记录更新，更新内容都要必填")
    @PostMapping("/updateLectureScheduleBean")
    public JSONObject updateLectureScheduleBean(@RequestBody LectureScheduleBean lectureScheduleBean) throws MessageException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.Result.RETCODE,Constant.Result.SUCCESS);
        jsonObject.put(Constant.Result.RETMSG,Constant.Result.RETMSG);
        if(lectureScheduleBean.getId() == null) throw new MessageException("观看视频记录的ID不可为空");
        lectureScheduleService.updateById(lectureScheduleBean);
        return jsonObject;
    }
}
