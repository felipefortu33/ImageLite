package io.github.dougllasfps.imageliteapi.domain.entity;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Anota a classe como uma entidade JPA e define a tabela correspondente.
@Entity
@Table
// Configura a classe para usar o AuditingEntityListener do Spring Data JPA para auditoria automática.
@EntityListeners(AuditingEntityListener.class)
// Anota a classe com @Data do Lombok para gerar getters, setters, toString, equals, e hashCode.
@Data
// Anota a classe com @NoArgsConstructor do Lombok para gerar um construtor sem argumentos.
@NoArgsConstructor
// Anota a classe com @AllArgsConstructor do Lombok para gerar um construtor com todos os argumentos.
@AllArgsConstructor
// Anota a classe com @Builder do Lombok para gerar um padrão de projeto builder.
@Builder
public class Image {

    // Declara o campo id como chave primária e configura a geração automática de valores UUID.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Declara o campo name como uma coluna na tabela.
    @Column
    private String name;

    // Declara o campo size como uma coluna na tabela.
    @Column
    private Long size;

    // Declara o campo extension como uma coluna enumerada na tabela.
    @Column
    @Enumerated(EnumType.STRING)
    private ImageExtension extension;

    // Declara o campo uploadDate como uma coluna na tabela, e o anota com @CreatedDate para auditoria automática.
    @Column
    @CreatedDate
    private LocalDateTime uploadDate;

    // Declara o campo tags como uma coluna na tabela.
    @Column
    private String tags;

    // Declara o campo file como uma coluna do tipo Lob (Large Object) na tabela.
    @Column
    @Lob
    private byte[] file;

    // Método para obter o nome do arquivo concatenando o nome e a extensão.
    public String getFileName(){
        return getName().concat(".").concat(getExtension().name());
    }
}
