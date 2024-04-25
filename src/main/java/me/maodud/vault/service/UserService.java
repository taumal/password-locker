package me.maodud.vault.service;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.User;
import me.maodud.vault.model.VerificationToken;
import me.maodud.vault.repository.UserRepository;
import me.maodud.vault.repository.VerificationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final VerificationTokenRepository tokenRepository;

    public User createUser(User user) {
        // Hash user's password before saving
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return repository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

}
