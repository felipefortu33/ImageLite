package io.github.dougllasfps.imageliteapi.application.users;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.application.jwt.JwtService;
import io.github.dougllasfps.imageliteapi.domain.AccessToken;
import io.github.dougllasfps.imageliteapi.domain.entity.User;
import io.github.dougllasfps.imageliteapi.domain.exception.DuplicatedTupleException;
import io.github.dougllasfps.imageliteapi.domain.service.UserService;
import io.github.dougllasfps.imageliteapi.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Anota a classe como um serviço do Spring e usa o Lombok para gerar o construtor com todas as dependências obrigatórias.
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Declaração das dependências que serão injetadas.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Método para buscar um usuário pelo email.
    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Método para salvar um usuário, garantindo que não exista duplicação.
    @Override
    @Transactional
    public User save(User user) {
        var possibleUser = getByEmail(user.getEmail());
        if(possibleUser != null){
            throw new DuplicatedTupleException("User already exists!"); // Lança uma exceção se o usuário já existir.
        }
        encodePassword(user); // Codifica a senha antes de salvar.
        return userRepository.save(user); // Salva o usuário no repositório.
    }

    // Método para autenticar um usuário e gerar um token de acesso.
    @Override
    public AccessToken authenticate(String email, String password) {
        var user = getByEmail(email);
        if(user == null){
            return null; // Retorna null se o usuário não for encontrado.
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword()); // Verifica se a senha corresponde.

        if(matches){
            return jwtService.generateToken(user); // Gera e retorna um token de acesso se a senha for correta.
        }

        return null; // Retorna null se a senha não corresponder.
    }

    // Método privado para codificar a senha de um usuário.
    private void encodePassword(User user){
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword); // Define a senha codificada no usuário.
    }
}
