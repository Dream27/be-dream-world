package com.dream.dw.mq.activemq;

import com.alibaba.fastjson.JSONObject;
import com.dream.dw.email.EmailFactory;
import com.dream.dw.email.EmailUtils;
import io.jstack.sendcloud4j.mail.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    /**
     * Listener to listen mytest.queue
     * @param messageBody JSONObject.
     */
    @JmsListener(destination = MessageQueue.EMAIL_QUEUE)
    public void emailQueueListener(JSONObject messageBody) {
        Result result = EmailUtils.sendEmail(EmailFactory.newRegisterActiveEmail((String) messageBody.get("toEmailAddress"), (String) messageBody.get("userName"), (String) messageBody.get("activeUrl")));
//        if (result.isSuccess()) {
//
//        }
    }

}
