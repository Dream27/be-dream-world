package com.dream.dw.mq.activemq;

import com.dream.dw.email.EmailFactory;
import com.dream.dw.email.EmailUtils;
import com.dream.dw.mq.message.EmailMessage;
import com.dream.dw.mq.message.MQMessage;
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
     * @param message Message.
     */
    @JmsListener(destination = MessageQueue.EMAIL_QUEUE)
    public void emailQueueListener(MQMessage message) {
        EmailMessage emailMessage = (EmailMessage)message;
        Result result = EmailUtils.sendEmail(EmailFactory.newRegisterActiveEmail(emailMessage.getToEmailAddress(), emailMessage.getUserName(), emailMessage.getActiveUrl()));
//        if (result.isSuccess()) {
//
//        }
    }

}
