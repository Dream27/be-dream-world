package com.dream.dw.email;

import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.MailWebApi;
import io.jstack.sendcloud4j.mail.Result;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Dream on 2018/1/24.
 */
@Component
@ConfigurationProperties(prefix = "email.sendcloud")
public class EmailUtils {

    private String apiUser;

    private String apiKey;

    private String fromName;

    private String fromEmailAddress;

    @Bean
    private MailWebApi mailWebApi() {
        return SendCloud.createWebApi(apiUser, apiKey).mail();
    }

    public Result sendEmail(Email email) {
        email.from(fromEmailAddress).fromName(fromName);
        return mailWebApi().send(email);
    }

}
