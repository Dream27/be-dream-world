package com.dream.dw.mq.message;

import com.dream.dw.mq.activemq.MessageQueue;
import lombok.Data;

@Data
public class TestMessage extends MQMessage {

    String testMessage;

    public TestMessage() {
        destination = MessageQueue.TEST_QUEUE;
    }
}
