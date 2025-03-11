package io.github.dougllasfps.imageliteapi.application.images;

// Importa as anotações @Builder e @Data do Lombok, que geram automaticamente métodos comuns e um construtor de "Builder".
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

// Anotação @Data do Lombok, que gera automaticamente getters, setters, toString, equals e hashCode.
@Data
// Anotação @Builder do Lombok, que gera um construtor de "Builder" para a classe.
@Builder
public class ImageDTO {
    // Campo para armazenar a URL da imagem.
    private String url;
    // Campo para armazenar o nome da imagem.
    private String name;
    // Campo para armazenar a extensão da imagem (por exemplo, PNG, JPEG).
    private String extension;
    // Campo para armazenar o tamanho da imagem em bytes.
    private Long size;
    // Campo para armazenar a data de upload da imagem.
    private LocalDate uploadDate;
}
