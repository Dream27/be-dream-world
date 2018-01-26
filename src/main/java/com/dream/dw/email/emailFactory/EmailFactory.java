package com.dream.dw.email.emailFactory;

import com.dream.dw.email.MyEmail;
import com.dream.dw.email.RegisterActiveEmail;
import io.jstack.sendcloud4j.mail.Email;

import java.util.HashMap;

/**
 * Created by Dream on 2018/1/26.
 */
public class EmailFactory {

    static public Email createEmail(String type, String receiveEmailAdress, HashMap<String, String> content) {
        MyEmail myEmail = null;

        if(type == "register_active")
        {
            myEmail = new RegisterActiveEmail(receiveEmailAdress, content);
        }
        //else {}

        return myEmail.getEmail();
    }
}
