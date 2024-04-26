package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.maodud.vault.event.OnRegistrationCompleteEvent;
import me.maodud.vault.model.User;
import me.maodud.vault.model.VerificationToken;
import me.maodud.vault.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final MessageSource messages;
    private final UserService service;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;

    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("")
    public String registerUserAccount(@ModelAttribute("user") @Valid User user,
                                      BindingResult result, HttpServletRequest request) {
        log.info(request.getLocale().toString());

        if (result.hasErrors()) {
            return "register";
        }

        // Auto login
        try {
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            User registered = service.createUser(user);
            String appUrl = request.getRequestURL().toString();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
//            authToken.setDetails(new WebAuthenticationDetails(request));
//            Authentication authentication = authenticationManager.authenticate(authToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("Error while " + e.getMessage());
            return "register";
        }

        return "redirect:/registration/verify-email";
    }

    @GetMapping("/verify-email")
    public String confirmRegistration(HttpServletRequest request, Model model, @RequestParam(value = "token", required = false) String token) {
        Locale locale = request.getLocale();
        if (token == null) {
            return "verify-email";
        }

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            return "redirect:/registration/bad-user?lang=" + locale.getLanguage() + "&message=" + message;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        log.info("Expired In: " + (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()));
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            return "redirect:/registration/bad-user?lang=" + locale.getLanguage()+ "&message=" + message + "&expired=" + true + "&token=" + token;
        }
        log.info("email verified");
        user.setEnabled(true);
        service.saveRegisteredUser(user);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));

        authWithoutPassword(user);
        return "redirect:/?success=true";
    }

    @GetMapping("/bad-user")
    public String badUser(Model model, @RequestParam(required = false, value = "message") String message,
                          @RequestParam(required = false, value = "expired") boolean expired,
                          @RequestParam(required = false, value = "token") String token) {
        model.addAttribute("message", message);
        model.addAttribute("expired", expired);
        model.addAttribute("token", token);
        return "error/bad-user";
    }

    public void authWithoutPassword(User user) {
//        List<Privilege> privileges = user.getRoles().stream().map(Role::getPrivileges)
//                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
//        List<GrantedAuthority> authorities = privileges.stream()
//                .map(p -> new SimpleGrantedAuthority(p.getName()))
//                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
