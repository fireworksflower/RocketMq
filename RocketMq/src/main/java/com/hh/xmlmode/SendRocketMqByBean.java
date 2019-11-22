package com.hh.xmlmode;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.vvproducer.VVDefaultProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lijing
 * @date 2019/11/22 15:02
 **/
@Controller
public class SendRocketMqByBean {

    @Autowired
    private VVDefaultProducer producer;


    @ResponseBody
    @RequestMapping("sendRocketMqMethod")
    public void sendRocketMqMethod() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {

        JSONObject object = new JSONObject();

        object.put("name", "huahua");
        object.put("age", "9");
        String str = object.toString();
      // 创建消息
      final Message msg = new Message("T_HA_CMS_AUDITOBJ", "tagA", str.getBytes());

// 同步发送消息，并获取发送返回结果
      SendResult sendResult = producer.send(msg);
      System.out.printf("%s%n", sendResult);
  }


}
