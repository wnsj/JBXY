package com.jiubo.project.service;

import com.github.pagehelper.PageInfo;
import com.jiubo.project.bean.ClassInforBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiubo.project.exception.MessageException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swd
 * @since 2021-01-20
 */
public interface ClassInforService extends IService<ClassInforBean> {

    /*
     * @param leavetwo:
     * @return: java.util.List<com.jiubo.project.bean.ClassInforBean>
     * @AUTHOR: wuxiaoguang
     * @DATE: 2021/1/20 9:42
     * @DESCRIPTION:课程查询
     */
    public PageInfo<ClassInforBean> courseQueriesByLeavetwo(String leavetwo,
                                                            Date startTime,
                                                            Date endTime,
                                                            String department,
                                                            String name, Integer page, Integer pageSize) throws MessageException;

    Map<String, Object> checkedVideo(Integer id, String classPwd, String openid) throws MessageException;


    String test();
}
