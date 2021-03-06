package com.zjitc.topic;

import com.rabbitmq.client.*;
import com.zjitc.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * Dare : 2018/5/2
 * Time : 14:38
 * Description :
 *
 * @author : kevin
 */
public class Recv2 {
  private static final String QUEUE_NAME = "test_queue_routing_2";
  private static final String EXCHANGE_NAME = "test_exchange_direct";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionUtil.getConnection();

    final Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");

    channel.basicQos(1);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body, "utf-8");

        System.out.println("[2] Recv msg:" + msg);
        System.out.println("[2] done");

        channel.basicAck(envelope.getDeliveryTag(), false);

      }
    };
    channel.basicConsume(QUEUE_NAME, false, consumer);
  }
}
