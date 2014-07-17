package me.yekki.dep;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name="dest1")
public class Dest1MDBBean implements MessageListener {
    
    private Logger logger = Logger.getLogger(
                "me.yekki.dep.Dest1MDBBean");
    
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage)message;
            logger.info("###### Dest1 got message:" + msg.getText() + " ######");
        }
        catch (JMSException e) {
            
            e.printStackTrace();
        }
    }
}
