package com.itheima.mq.rocketmq.order;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟订单流程
 */
public class OrderStep {
    private long orderID;
    private String desc;

    public long getOrderID() {
        return orderID;
    }

    private void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderID=" + orderID +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static List<OrderStep> buildOrder(){
        //创建三个订单1039L,1065L,7235L
        //1039L；创建 付款 推送 完成
        //1065L：创建 付款
        //7235L：创建 付款
        List<OrderStep> orderlist = new ArrayList<>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderID(1039L);
        orderDemo.setDesc("创建");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(1065L);
        orderDemo.setDesc("创建");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(1039L);
        orderDemo.setDesc("付款");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(7235L);
        orderDemo.setDesc("创建");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(7235L);
        orderDemo.setDesc("付款");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(7235L);
        orderDemo.setDesc("推送");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(7235L);
        orderDemo.setDesc("完成");
        orderlist.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderID(1065L);
        orderDemo.setDesc("付款");
        orderlist.add(orderDemo);
        return orderlist;
    }
}
