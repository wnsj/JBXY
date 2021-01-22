package com.jiubo.project.service;

import com.jiubo.project.bean.CheckloginBean;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2021-01-22
 */
public interface CheckloginService extends IService<CheckloginBean> {

    Object departmentFind(CheckloginBean checkloginBean);
}
