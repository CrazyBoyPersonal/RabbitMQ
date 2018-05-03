package com.zjitc.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.zjitc.util.ConnectionUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
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
public class Send3 {
  private static final String QUEUE_NAME = "test_queue_confirm3";

  public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
    Connection connection = ConnectionUtil.getConnection();

    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.confirmSelect();

    final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

    channel.addConfirmListener(new ConfirmListener() {
      @Override
      public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        if (multiple) {
          System.out.println("----handleAck----multiple");
          confirmSet.headSet(deliveryTag + 1).clear();
        } else {
          System.out.println("----handleAck----multiple----false");
          confirmSet.remove(deliveryTag);
        }
      }

      @Override
      public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        if (multiple) {
          System.out.println("----handleNack----multiple");
          confirmSet.headSet(deliveryTag + 1).clear();
        } else {
          System.out.println("----handleNack----multiple----false");
          confirmSet.remove(deliveryTag);
        }
      }
    });

    String msg = "hello confirm";
    //批量发送
    while(true) {
      long seqNo = channel.getNextPublishSeqNo();
      channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
      confirmSet.add(seqNo);
    }

    //channel.close();
    //connection.close();
  }
}
