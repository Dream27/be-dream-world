package com.dream.dw.mq.message;

import com.dream.dw.mq.activemq.MessageQueue;
import lombok.Data;

@Data
public class ErrorMessage extends MQMessage {

    MQMessage mqMessage;

    public ErrorMessage() {
        this.destination = MessageQueue.ERROR_QUEUE;
    }
}
