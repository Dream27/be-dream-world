package com.dream.dw.email;

import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Substitution;
import java.util.HashMap;

/**
 * Created by Dream on 2018/1/26.
 */
public class RegisterActiveEmail extends MyEmail {

    //@Value("${sendcloud.activetemplate}")
    //private String template;

    public RegisterActiveEmail(String receiveEmailAddress, HashMap<String, String> content) {
        super.receiveEmailAddress = receiveEmailAddress;
        super.content = content;
    }

    public Email getEmail() {
        return Email.template("test_template_active")
                .from(sendEmailAddress)
                .fromName(sendName)
                .substitutionVars(Substitution.sub()  // replace template variable
                        .set("url", content.get("activeUrl"))
                        .set("name", content.get("userName")))
                .to(receiveEmailAddress);
    }
}
