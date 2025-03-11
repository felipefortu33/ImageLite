package io.github.dougllasfps.imageliteapi.application.jwt;

import io.github.dougllasfps.imageliteapi.domain.AccessToken;
import io.github.dougllasfps.imageliteapi.domain.entity.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Anota a classe como um serviço do Spring e usa o Lombok para gerar o construtor com todas as dependências obrigatórias.
@Service
@RequiredArgsConstructor
public class JwtService {

    // Declaração das dependências que serão injetadas.
    private final SecretKeyGenerator keyGenerator;

    // Método para gerar um token JWT para um usuário.
    public AccessToken generateToken(User user){

        // Obtém a chave secreta.
        var key = keyGenerator.getKey();
        // Gera a data de expiração do token.
        var expirationDate = generateExpirationDate();
        // Gera as claims (informações adicionais) do token.
        var claims = generateTokenClaims(user);

        // Cria e assina o token JWT.
        String token = Jwts
                .builder()
                .signWith(key) // Assina o token com a chave secreta.
                .subject(user.getEmail()) // Define o assunto do token como o email do usuário.
                .expiration(expirationDate) // Define a data de expiração do token.
                .claims(claims) // Adiciona as claims ao token.
                .compact(); // Constrói o token.

        return new AccessToken(token); // Retorna um novo objeto AccessToken com o token gerado.
    }

    // Método para gerar a data de expiração do token.
    private Date generateExpirationDate(){
        var expirationMinutes = 60; // Define o tempo de expiração em minutos.
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes); // Calcula a data de expiração.
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant()); // Converte LocalDateTime para Date.
    }

    // Método para gerar as claims (informações adicionais) do token.
    private Map<String, Object> generateTokenClaims(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName()); // Adiciona o nome do usuário às claims.
        return claims;
    }

    // Método para obter o email do usuário a partir do token JWT.
    public String getEmailFromToken(String tokenJwt){
        try {
            // Cria um parser para o token JWT.
            JwtParser build = Jwts.parser()
                    .verifyWith(keyGenerator.getKey()) // Define a chave para verificar a assinatura do token.
                    .build();

            // Analisa o token JWT e obtém as claims.
            Jws<Claims> jwsClaims = build.parseSignedClaims(tokenJwt);
            Claims claims = jwsClaims.getPayload();
            return claims.getSubject(); // Retorna o assunto (email do usuário) das claims.

        }catch (JwtException e){
            // Lança uma exceção personalizada se o token for inválido.
            throw new InvalidTokenException(e.getMessage());
        }
    }
}
