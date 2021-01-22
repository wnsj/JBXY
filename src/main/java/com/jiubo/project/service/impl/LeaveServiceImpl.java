package com.jiubo.project.service.impl;

import com.jiubo.project.bean.LeaveBean;
import com.jiubo.project.dao.LeaveDao;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.LeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2021-01-21
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveDao, LeaveBean> implements LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    @Override
    public List<LeaveBean> directoryListing(LeaveBean leaveBean) throws MessageException {
        if (leaveBean.getId() == null) throw new MessageException("目录结构id不可为空！");
        return leaveDao.directoryListing(leaveBean);
    }
}
