package com.zjitc.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * User : kevin
 * Dare : 2018/5/2
 * Time : 10:27
 * To change this template use File | Setting | File Template.
 * Description :
 * @author kevin
 */
public class ConnectionUtil {

  public static Connection getConnection() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setHost("120.24.56.46");

    factory.setPort(5672);

    factory.setVirtualHost("/v_host");

    factory.setUsername("kevin");

    factory.setPassword("123456");

    return factory.newConnection();
  }
}
