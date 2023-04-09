package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.enums.Type;
import me.maodud.vault.model.LoginCredential;
import me.maodud.vault.service.FolderService;
import me.maodud.vault.service.LoginCredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        model.addAttribute("folders", folderService.getAllFolderList());
        return "welcome";
    }

    @GetMapping("/new")
    public String newCredential(Model model) {
        model.addAttribute("credential", new LoginCredential());
        model.addAttribute("folders", folderService.getAllFolderList());
        return "login-credential/form";
    }

    @PostMapping("")
    public String saveFolder(LoginCredential credential, BindingResult result) {
        try {
            if (!result.hasErrors()) {
                service.createCredential(credential, credential.getFolder().getId());
                System.out.println("created");
            } else {
                System.out.println("not created");
            }
        } catch (Exception e) {
            System.out.println("not created. cause: " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/folder")
    public String getCredentialByFolder(@RequestParam("id") Long id, Model model) {
        List<LoginCredential> credentialsByFolderId = service.getCredentialsByFolderId(id);
        model.addAttribute("credentials", credentialsByFolderId);
        model.addAttribute("folders", folderService.getAllFolderList());
        return "welcome";
    }

    @GetMapping("/types")
    public String getCredentialByType(@RequestParam("type") Type type, Model model) {
        List<LoginCredential> credentialsByType = service.getCredentialsByType(type);
        model.addAttribute("credentials", credentialsByType);
        model.addAttribute("folders", folderService.getAllFolderList());
        return "welcome";
    }

    @ModelAttribute("typeMap")
    public Map<String, String> getTypeMap() {
        return Type.getTypeMap();
    }

}
