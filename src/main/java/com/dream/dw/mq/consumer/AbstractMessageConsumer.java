package com.dream.dw.mq.consumer;

import javax.jms.Message;

public abstract class AbstractMessageConsumer {

    public abstract String getQueue();

    public abstract boolean process(Message message);

    public abstract int getRetryCount();

    protected void init() {
        // listener queue
        String queue = getQueue();

    }

    private void processRetry(Message message) {
        // retry
        if (process(message)) {

        }
    }

}
