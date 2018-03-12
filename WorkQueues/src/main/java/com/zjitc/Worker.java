package com.zjitc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Create by IntelliJ IDEA
 *
 * @author: jsonor
 * @date-Time: 2018/3/12 16:45
 * @description:
 */
public class Worker {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("127.0.0.1");
    Connection connection = connectionFactory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println("[*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws UnsupportedEncodingException {
        String message = new String(body, "UTF-8");

        System.out.println("[x] Received '" + message + "'");
        try {
          doWork(message);
        } catch (InterruptedException e) {
          System.out.println("[x] Done");
        }
      }
    };

    boolean autoAck = true;  // acknowledgment is covered below
    channel.basicConsume(QUEUE_NAME,autoAck, consumer);
  }

  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
      if (ch == '.') {
        Thread.sleep(1000);
      }
    }
  }
}
