package com.dream.dw.mq.activemq;

import com.dream.dw.email.EmailFactory;
import com.dream.dw.email.EmailUtils;
import com.dream.dw.mq.message.EmailMessage;
import com.dream.dw.mq.message.MQMessage;
import com.dream.dw.mq.message.MessageFactory;
import io.jstack.sendcloud4j.mail.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    /**
     * Listener to listen mytest.queue
     * @param message Message.
     */
    @JmsListener(destination = MessageQueue.EMAIL_QUEUE)
    public void emailQueueListener(MQMessage message) {
        retryProcess(MessageQueue.EMAIL_QUEUE, message);
    }

    private void retryProcess(String queue, MQMessage message) {
        boolean result = false;
        int times = 5;

        //process
        while(!result && times != 0) {
            if (queue.equals(MessageQueue.EMAIL_QUEUE)) {
                EmailMessage emailMessage = (EmailMessage) message;
                Result result1 = EmailUtils.sendEmail(EmailFactory.newRegisterActiveEmail(emailMessage.getToEmailAddress(), emailMessage.getUserName(), emailMessage.getActiveUrl()));
                result = result1.isSuccess();
            }
            times = times - 1;
        }

        //send to error queue
        if(!result) {
            sendErrorQueue(message);
        }
    }

    private void sendErrorQueue(MQMessage message) {
        MessageProducer.sendMessage(MessageFactory.getErrorMessage(message));
    }

}
