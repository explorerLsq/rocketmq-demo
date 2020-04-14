package com.itheima.mq.rocketmq.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
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
        consumer.subscribe("DelayTopic","*");
        //设定消费模式：负载均衡|广播模式 默认是负载均衡模式
        //广播模式设定
        //consumer.setMessageModel(MessageModel.BROADCASTING);

        //设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            //接收消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext Context) {
                for (MessageExt msg:msgs) {
                    System.out.println("消息ID：【"+msg.getMsgId()+"】，延迟时间："+(System.currentTimeMillis()-msg.getStoreTimestamp()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者consumer
        consumer.start();
        System.out.println("消费者启动");
    }
}
