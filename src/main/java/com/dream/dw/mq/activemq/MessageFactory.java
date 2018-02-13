package com.dream.dw.mq.activemq;

import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;

public class MessageFactory {

    public static JSONObject getEmailMessage(String toEmailAddress, String userName, String activeUrl) {
        Destination destination = new ActiveMQQueue(MessageQueue.EMAIL_QUEUE);
        JSONObject message = new JSONObject();
        JSONObject messageBody = new JSONObject();

        messageBody.put("toEmailAddress", toEmailAddress);
        messageBody.put("userName", userName);
        messageBody.put("activeUrl", activeUrl);
        message.put("destination", destination);
        message.put("messageBody", messageBody);

        return message;
    }
}
