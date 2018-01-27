package com.dream.dw.email;

import io.jstack.sendcloud4j.mail.Email;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * Created by Dream on 2018/1/26.
 */
@Configuration
@ConfigurationProperties(prefix = "sendcloud")
public class MyEmail {

    static String sendName;

    static String sendEmailAddress;

    String receiveEmailAddress;

    HashMap<String, String> content;

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendEmailAddress() {
        return sendEmailAddress;
    }

    public void setSendEmailAddress(String sendEmailAddress) {
        this.sendEmailAddress = sendEmailAddress;
    }

    public Email getEmail() {
        return null;
    }
}
