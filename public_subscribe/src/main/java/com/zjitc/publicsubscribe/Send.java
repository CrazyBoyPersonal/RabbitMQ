package com.zjitc.publicsubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zjitc.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * Dare : 2018/5/2
 * Time : 14:10
 * To change this template use File | Setting | File Template.
 * Description :
 * @author kevin
 */
public class Send {
  private static final String EXCHANGE_NAME = "test_exchange_fanout";

  public static void main(String[] args) throws IOException, TimeoutException {
    Connection connection = ConnectionUtil.getConnection();

    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

    String msg = "hello world";

    channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

    System.out.println(msg);

    channel.close();
    connection.close();
  }
}
