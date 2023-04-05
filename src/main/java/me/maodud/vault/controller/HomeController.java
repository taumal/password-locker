package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.service.FolderService;
import me.maodud.vault.service.LoginCredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final LoginCredentialService credentialService;
    private final FolderService folderService;
    @GetMapping("")
    public String getFolders(Model model) {
        model.addAttribute("credentials", credentialService.getAllCredentials());
        model.addAttribute("folders", folderService.getAllFolderList());
        return "welcome";
    }
}
