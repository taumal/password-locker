package me.maodud.vault.controller.api.v1;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.LoginCredential;
import me.maodud.vault.service.FolderService;
import me.maodud.vault.service.LoginCredentialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/login/credentials")
@RequiredArgsConstructor
public class LoginCredentialAPIController {
    private final LoginCredentialService service;
    private final FolderService folderService;

    @GetMapping
    public List<LoginCredential> getAllCredentials() {
        return service.getAllCredentials();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public LoginCredential getCredentialById(@PathVariable Long id) {
        return service.getCredentialById(id);
    }

    @PostMapping
    public LoginCredential createCredential(@RequestBody LoginCredential credential) {
        return service.createCredential(credential, credential.getFolder().getId());
    }

    @PutMapping("/{id}")
    public LoginCredential updateCredential(@PathVariable Long id, @RequestBody LoginCredential credentialDto) {
        return service.updateCredential(id, credentialDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCredential(@PathVariable Long id) {
        service.deleteCredential(id);
    }
}
