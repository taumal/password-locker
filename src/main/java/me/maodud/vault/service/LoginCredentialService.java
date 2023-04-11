package me.maodud.vault.service;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.enums.Type;
import me.maodud.vault.exception.ResourceNotFoundException;
import me.maodud.vault.model.Folder;
import me.maodud.vault.model.LoginCredential;
import me.maodud.vault.repository.FolderRepository;
import me.maodud.vault.repository.LoginCredentialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginCredentialService {
    private final LoginCredentialRepository repository;
    private final FolderRepository folderRepository;

    public LoginCredential createCredential(LoginCredential credential, Long folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder not found"));
        credential.setFolder(folder);
        return repository.save(credential);
    }

    public List<LoginCredential> getAllCredentials() {
        return repository.findAll();
    }

    public LoginCredential getCredentialById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Login Credential not found"));
    }

    public LoginCredential updateCredential(Long id, LoginCredential updatedCredential) {
        LoginCredential credential = getCredentialById(id);
        credential.setName(updatedCredential.getName());
        credential.setUsername(updatedCredential.getUsername());
        credential.setPassword(updatedCredential.getPassword());
        credential.setUrl(updatedCredential.getUrl());
        credential.setNotes(updatedCredential.getNotes());
        return repository.save(credential);
    }

    public void deleteCredential(Long id) {
        LoginCredential credential = getCredentialById(id);
        repository.delete(credential);
    }

    public List<LoginCredential> getCredentialsByType(Type type) {
        return repository.findByType(type);
    }

    public List<LoginCredential> getCredentialsByFolderId(Long folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder not found"));
        return repository.findByFolder(folder);
    }

    public List<LoginCredential> saveAllCredentials(List<LoginCredential> credentials) {
        return repository.saveAll(credentials);
    }
}
