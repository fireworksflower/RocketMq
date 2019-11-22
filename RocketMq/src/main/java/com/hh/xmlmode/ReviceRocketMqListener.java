package com.hh.xmlmode;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lijing
 * @date 2019/11/22 15:02
 **/

public class ReviceRocketMqListener implements MessageListenerConcurrently{
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (null != msgs && msgs.size() > 0) {
            String jsonStr = "";
            System.out.printf("%s%n", msgs);
            for (int j = 0; j < msgs.size(); j++) {
                MessageExt msg = msgs.get(j);
                byte[] body = msg.getBody();
                jsonStr = new String(body);
                System.out.println(jsonStr);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

    }

}
