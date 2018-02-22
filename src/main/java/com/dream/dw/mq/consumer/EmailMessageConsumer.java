package com.dream.dw.mq.consumer;

import com.dream.dw.email.EmailFactory;
import com.dream.dw.email.EmailUtils;
import com.dream.dw.mq.activemq.MessageQueue;
import com.dream.dw.mq.message.EmailMessage;
import com.dream.dw.mq.message.MQMessage;
import io.jstack.sendcloud4j.mail.Result;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EmailMessageConsumer extends AbstractMessageConsumer {

    @Override
    public String getQueue() {
        return MessageQueue.EMAIL_QUEUE;
    }

    @Override
    public int getRetryCount() {
        return 3;
    }

    @PostConstruct
    public void init() {
        setup();
    }

    @Override
    public boolean process(MQMessage message) {
        // send email
        EmailMessage emailMessage = (EmailMessage) message;
        Result result = EmailUtils.sendEmail(EmailFactory.newRegisterActiveEmail(emailMessage.getToEmailAddress(), emailMessage.getUserName(), emailMessage.getActiveUrl()));
        return result.isSuccess();
    }

}
