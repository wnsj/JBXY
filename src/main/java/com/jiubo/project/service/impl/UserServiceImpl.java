package com.jiubo.project.service.impl;

import com.jiubo.project.bean.UserBean;
import com.jiubo.project.dao.UserDao;
import com.jiubo.project.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swd
 * @since 2021-01-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserBean> implements UserService {

}
