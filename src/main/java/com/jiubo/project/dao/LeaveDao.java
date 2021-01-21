package com.jiubo.project.dao;

import com.jiubo.project.bean.LeaveBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-01-21
 */
public interface LeaveDao extends BaseMapper<LeaveBean> {

    List<LeaveBean> directoryListing(LeaveBean leaveBean);
}
