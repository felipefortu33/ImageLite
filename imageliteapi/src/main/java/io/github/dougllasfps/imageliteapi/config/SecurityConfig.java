package io.github.dougllasfps.imageliteapi.config;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.application.jwt.JwtService;
import io.github.dougllasfps.imageliteapi.config.filter.JwtFilter;
import io.github.dougllasfps.imageliteapi.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Anota a classe como uma configuração de segurança Spring.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define um bean para o JwtFilter, que será usado na cadeia de filtros de segurança.
    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, UserService userService){
        return new JwtFilter(jwtService, userService);
    }

    // Define um bean para o PasswordEncoder, usando BCrypt para criptografar senhas.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Define a cadeia de filtros de segurança.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desativa a proteção contra CSRF.
                .cors(cors -> cors.configure(http)) // Configura o CORS.
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v1/users/**").permitAll(); // Permite todas as requisições para /v1/users/**.
                    auth.requestMatchers(HttpMethod.GET, "/v1/images/**").permitAll(); // Permite todas as requisições GET para /v1/images/**.
                    auth.anyRequest().authenticated(); // Requer autenticação para todas as outras requisições.
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o JwtFilter antes do UsernamePasswordAuthenticationFilter.
                .build(); // Constrói a cadeia de filtros.
    }

    // Define a configuração de CORS.
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", config); // Aplica a configuração de CORS a todas as requisições.

        return cors; // Retorna a configuração de CORS.
    }
}
