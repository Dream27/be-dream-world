package com.dream.dw.mq.message;

import com.dream.dw.mq.activemq.MessageQueue;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmailMessage extends MQMessage {

    String toEmailAddress;

    String userName;

    String activeUrl;

    public EmailMessage() {
        this.destination = MessageQueue.EMAIL_QUEUE;
    }

}
