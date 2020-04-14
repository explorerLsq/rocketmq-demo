package com.itheima.mq.rocketmq.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //制定nameserver地址
        producer.setNamesrvAddr("192.168.52.129:9876;192.168.52.130:9876");
        //启动producer
        producer.start();

        List<Message> msgs = new ArrayList<Message>();


        //创建消息对象，制定主题Topic，Tag和消息体
        /**
         * 参数一：消息主题Topic
         * 参数二：消息Tag
         * 参数三：消息内容
         */
        Message msg1 = new Message("BatchTopic","Tag1",("Hello World"+1).getBytes());
        Message msg2 = new Message("BatchTopic","Tag1",("Hello World"+2).getBytes());
        Message msg3 = new Message("BatchTopic","Tag1",("Hello World"+3).getBytes());

        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        //发送消息
        SendResult result = producer.send(msgs);
        //发送状态
        SendStatus status = result.getSendStatus();
        //消息ID
        String msgId = result.getMsgId();
        //消息接收队列ID
        int queueId = result.getMessageQueue().getQueueId();
        System.out.println("发送状态："+result+",消息ID："+msgId+",队列："+queueId);
        //线程睡1秒
        TimeUnit.SECONDS.sleep(1);

        //关闭生产者producer
        producer.shutdown();
    }
}
