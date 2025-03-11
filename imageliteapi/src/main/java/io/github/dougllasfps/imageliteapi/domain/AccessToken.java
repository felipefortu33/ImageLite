package io.github.dougllasfps.imageliteapi.domain;

// Importa as anotações necessárias do Lombok.
import lombok.AllArgsConstructor;
import lombok.Data;

// Anotação @Data do Lombok para gerar getters, setters, toString, equals e hashCode.
@Data
// Anotação @AllArgsConstructor do Lombok para gerar um construtor com todos os argumentos.
@AllArgsConstructor
public class AccessToken {
    // Campo para armazenar o token de acesso.
    private String accessToken;
}
