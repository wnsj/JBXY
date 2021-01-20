package com.jiubo.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiubo.project.bean.ClassInforBean;
import com.jiubo.project.dao.ClassInforDao;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.ClassInforService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swd
 * @since 2021-01-20
 */
@Service
public class ClassInforServiceImpl extends ServiceImpl<ClassInforDao, ClassInforBean> implements ClassInforService {

    @Autowired
    private ClassInforDao classInforDao;

    @Override
    public List<ClassInforBean> courseQueriesByLeavetwo(ClassInforBean classInforBean) throws MessageException {
        Map<String, Object> map = new HashMap<>();
        String leavetwo = classInforBean.getLeavetwo();
        if (leavetwo == null && leavetwo.equals("")) throw new MessageException("课程编码不可为空！");
        return classInforDao.courseQueriesByLeavetwo(classInforBean);
    }
}
