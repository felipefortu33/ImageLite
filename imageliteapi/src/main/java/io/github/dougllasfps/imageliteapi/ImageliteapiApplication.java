package io.github.dougllasfps.imageliteapi;

// Importa as classes necessárias.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Anota a classe como uma aplicação Spring Boot.
@SpringBootApplication
// Habilita a auditoria JPA, permitindo o uso de anotações como @CreatedDate.
@EnableJpaAuditing
public class ImageliteapiApplication {

	// Método main que inicia a aplicação Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
