package me.maodud.vault.component;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.event.OnRegistrationCompleteEvent;
import me.maodud.vault.model.User;
import me.maodud.vault.service.EmailService;
import me.maodud.vault.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientName = user.getName();
        String recipientAddress = user.getUsername();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/verify-email?token=" + token;
        emailService.sendMail(recipientName, recipientAddress, subject, token, confirmationUrl);
    }
}
