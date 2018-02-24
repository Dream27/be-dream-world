package com.dream.dw.mq.consumer;

import com.dream.dw.mq.activemq.MessageProducer;
import com.dream.dw.mq.activemq.MessageQueue;
import com.dream.dw.mq.message.MQMessage;
import com.dream.dw.mq.message.TestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestMessageConsumer extends AbstractMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TestMessageConsumer.class);

    @Override
    public String getQueue() {
        return MessageQueue.TEST_QUEUE;
    }

    @Override
    public int getRetryCount() {
        return 3;
    }

    @PostConstruct
    public void init() {
        setup();

        // Test sending message
//        TestMessage testMessage = new TestMessage();
//        testMessage.setTestMessage("This is a test message.");
//        MessageProducer.sendMessage(testMessage);
    }

    @Override
    public boolean process(MQMessage message) {
        TestMessage testMessage = (TestMessage) message;
        logger.error("test message: {}", testMessage.getTestMessage());
        return true;
    }

}
