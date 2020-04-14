package com.itheima.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
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
            Message msg = new Message("base","Tag2",("Hello World"+i).getBytes());
            //发送 异步  消息
            producer.send(msg, new SendCallback() {
                /**
                 * 发送成功回调函数
                 * @param sendResult
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果："+sendResult);
                }

                /**
                 * 发送失败的回调函数
                 * @param throwable
                 */
                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送异常："+throwable);
                }
            });
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //关闭生产者producer
        producer.shutdown();
    }
}
