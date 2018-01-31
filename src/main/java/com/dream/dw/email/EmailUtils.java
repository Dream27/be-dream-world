package com.dream.dw.email;

import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.MailWebApi;
import io.jstack.sendcloud4j.mail.Result;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Dream on 2018/1/24.
 */
@Component
@ConfigurationProperties(prefix = "email.sendcloud")
@Setter
public class EmailUtils {

    private String apiUser;

    private String apiKey;

    private String fromName;

    private String fromEmailAddress;

    private MailWebApi mailWebApi;

    private static EmailUtils emailUtils;

    @PostConstruct
    private void init() {
        mailWebApi = SendCloud.createWebApi(apiUser, apiKey).mail();
        emailUtils = this;
    }

    public static Result sendEmail(Email email) {
        email.from(emailUtils.fromEmailAddress).fromName(emailUtils.fromName);
        return emailUtils.mailWebApi.send(email);
    }

}
