package com.ticketsystem.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    private static final String GMAIL_USER = "srous3002@gmail.com";
    private static final String GMAIL_APP_PASSWORD = "ltfwcfwoifwkifmm";

    public static void enviar(String destino, String asunto, String cuerpo, boolean html)
            throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL_USER, GMAIL_APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(GMAIL_USER));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destino)
        );
        message.setSubject(asunto);

        if (html) {
            message.setContent(cuerpo, "text/html; charset=UTF-8");
        } else {
            message.setText(cuerpo);
        }

        Transport.send(message);
        System.out.println("✅ Correo enviado por Gmail a " + destino);
    }
}
