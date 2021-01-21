package com.jiubo.project.dao;

import com.jiubo.project.bean.ClassInforBean;
import com.jiubo.project.bean.CourseaskBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2021-01-21
 */
public interface CourseaskDao extends BaseMapper<CourseaskBean> {
    CourseaskBean queryCountCourseas(CourseaskBean courseaskBean);
}
