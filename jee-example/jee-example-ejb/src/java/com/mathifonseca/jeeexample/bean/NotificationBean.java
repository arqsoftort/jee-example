package com.mathifonseca.jeeexample.bean;

import com.mathifonseca.jeeexample.dto.BookDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Queue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NotificationBean implements MessageListener {
    
    @EJB
    private MailBean mailBean;
    
    public NotificationBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            BookDto book = (BookDto) ((ObjectMessage) message).getObject();
            mailBean.send("someone@gmail.com", "New book!", book.getName());
        } catch (JMSException ex) {
            Logger.getLogger(NotificationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
