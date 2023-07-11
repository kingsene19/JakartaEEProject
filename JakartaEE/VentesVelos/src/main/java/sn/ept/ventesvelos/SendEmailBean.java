package sn.ept.ventesvelos;

import jakarta.ejb.Stateless;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Stateless
public class SendEmailBean {
    private static final String smtpHost = "smtp.gmail.com";
    private static final int smtpPort = 587;
    private static final String username = "tunknowed@gmail.com";
    private static final String password = "lvdgrtzpsvcjptzg";

    public void sendEmail(String destinataire, String sujet, String contenu){
        // Ajout des paramètres du serveur SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", smtpHost);

        // Création d'une session avec authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);
            // Envoi du message
            Transport.send(message);
            System.out.println("Le message a été envoyé avec succès");
        } catch (MessagingException e) {
            // System.out.println("Le message n'a pas pu être envoyé " + e.getMessage());
            e.printStackTrace();
        }

    }
}
