package com.zjitc.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zjitc.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * User : kevin
 * Dare : 2018/5/2
 * Time : 10:32
 * To change this template use File | Setting | File Template.
 * Description :
 */
public class Send {

  public static final String QUEUE_NAME = "test_work_queue";

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    Connection connection = ConnectionUtil.getConnection();

    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    int prefetchCount = 1;
    channel.basicQos(prefetchCount);

    for (int i = 0; i < 100; i++) {
      String msg = "hello2 " + i;
      System.out.println("[WQ] send : " + msg);
      channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
      Thread.sleep(200);
    }

    channel.close();
    connection.close();
  }
}
