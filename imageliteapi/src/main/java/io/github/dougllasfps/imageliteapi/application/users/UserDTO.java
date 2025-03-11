package io.github.dougllasfps.imageliteapi.application.users;

// Importa a anotação @Data do Lombok, que gera automaticamente métodos comuns como getters, setters, toString, equals e hashCode
import lombok.Data;

// Define uma classe DTO (Data Transfer Object) para transportar dados de usuário
@Data
public class UserDTO {

    // Campo para armazenar o nome do usuário
    private String name;

    // Campo para armazenar o email do usuário
    private String email;

    // Campo para armazenar a senha do usuário
    private String password;
}
