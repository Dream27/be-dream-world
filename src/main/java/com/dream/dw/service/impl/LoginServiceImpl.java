package com.dream.dw.service.impl;

import com.dream.dw.dao.UserExample;
import com.dream.dw.dao.UserMapper;
import com.dream.dw.model.User;
import com.dream.dw.service.LoginService;
import com.dream.dw.service.UserService;
import com.dream.dw.util.IdWorker;
import com.dream.dw.util.JedisClusterUtils;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import io.jstack.sendcloud4j.mail.Substitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ValueOperations;
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
        //System.out.println("==================================");
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
    public User checkUserExist(User user) {
        UserExample userExample = new UserExample();
        //用户邮箱不可重复
        userExample.createCriteria().andEmailEqualTo(user.getEmail()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()) {
            return null;
        } else {
            return  users.get(0);
        }
    }

    @Override
    /*
    * 注册新用户并发送激活邮件
    * 返回值为0：用户添加失败；-1：用户添加成功，邮件发送失败；1：用户添加成功，邮件发送成功； 2：已有该用户
    * */
    public int registerUser(User user) {
        //先查询若有该用户：状态激活，直接返回；状态未激活，则只发送邮件。若无该用户，创建用户发送邮件；
        int result1 = 0;
        User user1 = checkUserExist(user);
        if( user1 != null && user1.getStatus() == 0) {
            return 2;
        } else if ( user1 != null && user1.getStatus() == 1) {
            result1 = (!sendActiveEmail(user1).equals("false") ? 1:-1); //发送激活邮件
        } else {
            //插入新用户
            IdWorker idWorker = new IdWorker();
            user.setUserId(idWorker.nextId());
            int result = userMapper.insertSelective(user);

            if (result != 0) {
                result1 = (!sendActiveEmail(user).equals("false") ? 1:-1); //发送激活邮件
            }
        }
        return result1;
    }

    @Override
    //@CachePut(value = "activecode", key="#user.uid"+"")
    public String sendActiveEmail(User user) {
        String apiUser = "dream7_test_d01TQg";
        String apiKey = "DN4wAIhwrUJYFFyS";
        SendCloud webapi = SendCloud.createWebApi(apiUser, apiKey);
        String code= UUID.randomUUID().toString().replace("-", "");

        Email email = Email.template("test_template_active")
                .from("3373524374@qq.com.sendcloud.org")
                .fromName("DreamWorld")
                .substitutionVars(Substitution.sub()  // 模板变量替换
                        .set("url", "http://localhost:8080/login/active?code="+code)
                        .set("name", user.getName()))
                .to(user.getEmail());

        Result result = webapi.mail().send(email);
        if (result.isSuccess()) {
            //存储激活码到redis中
            JedisClusterUtils.saveString(code, user.getUserId()+"");
        }

        return !result.isSuccess() ? "false":code;
    }

    @Override
    public boolean activeUser(String code) {
        //查询code对应的用户id，若有，将用户状态改为已激活，删除redis记录，返回true，否则返回false
        if(JedisClusterUtils.isCached(code))
        {
            Long userId = Long.parseLong(JedisClusterUtils.getString(code));
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(userId);
            User user = new User();
            user.setStatus(0);
            int result = userMapper.updateByExampleSelective(user, userExample);

            JedisClusterUtils.delKey(code);
            return result == 0 ? false:true;
        }
        return true;
    }

//    @Override
//    //@CachePut(value = "test", key="#user.userId + ''")
//    @Cacheable(value = "test", key="#user.userId + ''")
//    public String test(User user) {
//        System.out.println("================");
//        String code= UUID.randomUUID().toString().replace("-", "");
//        return code;
//    }
//
//
//    @Override
//    public boolean isCached(String key) {
//        return JedisClusterUtils.isCached(key);
//    }
}
