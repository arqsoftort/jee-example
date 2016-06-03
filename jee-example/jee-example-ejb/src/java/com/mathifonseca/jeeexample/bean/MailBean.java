package com.mathifonseca.jeeexample.bean;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
@LocalBean
public class MailBean {

    public void send(String to, String subject, String body) {
        System.out.println(String.format("Sending message: to=%s subject=%s body=%s", to, subject, body));
        //uncomment after changing to valid mail, username and password
        //realSend(to, subject, body);
    }
    
    //for gmail, enable -> https://www.google.com/settings/security/lesssecureapps
    private void realSend(String to, String subject, String body) {
        final String from = "mail@mail.com";
        final String username = "username";
        final String password = "password";
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(username, password);
                }
            });
        try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(from));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
           message.setSubject(subject);
           message.setText(body);
           Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
