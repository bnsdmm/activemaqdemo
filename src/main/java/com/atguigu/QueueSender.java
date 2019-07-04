package com.atguigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueSender {
    public static void main(String[] args) throws Exception{
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.215.126:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //带事务的session
        Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 3; i++) {
            TextMessage message = session.createTextMessage("message--"+i);
            Thread.sleep(1000);
            //通过消息生产者发出消息
            producer.send(message);
        }
        session.commit();
        session.close();
        connection.close();
    }
}
