package com.itheima.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        // 创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //制定nameserver地址
        producer.setNamesrvAddr("192.168.52.129:9876;192.168.52.130:9876");
        //启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //创建消息对象，制定主题Topic，Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("base","Tag1",("Hello rocketmq,单向消息"+i).getBytes());
            //发送单向消息
            producer.sendOneway(msg);

            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //关闭生产者producer
        producer.shutdown();
    }

}
