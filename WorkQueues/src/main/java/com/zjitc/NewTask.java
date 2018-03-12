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
 * @date-Time: 2018/3/12 16:26
 * @description:
 */
public class NewTask {

  private final static String QUEUE_NAME = "hello";

  private static String getMessage(String[] strings) {
    if (strings.length < 1) {
      return "hello world";
    }
    return joinStrings(strings, "");
  }

  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) {
      return "";
    }
    StringBuilder words = new StringBuilder(strings[0]);
    for (int i = 1; i < length; i++) {
      words.append(delimiter).append(strings[i]);
    }
    return words.toString();
  }

  public static void main(String[] args) throws IOException, TimeoutException {
    String message = getMessage(args);

    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("127.0.0.1");
    Connection connection = connectionFactory.newConnection();
    Channel channel = connection.createChannel();

    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");
  }


}
