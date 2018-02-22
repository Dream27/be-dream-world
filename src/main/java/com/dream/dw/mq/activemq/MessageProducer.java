package com.dream.dw.mq.activemq;

import com.dream.dw.mq.message.MQMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.Destination;
import javax.jms.Message;

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
     * @param message MQMessage.
     */
    static public void sendMessage(final MQMessage message) {
        Destination destination = new ActiveMQQueue(message.getDestination());
        messageProducer.jmsTemplate.convertAndSend(destination, message);
    }

}
