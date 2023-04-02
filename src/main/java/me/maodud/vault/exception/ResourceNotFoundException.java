package me.maodud.vault.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String entityName, String attributeName, Object attributeValue) {
        super(String.format("%s not found with %s : '%s'", entityName, attributeName, attributeValue));
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

