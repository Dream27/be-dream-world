package com.dream.dw.mq.activemq.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    Logger logger = LoggerFactory.getLogger(Consumer.class);

    /**
     * Listener to listen mytest.queue
     * @param message Message.
     */
    @JmsListener(destination = MessageQueue.EMAIL_QUEUE)
    public void emailQueueListener(String message) {
        logger.error("Received message from test.queue: " + message);
    }

}
