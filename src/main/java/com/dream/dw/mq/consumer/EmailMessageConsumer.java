package com.dream.dw.mq.consumer;

import com.dream.dw.mq.activemq.MessageQueue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.Message;

@Component
public class EmailMessageConsumer extends AbstractMessageConsumer {

    @Override
    public String getQueue() {
        return MessageQueue.EMAIL_QUEUE;
    }

    @Override
    public boolean process(Message message) {
        // send email
        return false;
    }

    @Override
    public int getRetryCount() {
        return 3;
    }

    @PostConstruct
    public void postConstruct() {
        init();
    }

}
