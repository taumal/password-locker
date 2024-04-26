package me.maodud.vault.service;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.exception.EmailExistsException;
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

    public User createUser(User user) throws EmailExistsException {
        if (userExist(user.getUsername())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            + user.getUsername()
            );
        }
        // Hash user's password before saving
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return repository.save(user);
    }

    private boolean userExist(String email) {
        return repository.findByUsername(email) != null;
    }

    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void saveRegisteredUser(User user) {
        repository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

}
