package io.github.dougllasfps.imageliteapi.config.filter;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.application.jwt.InvalidTokenException;
import io.github.dougllasfps.imageliteapi.application.jwt.JwtService;
import io.github.dougllasfps.imageliteapi.domain.entity.User;
import io.github.dougllasfps.imageliteapi.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Anota a classe como exigindo um construtor com todas as dependências obrigatórias e gera um logger.
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    // Declaração das dependências que serão injetadas.
    private final JwtService jwtService;
    private final UserService userService;

    // Método que processa cada requisição HTTP e aplica o filtro JWT.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Obtém o token da requisição.
        String token = getToken(request);

        // Se o token não for nulo, tenta validar e autenticar o usuário.
        if(token != null){
            try {
                String email = jwtService.getEmailFromToken(token); // Obtém o email a partir do token.
                User user = userService.getByEmail(email); // Busca o usuário pelo email.
                setUserAsAuthenticated(user); // Define o usuário como autenticado.
            } catch (InvalidTokenException e) {
                log.error("Token inválido: {} ", e.getMessage()); // Registra o erro de token inválido.
            } catch (Exception e) {
                log.error("Erro na validação do token: {} ", e.getMessage()); // Registra outros erros de validação.
            }
        }

        // Continua a cadeia de filtros.
        filterChain.doFilter(request, response);
    }

    // Método privado para definir o usuário como autenticado.
    private void setUserAsAuthenticated(User user){
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication); // Define a autenticação no contexto de segurança.
    }

    // Método privado para obter o token da requisição HTTP.
    private String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null){
            String[] authHeaderParts = authHeader.split(" ");
            if(authHeaderParts.length == 2){
                return authHeaderParts[1]; // Retorna o token JWT.
            }
        }
        return null; // Retorna null se o token não estiver presente no cabeçalho.
    }

    // Método que determina se o filtro deve ser aplicado a determinada requisição.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("/v1/users"); // Não aplica o filtro para requisições aos endpoints de usuários.
    }
}
