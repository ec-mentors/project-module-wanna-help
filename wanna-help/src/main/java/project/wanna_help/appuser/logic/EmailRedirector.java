package project.wanna_help.appuser.logic;


import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailRedirector {

    public void redirectEMail(String nameOrEmail, String subject, String content) {
        String sourceEmail = "wanna.help@outlook.com";
        String sourcePassword = System.getenv("PROJECT_EMAIL_PASSWORD");
        String host = "smtp-mail.outlook.com";
        int port = 587;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sourceEmail, sourcePassword);
            }
        });

        try {
            // Create a new message and set the recipient and sender addresses
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sourceEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(nameOrEmail));

            // Set the subject and content of the email
            message.setSubject(subject);
            message.setText(content);

            // Redirect the email by sending it
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
