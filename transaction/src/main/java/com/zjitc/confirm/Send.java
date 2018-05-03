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
 * Description :
 * @author kevin
 */
public class Send {
  public static final String QUEUE_NAME = "test_queue_transaction";

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    Connection connection = ConnectionUtil.getConnection();

    Channel channel = connection.createChannel();
    String msg = "hello";

    try {
      channel.txSelect();
      System.out.println("[WQ] send : " + msg);
      channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
      channel.txCommit();
    } catch (Exception e) {
      channel.txRollback();
      System.out.println("send massage txRollback");
    }

    channel.close();
    connection.close();
  }
}
