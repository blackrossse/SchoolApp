package com.example.school.javacode;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSenderClass extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    private Multipart _multipart;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public MailSenderClass(String user, String password) {
        this.user = user;
        this.password = password;

        _multipart = new MimeMultipart();

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.enable", "enable");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients)
            throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);

            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);

            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipients));

            message.setText(body);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

