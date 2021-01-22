package com.jiubo.project.service.impl;

import com.jiubo.project.bean.CourseaskBean;
import com.jiubo.project.dao.CourseaskDao;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.CourseaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-01-21
 */
@Service
public class CourseaskServiceImpl extends ServiceImpl<CourseaskDao, CourseaskBean> implements CourseaskService {
    @Autowired
    private CourseaskDao courseaskDao;

    @Override
    public void insert(CourseaskBean courseaskBean) throws MessageException {
        if (courseaskBean == null ) throw new MessageException("新增评价信息不可为空！");
        courseaskDao.insert(courseaskBean);
    }

    @Override
    public CourseaskBean queryCountCourseas(CourseaskBean courseaskBean) throws MessageException {
        if (courseaskBean == null) throw new MessageException("查询课程不能为空！");
        if (courseaskBean.getCourseteacher() == null && courseaskBean.getCourseteacher().equals("")) throw new MessageException("授课老师不可为空！");
        if (courseaskBean.getCoursetitle() == null && courseaskBean.getCoursetitle().equals("")) throw new MessageException("课程名称不能为空！");
        CourseaskBean result = courseaskDao.queryCountCourseas(courseaskBean);
        return result;
    }
}
