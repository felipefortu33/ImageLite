package io.github.dougllasfps.imageliteapi.domain.service;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.AccessToken;
import io.github.dougllasfps.imageliteapi.domain.entity.User;

// Define uma interface de serviço para operações relacionadas a usuários.
public interface UserService {

    // Método para buscar um usuário pelo email. Retorna o usuário correspondente.
    User getByEmail(String email);

    // Método para salvar um usuário. Retorna o usuário salvo.
    User save(User user);

    // Método para autenticar um usuário com base no email e senha. Retorna um token de acesso se as credenciais forem válidas.
    AccessToken authenticate(String email, String password);
}
