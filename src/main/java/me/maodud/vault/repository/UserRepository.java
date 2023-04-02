package me.maodud.vault.repository;

import me.maodud.vault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String email);
}
