package com.zjitc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by IntelliJ IDEA
 *
 * @author: jsonor
 * @date-Time: 2018/3/12 15:26
 * @description:
 */
public class Send {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) throws IOException , TimeoutException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("120.24.56.46");
    Connection connection = connectionFactory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World!";

    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    System.out.println("[x] sent '" + message + "'");

    channel.close();
    connection.close();
  }
}
