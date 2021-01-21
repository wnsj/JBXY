package com.jiubo.project.service;

import com.jiubo.project.bean.CourseaskBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiubo.project.exception.MessageException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2021-01-21
 */
public interface CourseaskService extends IService<CourseaskBean> {

    void insert(CourseaskBean courseaskBean) throws MessageException;

    CourseaskBean queryCountCourseas(CourseaskBean classInforBean) throws MessageException;
}
