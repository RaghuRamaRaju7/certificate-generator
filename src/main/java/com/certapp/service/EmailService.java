package com.certapp.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.Properties;

public class EmailService {

    public static void sendEmailWithAttachment(String to, String filePath) {
        final String from = "iraghuramaraju7@gmail.com"; 
        final String password = "cfmlcfbblycuenpd";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Your Certificate");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hello,\n\nYour certificate is attached.\n\nRegards,\nCertificate Generator Team");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(new File(filePath));
            multipart.addBodyPart(attachment);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
