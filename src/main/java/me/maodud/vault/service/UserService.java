package me.maodud.vault.service;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.User;
import me.maodud.vault.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(User user) {
        // Hash user's password before saving
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return repository.save(user);
    }

}
