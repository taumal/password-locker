package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.exception.EmailExistsException;
import me.maodud.vault.model.User;
import me.maodud.vault.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;

    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("")
    public String registerUserAccount(@ModelAttribute("user") @Valid User user,
                                      BindingResult result, HttpServletRequest request) throws EmailExistsException {
        if (result.hasErrors()) {
            return "register";
        }
        service.createUser(user);
        return "redirect:/login?success=true";
    }
}
