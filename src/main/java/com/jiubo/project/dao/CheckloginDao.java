package com.jiubo.project.dao;

import com.jiubo.project.bean.CheckloginBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2021-01-22
 */
public interface CheckloginDao extends BaseMapper<CheckloginBean> {

    List<CheckloginBean> departmentFind(CheckloginBean checkloginBean);
}
