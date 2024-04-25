package me.maodud.vault.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String recipientName, String recipientAddress, String subject, String token, String confirmationUrl) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("no-reply@caredocker.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
        message.setSubject(subject);


        StringBuilder content = new StringBuilder();

        content.append("<h3>Hello ").append(recipientName).append("</h3>");
        content.append("<p>You registered an account on \"Elderly Care Solution\", before being able to use your account you need to verify that this is your email address by clicking here:\n");
        content.append("<a href=\"").append(confirmationUrl).append("\">").append(confirmationUrl).append("</a></p>");
        message.setContent(content.toString(), "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
