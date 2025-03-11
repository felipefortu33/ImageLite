package io.github.dougllasfps.imageliteapi.domain.service;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;

// Define uma interface de serviço para operações relacionadas a imagens.
public interface ImageService {

    // Método para salvar uma imagem. Retorna a imagem salva.
    Image save(Image image);

    // Método para obter uma imagem pelo ID. Retorna um Optional para lidar com valores nulos.
    Optional<Image> getById(String id);

    // Método para buscar imagens por extensão e consulta. Retorna uma lista de imagens.
    List<Image> search(ImageExtension extension, String query);
}
