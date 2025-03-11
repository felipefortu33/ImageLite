package io.github.dougllasfps.imageliteapi.domain.enums;

// Importa as classes necessárias.
import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.Arrays;

// Define uma enumeração para representar diferentes extensões de imagem.
public enum ImageExtension {

    // Define as constantes da enumeração e associa cada uma a um tipo de mídia.
    PNG(MediaType.IMAGE_PNG),
    GIF(MediaType.IMAGE_GIF),
    JPEG(MediaType.IMAGE_JPEG);

    // Anotação do Lombok para gerar automaticamente o getter para mediaType.
    @Getter
    private final MediaType mediaType;

    // Construtor para inicializar o tipo de mídia para cada constante da enumeração.
    ImageExtension(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    // Método estático para obter uma extensão de imagem a partir de um tipo de mídia.
    public static ImageExtension valueOf(MediaType mediaType) {
        // Procura a constante da enumeração que corresponde ao tipo de mídia fornecido.
        return Arrays.stream(values())
                .filter(ie -> ie.mediaType.equals(mediaType))
                .findFirst()
                .orElse(null);
    }

    // Método estático para obter uma extensão de imagem a partir do nome da extensão.
    public static ImageExtension ofName(String name) {
        // Procura a constante da enumeração que corresponde ao nome fornecido.
        return Arrays.stream(values())
                .filter(ie -> ie.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
