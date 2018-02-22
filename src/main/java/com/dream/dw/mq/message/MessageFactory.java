package com.dream.dw.mq.message;

public class MessageFactory {

    public static MQMessage getEmailMessage(String toEmailAddress, String userName, String activeUrl) {
        EmailMessage emailMessge = new EmailMessage();
        emailMessge.setToEmailAddress(toEmailAddress);
        emailMessge.setUserName(userName);
        emailMessge.setActiveUrl(activeUrl);
        return emailMessge;
    }

    public static MQMessage getErrorMessage(MQMessage mqMessage) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMqMessage(mqMessage);
        return errorMessage;
    }

}
