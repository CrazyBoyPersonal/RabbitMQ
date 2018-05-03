package com.zjitc.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create with IntelliJ IDEA
 * User : kevin
 * Dare : 2018/5/1
 * Time : 16:59
 * To change this template use File | Setting | File Template.
 * Description :
 * @author kevin
 */
public class ConnectionUtil {
  public static Connection getConnection() throws IOException, TimeoutException {
    //定义一个连接工厂
    ConnectionFactory factory = new ConnectionFactory();
    //设置服务地址
    factory.setHost("120.24.56.46");
    //AMQP 5672
    factory.setPort(5672);
    //v-host
    factory.setVirtualHost("/v_host");
    //用户名
    factory.setUsername("kevin");
    //密码
    factory.setPassword("123456");
    //返回连接
    return factory.newConnection();
  }
}
