package me.yekki.dep;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;

import javax.ejb.MessageDriven;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "me.yekki.dep.inbound")
public class DispatcherBean implements MessageListener {
    @Resource(mappedName="me.yekki.dep.connection_factory")
    private ConnectionFactory connectionFactory;
    
    @Resource(name = "me.yekki.dep.outbound.1")
    private Destination dest1;
    
    @Resource(name = "me.yekki.dep.outbound.2")
    private Destination dest2;
    
    private Connection connection;
    
    
    @PostConstruct
    public void init(){
        try {
            connection = connectionFactory.createConnection();
        }catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    @PreDestroy
    public void post(){
        try {
            connection.close();
        }catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onMessage(Message message) {
        
        try {
            TextMessage msg = null;
            
            if (message instanceof TextMessage) {
                msg = (TextMessage)message;
            }
            else {
                throw new JMSException("unsupported jms type.");
            }
            
            String policy = message.getStringProperty("router");
            
            if (policy == null) {
                throw new JMSException("no routing policy setting.");
            }
            else if (policy.equals("all")) {
                send(dest1, msg);
                send(dest2, msg);
            }
            else {
                switch (policy) {
                    case "dest1":
                        send(dest1, msg);
                        break;
                    case "dest2":
                        send(dest2, msg);
                        break;
                    default:
                        throw new JMSException("unrecognized route policy:" + policy);
                }
            }
        }
        catch (JMSException e) {
            
            e.printStackTrace();
        }
    }
    
    private void send(Destination dest, TextMessage message) throws JMSException {
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageProducer producer = session.createProducer(dest);
        final TextMessage msg = session.createTextMessage(message.getText());
        producer.send(msg);
        session.close();
    }
}
