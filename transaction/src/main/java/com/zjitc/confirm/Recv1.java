package com.zjitc.confirm;

import com.rabbitmq.client.*;
import com.zjitc.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * User : kevin
 * Dare : 2018/5/1
 * Time : 19:09
 * To change this template use File | Setting | File Template.
 * Description :
 */
public class Recv1 {
  public static final String QUEUE_NAME = "test_queue_transaction";

  public static void main(String[] args) throws IOException, TimeoutException {
    //获取连接
    Connection connection = ConnectionUtil.getConnection();
    //获取channel
    Channel channel = connection.createChannel();
    //生声明队列
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    //定义一个消费者
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body, "utf-8");
        System.out.println("[1] Recv msg:" + msg);
        System.out.println("[1] done");

      }
    };

    channel.basicConsume(QUEUE_NAME, false, consumer);
  }
}
