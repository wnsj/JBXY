package com.jiubo.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiubo.project.bean.UserBean;
import com.jiubo.project.dao.UserDao;
import com.jiubo.project.exception.MessageException;
import com.jiubo.project.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiubo.project.util.DateUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swd
 * @since 2021-01-19
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, UserBean> implements UserService {

    @Value("${appId}")
    private String appid;

    @Value("${appSecret}")
    private String appSecret;

    @Value("${redirectUri}")
    private String redirectUri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDao userDao;


    @Override
    public Map<String,Object> getCodeUrl() throws UnsupportedEncodingException {
        Map<String,Object> map = new HashMap<>();
//        redirectUri = "http://shop.bkxinli.com/bkhd/shareTest.html";
        log.info("urlEncode前的链接:{}",redirectUri);
        String url = URLEncoder.encode(redirectUri,"UTF-8");
        log.info("urlEncode后的链接:{}",url);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        log.info("uuid:{}",uuid);
        StringBuilder resultUrl = new StringBuilder();
        resultUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
        resultUrl.append("appid=").append(appid);
        resultUrl.append("&redirect_uri=").append(url).append("?sessionid=").append(uuid);
        resultUrl.append("&response_type=code&scope=snsapi_userinfo");
        resultUrl.append("&state=").append(uuid);
        map.put("url",resultUrl);
        map.put("sessionId",uuid);
        return map;
    }

    @Override
    public Integer getInfoByCode(String sessionId, String code) throws MessageException {
        StringBuilder builder = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?");
        builder.append("appid=").append(appid);
        builder.append("&secret=").append(appSecret);
        builder.append("&code=").append(code).append("&grant_type=authorization_code");
        ResponseEntity<String> forEntity = restTemplate.getForEntity(builder.toString(),String.class);
        String body = forEntity.getBody();
        JSONObject jsonObject =JSONObject.parseObject(body);
        String openId = jsonObject.getString("openid");
        log.info("根据code访问返回openId:{}",openId);
        if(StringUtils.isEmpty(openId)){
            throw new MessageException("通过code去微信查询openid异常");
        }
        QueryWrapper<UserBean> qw = new QueryWrapper<>();
        qw.eq("openid",openId);
        List<UserBean> list = userDao.selectList(qw);
        //0代表没有查到1代表数据库存在此openid数据
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        UserBean userBean = list.get(0);
        userBean.setSessionid(sessionId);
        userDao.updateById(userBean);
        return 1;
    }

    @Override
    public UserBean isLogin(String sessionId) {
        QueryWrapper<UserBean> qw = new QueryWrapper<>();
        UserBean userBean = new UserBean();
        qw.eq("sessionid",sessionId);
        List<UserBean> list = userDao.selectList(qw);
        if(!CollectionUtils.isEmpty(list)){
            userBean = list.get(0);
        }
        return userBean;
    }

    @Override
    public Integer addUserInfo(UserBean userBean) throws MessageException {
        Date date = new Date();
        userBean.setCreatTime(DateUtils.dateToLocalDateTime(date));
        Integer row = userDao.insert(userBean);
        if(!row.equals(1)){
            throw new MessageException("数据插入异常");
        }
        return row;
    }

    @Override
    public Integer updateUserInfo(UserBean userBean) throws MessageException {
        if(userBean.getId() == null){
            throw new MessageException("用户id为null");
        }
        Integer row = userDao.updateById(userBean);
        if(!row.equals(1)){
            throw new MessageException("根据用户id更新数据失败");
        }
        return row;
    }
}
