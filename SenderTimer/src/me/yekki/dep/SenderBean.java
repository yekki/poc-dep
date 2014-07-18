package me.yekki.dep;

import java.util.Date;

import javax.annotation.Resource;

import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import java.util.Date;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import javax.annotation.PreDestroy;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;

import javax.jms.Destination;
import javax.jms.JMSException;

import javax.jms.MessageProducer;
import javax.jms.Session;

import javax.jms.TextMessage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Singleton
public class SenderBean {
 
    private Logger logger = Logger.getLogger(
                "me.yekki.dep.SenderBean");
    
    private Context ctx;
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Destination dest;
    private String message = "Hello Wisdom!";
    
    Random random = new Random(1);
    
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        
        try {
            Hashtable ht = new Hashtable();   
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");   
            ht.put(Context.PROVIDER_URL, "t3://10.10.198.231:7001");
            ht.put(Context.SECURITY_PRINCIPAL, "weblogic");
            ht.put(Context.SECURITY_CREDENTIALS, "welcome1");
            ctx = new InitialContext(ht);
            
            factory = (ConnectionFactory)ctx.lookup("me.yekki.dep.connection_factory");
            dest = (Destination)ctx.lookup("me.yekki.dep.inbound");
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(dest);
        }
        catch (NamingException ne) {
            ne.printStackTrace();
        }
        catch (JMSException je) {
            je.printStackTrace();
        }
    }
    
    @PreDestroy
    public void post() {
        
        try {
            producer.close();
            session.close();
            connection.close();
            ctx.close();
        }
        catch (NamingException ne) {
            ne.printStackTrace();
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    @Schedule(hour="*", minute="*", second="*/5")
    public void send() {
        
        try {
            String policy = null;
            
            switch (1 + random.nextInt(3)) {
            case 1:
                policy = "dest1";
                break;
            case 2:
                policy = "dest2";
                break;
            case 3:
                policy = "all";
                break;
            }
            TextMessage msg = session.createTextMessage(message);
            msg.setStringProperty("router", policy);
            producer.send(msg);
            logger.info("### message sent! policy=" + policy + " ###");
        }
        catch (JMSException e) {
            
            e.printStackTrace();
        }
    }
}
