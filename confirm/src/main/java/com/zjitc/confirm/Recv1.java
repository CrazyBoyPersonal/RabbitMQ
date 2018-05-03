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
  private static final String QUEUE_NAME = "test_queue_confirm3";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionUtil.getConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body, "utf-8");
        System.out.println("[1] Recv msg:" + msg);
        System.out.println("[1] done");

      }
    };

    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}
