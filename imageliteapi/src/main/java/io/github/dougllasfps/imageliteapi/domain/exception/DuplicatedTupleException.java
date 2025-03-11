package io.github.dougllasfps.imageliteapi.domain.exception;

// Define uma classe de exceção personalizada que estende RuntimeException.
public class DuplicatedTupleException extends RuntimeException{

    // Construtor que aceita uma mensagem de erro e a passa para o construtor da superclasse.
    public DuplicatedTupleException(String message) {
        super(message);
    }
}
