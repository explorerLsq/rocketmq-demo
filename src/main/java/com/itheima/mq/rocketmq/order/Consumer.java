package com.itheima.mq.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws Exception {
        //创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //制定nameserver地址
        consumer.setNamesrvAddr("192.168.52.129:9876;192.168.52.130:9876");
        //订阅主题Topic和Tag(消费多Tag的消息“Tag1 | Tag2”,消费所有消息“*”)
        consumer.subscribe("OrderTopic", "*");

        //注册消息监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("线程名称：【"+Thread.currentThread().getName()+"】---消费消息：" + new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("消费者启动.");
    }
}
