package io.github.dougllasfps.imageliteapi.domain.entity;

// Importa as classes necessárias.
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Anota a classe como uma entidade JPA e define a tabela correspondente com o nome "auth_user".
@Entity
@Table(name = "auth_user")
// Configura a classe para usar o AuditingEntityListener do Spring Data JPA para auditoria automática.
@EntityListeners(AuditingEntityListener.class)
// Anota a classe com @Data do Lombok para gerar getters, setters, toString, equals e hashCode.
@Data
// Anota a classe com @NoArgsConstructor do Lombok para gerar um construtor sem argumentos.
@NoArgsConstructor
// Anota a classe com @AllArgsConstructor do Lombok para gerar um construtor com todos os argumentos.
@AllArgsConstructor
// Anota a classe com @Builder do Lombok para gerar um padrão de projeto builder.
@Builder
public class User {

    // Declara o campo id como chave primária e configura a geração automática de valores UUID.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Declara o campo name como uma coluna na tabela.
    @Column
    private String name;

    // Declara o campo email como uma coluna na tabela.
    @Column
    private String email;

    // Declara o campo password como uma coluna na tabela.
    @Column
    private String password;

    // Declara o campo createdAt como uma coluna na tabela, e o anota com @CreatedDate para auditoria automática.
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
