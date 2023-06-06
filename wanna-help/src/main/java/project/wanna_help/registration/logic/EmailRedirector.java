package project.wanna_help.registration.logic;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailRedirector {
    public void redirectEMail(String userEmail, String link) {
        String sourceEmail = "wanna.help@outlook.com";
        String sourcePassword = "WannaHelp";
        String host = "smtp-mail.outlook.com";
        int port = 587; // Port number for your email host

        // Set the properties for the JavaMail session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create a session with the email account
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sourceEmail, sourcePassword);
            }
        });

        try {
            // Create a new message and set the recipient and sender addresses
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sourceEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

            // Set the subject and content of the email
            message.setSubject("Email Redirection");
            message.setText("This is a redirected email from " + sourceEmail);

            // Redirect the email by sending it
            Transport.send(message);
            System.out.println("Email redirected successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
