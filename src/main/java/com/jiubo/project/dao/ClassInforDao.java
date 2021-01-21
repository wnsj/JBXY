package com.jiubo.project.dao;

import com.jiubo.project.bean.ClassInforBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author swd
 * @since 2021-01-20
 */
public interface ClassInforDao extends BaseMapper<ClassInforBean> {

    List<ClassInforBean> courseQueriesByLeavetwo(String leavetwo,
                                                 Date startTime,
                                                 Date endTime,
                                                 String department,
                                                 String name);
}
