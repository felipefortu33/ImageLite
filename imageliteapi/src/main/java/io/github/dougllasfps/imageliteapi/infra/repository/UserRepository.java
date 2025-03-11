package io.github.dougllasfps.imageliteapi.infra.repository;

// Importa a classe User do domínio e JpaRepository do Spring Data JPA.
import io.github.dougllasfps.imageliteapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Define uma interface de repositório para a entidade User, estendendo JpaRepository.
public interface UserRepository extends JpaRepository<User, String> {

    // Declaração de um método para buscar um usuário pelo email.
    User findByEmail(String email);
}
