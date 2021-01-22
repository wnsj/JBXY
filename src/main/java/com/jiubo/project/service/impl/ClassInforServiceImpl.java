package com.jiubo.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiubo.project.bean.ClassInforBean;
import com.jiubo.project.bean.LectureScheduleBean;
import com.jiubo.project.bean.PasswordBean;
import com.jiubo.project.bean.UserBean;
import com.jiubo.project.dao.ClassInforDao;
import com.jiubo.project.dao.LectureScheduleDao;
import com.jiubo.project.dao.PasswordDao;
import com.jiubo.project.dao.UserDao;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.ClassInforService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordDao passwordDao;

    @Autowired
    private LectureScheduleDao lectureScheduleDao;

    @Override
    public PageInfo<ClassInforBean> courseQueriesByLeavetwo(String leavetwo,
                                                            Date startTime,
                                                            Date endTime,
                                                            String department,
                                                            String name, Integer page, Integer pageSize) throws MessageException {
        if (leavetwo == null && leavetwo.equals("")) throw new MessageException("课程编码不可为空！");
        page = page == null ? 1:page;
        pageSize = pageSize == null ? 10:pageSize;
        PageHelper.startPage(page,pageSize);
        List<ClassInforBean> list = classInforDao.courseQueriesByLeavetwo(leavetwo,startTime,endTime,department,name);
        PageInfo<ClassInforBean> result = new PageInfo<>(list);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> checkedVideo(Integer id, String classPwd, String openid) throws MessageException {
        if(id == null) throw new MessageException("课程ID不可为空！");
        if (classPwd == null && classPwd.equals("")) throw new MessageException("观看视频密码不可为空！");
        if (openid == null && openid.equals("")) throw new MessageException("用微信登录码不可为空！");
        ClassInforBean classInforBean = classInforDao.selectById(id);
        QueryWrapper<UserBean> isUser = new QueryWrapper<>();
        isUser.eq("openid",openid);
        UserBean userBean = userDao.selectOne(isUser);
        if (userBean == null) throw new MessageException("数据库没有查询到相应的用户！");
        //查询密码
        QueryWrapper<PasswordBean> passwordBean= new QueryWrapper<>();
        passwordBean.eq("classid",id).eq("classPwd",classPwd);
        List<PasswordBean> passwordBeans = passwordDao.selectList(passwordBean);
        if (passwordBeans.size() > 1) throw new MessageException("数据出现重复数据!");
        PasswordBean password = passwordBeans.get(0);
        //判断视频的观看次数
        if (password.getJudge() >= password.getUpperlimit()) throw new MessageException("该视频已达到最大观看次数！");
        //更新观看次数
        password.setJudge(password.getJudge()+1);
        int updateById = passwordDao.updateById(password);
        if (updateById !=1) throw new MessageException("观看次数更新失败");
        //设置密码校验结果
        classInforBean.setCheckedResult(passwordBeans.size());

        QueryWrapper<LectureScheduleBean> lectureScheduleBean = new QueryWrapper<>();
        lectureScheduleBean.eq("user",userBean.getName())
                .eq("class_name",String.valueOf(id))
                .eq("deparment",userBean.getDepartment())
                .eq("deparsmall",userBean.getDepartsmall());
        List<LectureScheduleBean> lectureScheduleBeans = lectureScheduleDao.selectList(lectureScheduleBean);
        if (lectureScheduleBeans.size() > 1) throw new MessageException("数据中有重复数据");
        //用于存放返回的对象
        Map<String, Object> map = new HashMap<>();
        //判断是否观看过视频
        if (lectureScheduleBeans.size() == 1){
            map.put("lectureSchedule",lectureScheduleBeans.get(0));
        } else {
            LectureScheduleBean lectureSchedule = new LectureScheduleBean();
            lectureSchedule.setUser(userBean.getName())
                    .setClassName(String.valueOf(id))
                    .setTime(LocalDateTime.now())
                    .setDeparment(userBean.getDepartment())
                    .setDeparsmall(userBean.getDepartsmall())
                    .setIsread(0);
            int insert = lectureScheduleDao.insert(lectureSchedule);
            if (insert != 1) throw new MessageException("新增观看记录失败");
            lectureSchedule = lectureScheduleDao.selectOne(lectureScheduleBean);
            map.put("lectureSchedule",lectureSchedule);
        }
        //设置密码不可见
        classInforBean.setClassPwd("");
        map.put("classInforBean",classInforBean);
        return map;
    }

    @Override
    public String test() {
        return "哈哈哈哈哈";
    }
}
