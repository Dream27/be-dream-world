package com.dream.dw.mq.consumer;

import com.dream.dw.mq.activemq.MessageProducer;
import com.dream.dw.mq.message.MQMessage;
import com.dream.dw.mq.message.MessageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Message;
import javax.jms.MessageListener;

public abstract class AbstractMessageConsumer implements MessageListener{

    public abstract String getQueue();

    public abstract int getRetryCount();

    protected void init() {
        // listener queue
        String queue = getQueue();
    }

    @Override
    public void onMessage(Message message) {
        processRetry((MQMessage)message);
    }

//    @Bean
//    public DefaultMessageListenerContainer listenerContainer(){
//        DefaultMessageListenerContainer m =new DefaultMessageListenerContainer();
//        m.setConnectionFactory(connectionFactory);
//        Destination d = new ActiveMQQueue("*");//*表示通配所有队列名称
//        m.setDestination(d);
//        m.setMessageListener(new QueueMessageListener());
//        return m;
//    }

    public abstract boolean process(MQMessage message);

    private void processRetry(MQMessage message) {
        // retry
        int times = getRetryCount();

        while(!process(message) && times != 0) {
            times--;
        }

        sendErrorQueue(message);
    }

    private void sendErrorQueue(MQMessage message) {
        MessageProducer.sendMessage(MessageFactory.getErrorMessage(message));
    }

}
