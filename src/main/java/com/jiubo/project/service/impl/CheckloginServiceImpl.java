package com.jiubo.project.service.impl;

import com.jiubo.project.bean.CheckloginBean;
import com.jiubo.project.dao.CheckloginDao;
import com.jiubo.project.service.CheckloginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-01-22
 */
@Service
public class CheckloginServiceImpl extends ServiceImpl<CheckloginDao, CheckloginBean> implements CheckloginService {

    @Autowired
    private CheckloginDao checkloginDao;

    @Override
    public Object departmentFind(CheckloginBean checkloginBean) {
        return checkloginDao.departmentFind(checkloginBean);
    }
}
