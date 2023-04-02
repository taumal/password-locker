package me.maodud.vault.repository;

import me.maodud.vault.model.Folder;
import me.maodud.vault.model.LoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginCredentialRepository  extends JpaRepository<LoginCredential, Long> {
    List<LoginCredential> findByFolder(Folder folder);
}
