package io.github.dougllasfps.imageliteapi.application.users;

// Importa a entidade User do domínio.
import io.github.dougllasfps.imageliteapi.domain.entity.User;
// Importa a anotação @Component do Spring Framework.
import org.springframework.stereotype.Component;

// Marca a classe como um componente Spring, permitindo que seja detectada e gerenciada automaticamente pelo Spring.
@Component
public class UserMapper {

    // Método para mapear um UserDTO para uma entidade User.
    public User mapToUser(UserDTO dto) {
        // Cria uma nova instância de User usando o builder do Lombok e os dados do DTO.
        return User.builder()
                .email(dto.getEmail()) // Define o email do User com o email do DTO.
                .name(dto.getName()) // Define o nome do User com o nome do DTO.
                .password(dto.getPassword()) // Define a senha do User com a senha do DTO.
                .build(); // Constrói a instância de User.
    }
}
