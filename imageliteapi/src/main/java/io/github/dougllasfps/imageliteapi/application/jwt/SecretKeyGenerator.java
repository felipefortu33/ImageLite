package io.github.dougllasfps.imageliteapi.application.jwt;

// Importa a classe Keys do pacote io.jsonwebtoken.security para geração de chaves.
import io.jsonwebtoken.security.Keys;
// Importa a anotação @Component do Spring Framework.
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

// Anota a classe como um componente Spring, permitindo que seja detectada e gerenciada automaticamente pelo Spring.
@Component
public class SecretKeyGenerator {

    // Declaração de uma chave secreta.
    private SecretKey key;

    // Método para obter a chave secreta.
    public SecretKey getKey(){
        // Gera uma nova chave secreta se ainda não tiver sido gerada.
        if(key == null){
            key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        }
        return key; // Retorna a chave secreta.
    }
}
