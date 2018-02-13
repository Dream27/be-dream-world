package com.dream.dw.mq.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.Destination;

@Service("producer")
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * Send message to destination.
     * @param destination Destination queue.
     * @param message Message.
     */
    public void sendMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    @PostConstruct
    private void test() {
        Destination destination = new ActiveMQQueue(MessageQueue.EMAIL_QUEUE);
        sendMessage(destination, "This is a test message.");
    }

}
