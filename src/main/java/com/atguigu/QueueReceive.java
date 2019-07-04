package com.atguigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueReceive {
    public static void main(String[] args) throws Exception{
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.215.126:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //带事务的session，并且自动签收
        final Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");
        MessageConsumer consumer = session.createConsumer(destination);

        int i = 0;
        while (i<3){
            i++;
            TextMessage message = (TextMessage) consumer.receive();
            session.commit();
            System.out.println("收到的消息是："+message.getText());
        }
        session.close();
        connection.close();
    }
}
