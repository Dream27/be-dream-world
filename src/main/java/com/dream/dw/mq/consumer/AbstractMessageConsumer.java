package com.dream.dw.mq.consumer;

import com.dream.dw.mq.activemq.MessageProducer;
import com.dream.dw.mq.message.MQMessage;
import com.dream.dw.mq.message.MessageFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.*;

/**
 * This abstract class is to be extended by any message consumer. Each consumer can only listen for a specific queue.
 */
public abstract class AbstractMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMessageConsumer.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * Queue to listen for.
     */
    public abstract String getQueue();

    /**
     * Processing retry count.
     */
    public abstract int getRetryCount();

    /**
     * Process message logic.
     * @param message Message from specified queue.
     * @return true: Successfully processed.
     *         false: Failed to process.
     */
    public abstract boolean process(MQMessage message);

    /**
     * Setup message listener. This method must be called in subclass.
     */
    protected void setup() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(new ActiveMQQueue(getQueue()));
        MessageListener messageListener = this::processRetry;
        container.setMessageListener(messageListener);
        container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        container.initialize();
        container.start();
    }

    /**
     * Process the message with given retry count.
     */
    private void processRetry(Message message) {
        // Retrieve mqMessage from message
        MQMessage mqMessage = null;
        try {
            mqMessage = (MQMessage)((ObjectMessage)message).getObject();
        } catch (JMSException e) {
            logger.error("Illegal message: {}", message);
        }

        // Process message
        int retryCount = getRetryCount();
        boolean result = false;
        if (mqMessage != null) {
            while (retryCount > 0) {
                result = process(mqMessage);
                if (result) {
                    break;
                }
                retryCount--;
            }

            if (!result) {
                sendErrorQueue(mqMessage);
            }
        }

        // Acknowledge
        try {
            message.acknowledge();
        } catch (JMSException e) {
            logger.error("Failed to acknowledge message: {}, exception: {}", message, e);
        }
    }

    // Send message to error queue if the message is failed to be processed.
    private void sendErrorQueue(MQMessage message) {
        MessageProducer.sendMessage(MessageFactory.getErrorMessage(message));
    }

}
