package com.itheima.mq.rocketmq.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class Producer {
    public static void main(String[] args) throws Exception {
        // 创建消息生产者producer,并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //制定nameserver地址
        producer.setNamesrvAddr("192.168.52.129:9876;192.168.52.130:9876");
        //启动producer
        producer.start();

        List<OrderStep> orderSteps = OrderStep.buildOrder();

        //发送消息
        for (int i =0;i<orderSteps.size();i++){
            String body = orderSteps.get(i)+"";
            Message message = new Message("OrderTopic","Order","i"+i,body.getBytes());
            /**
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择队列的业务标识（订单ID）
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 *
                 * @param list :队列集合
                 * @param message：消息对象
                 * @param o：业务标识参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long orderId = (long) o;
                    //取模
                    long index = orderId % list.size();

                    return list.get((int) index);
                }
            },orderSteps.get(i).getOrderID());
            System.out.println("发送结果："+sendResult);
        }
        producer.shutdown();
    }
}
