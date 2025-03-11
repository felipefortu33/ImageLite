package io.github.dougllasfps.imageliteapi.application.images;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// Anota a classe como um componente Spring, permitindo que seja detectada e gerenciada automaticamente pelo Spring.
@Component
public class ImageMapper {

    // Método para mapear um MultipartFile para a entidade Image.
    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        // Obtém o tipo de conteúdo do arquivo.
        String contentType = file.getContentType();
        ImageExtension extension;

        // Verifica o tipo de conteúdo e define a extensão da imagem.
        if (MediaType.IMAGE_JPEG_VALUE.equals(contentType)) {
            extension = ImageExtension.JPEG;
        } else if (MediaType.IMAGE_PNG_VALUE.equals(contentType)) {
            extension = ImageExtension.PNG;
        } else {
            // Lança uma exceção se o tipo de arquivo não for suportado.
            throw new IllegalArgumentException("Tipo de arquivo não suportado: " + contentType);
        }

        // Constrói e retorna uma instância de Image usando o builder do Lombok.
        return Image.builder()
                .name(name) // Define o nome da imagem.
                .tags(String.join(",", tags)) // Concatena as tags em uma única string separada por vírgulas.
                .size(file.getSize()) // Define o tamanho do arquivo.
                .extension(extension) // Define a extensão da imagem.
                .file(file.getBytes()) // Define o conteúdo do arquivo como um array de bytes.
                .build(); // Constrói a instância de Image.
    }

    // Método para mapear uma entidade Image para um DTO ImageDTO.
    public ImageDTO imageToDTO(Image image, String url) {
        // Constrói e retorna uma instância de ImageDTO usando o builder do Lombok.
        return ImageDTO.builder()
                .url(url) // Define a URL da imagem.
                .extension(image.getExtension().name()) // Define a extensão da imagem como uma string.
                .name(image.getName()) // Define o nome da imagem.
                .size(image.getSize()) // Define o tamanho da imagem.
                .uploadDate(image.getUploadDate().toLocalDate()) // Converte a data de upload para LocalDate e define.
                .build(); // Constrói a instância de ImageDTO.
    }
}
