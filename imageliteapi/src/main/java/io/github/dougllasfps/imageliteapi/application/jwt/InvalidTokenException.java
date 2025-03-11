package io.github.dougllasfps.imageliteapi.application.jwt;

// Define uma classe de exceção personalizada que estende RuntimeException.
public class InvalidTokenException extends RuntimeException {

    // Construtor que aceita uma mensagem de erro e a passa para o construtor da superclasse.
    public InvalidTokenException(String message) {
        super(message);
    }
}
