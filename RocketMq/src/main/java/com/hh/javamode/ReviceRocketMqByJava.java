package com.hh.javamode;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.vvconsumer.VVDefaultPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lijing
 * @date 2019/11/22 14:34
 **/

/**
 * 用java代码实现接受RocketMQ消息
 */
public class ReviceRocketMqByJava {


  public static void main(String[] args) throws MQClientException, InterruptedException {
      // 构建Consumer实例, 构造方法必填参数groupId
      VVDefaultPushConsumer consumer = new VVDefaultPushConsumer("C_HA_CMS_AUDITOBJ");
// 如果在apollo中没有进行相关配置，想快速测试，可以使用加如下两行注释部分
 consumer.setUseApolloConfig(false);
 consumer.setNamesrvAddr("192.168.3.17:9876");
// 设置订阅的Topic
      consumer.subscribe("T_HA_CMS_AUDITOBJ");
    /** 创建并注册处理消息的Listener 实现consumeMessage方法，处理消息并返回处理结果 */
    consumer.registerMessageListener(
        new MessageListenerConcurrently() {
          @Override
          public ConsumeConcurrentlyStatus consumeMessage(
              List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
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
        });
      consumer.start();
      Thread.sleep(1000 * 100);
// 关闭消费者实例
      consumer.shutdown();

  }
}
