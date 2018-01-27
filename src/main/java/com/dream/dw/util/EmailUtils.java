package com.dream.dw.util;

import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dream on 2018/1/24.
 */
@Configuration
@ConfigurationProperties(prefix = "sendcloud")
public class EmailUtils {

    private static String apiUser;

    private static String apiKey;

    private static SendCloud webapi;

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public static Result sendActiveEmail(Email email) {
        webapi = SendCloud.createWebApi(apiUser, apiKey);
        Result result = webapi.mail().send(email);
        return result;
    }
}
