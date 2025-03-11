package io.github.dougllasfps.imageliteapi.application.users;

// A anotação Lombok @Data gera automaticamente getters, setters, toString, equals e hashCode.
import lombok.Data;

// Define uma classe DTO (Data Transfer Object) para transportar dados de credenciais.
@Data
public class CredentialsDTO {

    // Campo para armazenar o email do usuário.
    private String email;

    // Campo para armazenar a senha do usuário.
    private String password;
}
