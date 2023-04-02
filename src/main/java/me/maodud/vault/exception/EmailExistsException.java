package me.maodud.vault.exception;

public class EmailExistsException extends Exception {
    public EmailExistsException(String email) {
        super("Email already exists: " + email);
    }
}
