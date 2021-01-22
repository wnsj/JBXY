package com.jiubo.project.service;

import com.jiubo.project.bean.UserBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiubo.project.exception.MessageException;
import io.swagger.models.auth.In;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swd
 * @since 2021-01-19
 */
public interface UserService extends IService<UserBean> {

    Map<String,Object> getCodeUrl() throws UnsupportedEncodingException;

    Map<String,Object> getInfoByCode(String sessionId, String code) throws MessageException;

    UserBean isLogin(String sessionId);

    Integer addUserInfo(UserBean userBean) throws MessageException;

    Integer updateUserInfo(UserBean userBean) throws MessageException;
}
