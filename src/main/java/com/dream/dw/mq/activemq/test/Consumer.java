package com.dream.dw.mq.activemq.test;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    /**
     * Listener to listen mytest.queue
     * @param message Message.
     */
    @JmsListener(destination = "test.queue")
    public void myTestQueueListener(String message) {
        System.out.println("Received message from test.queue:" + message);
    }

}
