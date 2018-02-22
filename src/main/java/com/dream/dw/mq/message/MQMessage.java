package com.dream.dw.mq.message;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class MQMessage implements Serializable{

    String destination;

}
