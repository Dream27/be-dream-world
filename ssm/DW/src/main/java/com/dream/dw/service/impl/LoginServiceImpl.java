package com.dream.dw.service.impl;

import com.dream.dw.dao.UserExample;
import com.dream.dw.dao.UserMapper;
import com.dream.dw.model.User;
import com.dream.dw.service.LoginService;
import com.dream.dw.util.IdWorker;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by Dream on 2018/1/10.
 */
@Component
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean loginByName(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()) {
            return false;
        } else {
            return  true;
        }
    }

    @Override
    public boolean loginByEmail(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()) {
            return false;
        } else {
            return  true;
        }
    }

    @Override
    /*
    * 注册新用户并发送激活邮件
    * 返回值为0：用户添加失败；-1：用户添加成功，邮件发送失败；1：用户添加成功，邮件发送成功
    * */
    public int registerUser(User user) {
        //先查询是否有该用户



        IdWorker idWorker = new IdWorker();
        user.setUserId(idWorker.nextId());
        int result = userMapper.insertSelective(user);

        int result1 = 0;
        if (result != 0) {
            result1 = (sendActiveEmail(user) == true ? 1:-1);
        }
        return result1;
    }

    @Override
    public boolean sendActiveEmail(User user) {
        String apiUser = "dream7_test_d01TQg";
        String apiKey = "DN4wAIhwrUJYFFyS";
        SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);
        String code= UUID.randomUUID().toString().replace("-", "");

        Email email = Email.general()
                .from("3373524374@qq.com.sendcloud.org")
                .fromName("DreamWorld")
                .html("<h3><a href='http://localhost:8080/login/active?code="+code+"'>http://localhost:8080/login/active?code="+code+"</a></h3>")
                .subject("DreamWorld激活邮件")
                .to(user.getEmail());

        Result result = webapi.mail().send(email);
        result.isSuccess();      //API 请求是否成功
        result.getStatusCode();  //API 返回码
        result.getMessage();     //API 返回码的中文解释
        return !result.isSuccess() ? false:true;
    }

    @Override
    public boolean activeUser(String code) {
        //查询code对应的用户id，若有，将用户状态改为已激活，返回true，否则返回false
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andUserIdEqualTo(userId);
//        User user = new User();
//        user.setStatus(1);
//        int result = userMapper.updateByExampleSelective(user, userExample);
        return true;
    }
}
