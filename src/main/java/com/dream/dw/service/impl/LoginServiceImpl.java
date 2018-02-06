package com.dream.dw.service.impl;

import com.dream.dw.dao.UserExample;
import com.dream.dw.dao.UserMapper;
import com.dream.dw.email.EmailFactory;
import com.dream.dw.email.EmailUtils;
import com.dream.dw.model.User;
import com.dream.dw.service.LoginService;
import com.dream.dw.util.IdWorker;
import com.dream.dw.util.JedisClusterUtils;
import io.jstack.sendcloud4j.mail.Result;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by Dream on 2018/1/10.
 */
@Component
@Data
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Value("${url.activeUrl}")
    private String activeUrl;

    @Override
    public boolean loginByName(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        return users.isEmpty();
    }

    @Override
    public boolean loginByEmail(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail()).andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        return users.isEmpty();
    }

    @Override
    public User checkUserExist(User user) {
        UserExample userExample = new UserExample();
        //user email must be unique
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
    * register new user and send active email
    * return 0：add user error；-1：add user success，send email error；1：add user success，send email success； 2：the user has been exist
    * */
    public int registerUser(User user) {
        //query: If the user has been exist: if state has been active, return, else send active email. If the user has not been exist, add the user and send active email.
        int result1 = 0;
        User user1 = checkUserExist(user);
        if( user1 != null && user1.getStatus() == 0) {
            return 2;
        } else if ( user1 != null && user1.getStatus() == 1) {
            result1 = (!sendActiveEmail(user1).equals("false") ? 1:-1); //send active email
        } else {
            //add new user
            IdWorker idWorker = new IdWorker();
            user.setUserId(idWorker.nextId());
            int result = userMapper.insertSelective(user);

            if (result != 0) {
                result1 = (!sendActiveEmail(user).equals("false") ? 1:-1); //send active email
            }
        }
        return result1;
    }

    @Override
    //@CachePut(value = "activecode", key="#user.uid"+"")
    public String sendActiveEmail(User user) {
        String code= UUID.randomUUID().toString().replace("-", "");
        String activeUrl = this.activeUrl + code;

        // send email
        Result result = EmailUtils.sendEmail(EmailFactory.newRegisterActiveEmail(user.getEmail(), user.getName(), activeUrl));

        if (result.isSuccess()) {
            //save the active code to redis
            JedisClusterUtils.saveString(code, user.getUserId()+"");
            return code;
        }
        return "false";
        //return result.getMessage();
    }

    @Override
    public boolean activeUser(String code) {
        //query user id corresponding the code, if exist, update user state to active and delete relevant redis record, return true, else return false.
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
}
