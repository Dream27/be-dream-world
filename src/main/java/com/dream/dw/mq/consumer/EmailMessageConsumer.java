package com.dream.dw.mq.consumer;

import com.dream.dw.email.EmailFactory;
import com.dream.dw.email.EmailUtils;
import com.dream.dw.mq.activemq.MessageQueue;
import com.dream.dw.mq.message.EmailMessage;
import com.dream.dw.mq.message.MQMessage;
import io.jstack.sendcloud4j.mail.Result;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class EmailMessageConsumer extends AbstractMessageConsumer {

    @Override
    public String getQueue() {
        return MessageQueue.EMAIL_QUEUE;
    }

    @Override
    public int getRetryCount() {
        return 3;
    }

//    @Override
//    public void onMessage(Message message) {
//        try{
//            //MqBean bean = (MqBean) getMessageConverter().fromMessage(message);
//            ActiveMQDestination queues=(ActiveMQDestination)message.getJMSDestination();
//            if(queues.getPhysicalName().equalsIgnoreCase("queue1")) {
//                processRetry((MQMessage)message);
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean process(MQMessage message) {
        // send email
        EmailMessage emailMessage = (EmailMessage) message;
        Result result = EmailUtils.sendEmail(EmailFactory.newRegisterActiveEmail(emailMessage.getToEmailAddress(), emailMessage.getUserName(), emailMessage.getActiveUrl()));
        return result.isSuccess();
    }

    @PostConstruct
    public void postConstruct() {
        init();
    }

}
