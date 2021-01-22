package com.jiubo.project.service;

import com.jiubo.project.bean.LeaveBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiubo.project.exception.MessageException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2021-01-21
 */
public interface LeaveService extends IService<LeaveBean> {

    List<LeaveBean> directoryListing(LeaveBean leaveBean) throws MessageException;
}
