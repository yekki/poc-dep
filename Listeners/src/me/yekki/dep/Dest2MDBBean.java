package me.yekki.dep;


import java.util.logging.Logger;

import javax.ejb.MessageDriven;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name="dest2")
public class Dest2MDBBean implements MessageListener {
    private Logger logger = Logger.getLogger(
                "me.yekki.dep.Dest2MDBBean");
    
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage)message;
            logger.info("###### Dest2 got message:" + msg.getText() + " ######");
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
