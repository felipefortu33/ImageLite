package io.github.dougllasfps.imageliteapi.application.users;

// Importações necessárias para a classe
import io.github.dougllasfps.imageliteapi.domain.entity.User;
import io.github.dougllasfps.imageliteapi.domain.exception.DuplicatedTupleException;
import io.github.dougllasfps.imageliteapi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Anotação que define a classe como um controlador REST do Spring
@RestController
// Define o mapeamento base de todas as requisições que serão tratadas por este controlador
@RequestMapping("/v1/users")
// Lombok cria um construtor com todos os campos finais necessários
@RequiredArgsConstructor
public class UserController {

    // Injeção de dependências para UserService e UserMapper
    private final UserService userService;
    private final UserMapper userMapper;

    // Endpoint POST para salvar um novo usuário
    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto){
        try {
            // Mapeia o UserDTO para a entidade User e salva
            User user = userMapper.mapToUser(dto);
            userService.save(user);
            // Retorna o status 201 Created se o usuário for salvo com sucesso
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicatedTupleException e) {
            // Trata a exceção de duplicação e retorna o status 409 Conflict com a mensagem de erro
            Map<String, String> jsonResultado = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResultado);
        }
    }

    // Endpoint POST para autenticar um usuário
    @PostMapping("/auth")
    public ResponseEntity authenticate(@RequestBody CredentialsDTO credentials) {
        // Autentica o usuário com base nas credenciais fornecidas
        var token = userService.authenticate(credentials.getEmail(), credentials.getPassword());

        // Se o token for nulo, retorna o status 401 Unauthorized
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Se a autenticação for bem-sucedida, retorna o token de acesso
        return ResponseEntity.ok(token);
    }
}
