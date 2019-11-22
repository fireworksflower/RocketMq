package com.hh.javamode;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.vvproducer.VVDefaultProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lijing
 * @date 2019/11/22 14:26
 **/

/**
 * 用java代码实现发送RocketMQ消息
 */
@Controller
public class SendRocketMqByJava {

//异步发送
  public static void main(String[] args) throws RemotingException, MQClientException, InterruptedException {

      // 创建producer 实例，必填参数groupId
      VVDefaultProducer producer = new VVDefaultProducer("P_HA_CMS_AUDITOBJ");

// 如果在apollo中没有进行相关配置，想快速测试，可以使用加如下两行注释部分
 producer.setUseApolloConfig(false);
 producer.setNamesrvAddr("192.168.3.17:9876");

// 启动生产者实例
      producer.start();
      JSONObject object = new JSONObject();

      object.put("name", "huahua");
      object.put("age", "9");
      String str = object.toString();

// 创建消息
      final Message msg = new Message("T_HA_CMS_AUDITOBJ", "tagA", str.getBytes());

/**
 * 异步发送消息
 * 需要实现SendCallback，消息发送结束会回调Callback实例
 * onSuccess() 方法为消息发送成功回调此方法
 * onException() 方法为发送失败，出现异常调用此方法
 */
      producer.send(msg, new SendCallback() {
          @Override
          public void onSuccess(SendResult sendResult) {
              System.out.printf("%s%n", sendResult);
          }

          @Override
          public void onException(Throwable e) {
              e.printStackTrace();
          }
      });

      Thread.sleep(1000);
// 关闭生产者实例
      producer.shutdown();
  }
}
