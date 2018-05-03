package com.zjitc.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zjitc.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * User : kevin
 * Dare : 2018/5/1
 * Time : 17:19
 * To change this template use File | Setting | File Template.
 * Description : 批量发送消息 confirm
 *
 * @author kevin
 */
public class Send2 {
  private static final String QUEUE_NAME = "test_queue_confirm2";

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    Connection connection = ConnectionUtil.getConnection();

    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.confirmSelect();
    String msg = "hello confirm";
    //批量发送
    for (int i = 0; i < 10; i++) {
      channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
    }
    //确认
    if (!channel.waitForConfirms()) {
      System.out.println("send massage failed");
    } else {
      System.out.println("send massage ok");
    }

    channel.close();
    connection.close();
  }
}
