package com.dream.dw.mq.activemq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.Destination;

@Service("producer")
public class MessageProducer {

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    static private MessageProducer messageProducer;

     @PostConstruct
     private void init() {
        messageProducer = this;
    }

    /**
     * Send message to destination.
     * @param message Message.
     */
    static public void sendMessage(final JSONObject message) {
        messageProducer.jmsTemplate.convertAndSend((Destination) message.get("destination"), message.get("messageBody"));
    }
}
