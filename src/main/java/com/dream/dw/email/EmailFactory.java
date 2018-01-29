package com.dream.dw.email;

import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Substitution;

/**
 * Created by Dream on 2018/1/26.
 */
public class EmailFactory {

    public static Email newRegisterActiveEmail(String toEmailAddress, String userName, String activeUrl) {
        return Email.template("test_template_active")
                .to(toEmailAddress)
                .substitutionVars(Substitution.sub()
                    .set("name", userName)
                    .set("url", activeUrl));
    }

    public static Email newSystemNotificationEmail(String toEmailAddress, String notification) {
        return Email.template("dw_email_template_system_notification")
                .to(toEmailAddress)
                .substitutionVars(Substitution.sub()
                        .set("notification", notification));
    }
    
}
