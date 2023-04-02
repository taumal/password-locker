package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.LoginCredential;
import me.maodud.vault.service.FolderService;
import me.maodud.vault.service.LoginCredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/login/credentials")
@RequiredArgsConstructor
public class LoginCredentialController {
    private final LoginCredentialService service;
    private final FolderService folderService;

    @GetMapping("")
    public String listLoginCredential(Model model) {
        List<LoginCredential> credentials = service.getAllCredentials();
        model.addAttribute("credentials", credentials);
        return "login-credential/list";
    }

    @GetMapping("/new")
    public String newCredential(Model model) {
        model.addAttribute("credential", new LoginCredential());
        model.addAttribute("folders", folderService.getAllFolderList());
        return "login-credential/form";
    }

}