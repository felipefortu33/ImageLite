package io.github.dougllasfps.imageliteapi.application.images;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import io.github.dougllasfps.imageliteapi.domain.service.ImageService;
import io.github.dougllasfps.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Anota a classe como um serviço do Spring e usa o Lombok para gerar o construtor com todas as dependências obrigatórias.
@Service
@RequiredArgsConstructor
class ImageServiceImpl implements ImageService {

    // Declaração da dependência que será injetada.
    private final ImageRepository repository;

    // Método para salvar uma imagem.
    @Override
    @Transactional
    public Image save(Image image) {
        return repository.save(image); // Salva a imagem no repositório e retorna a entidade salva.
    }

    // Método para obter uma imagem pelo ID.
    @Override
    public Optional<Image> getById(String id) {
        return repository.findById(id); // Busca uma imagem pelo ID e retorna um Optional para lidar com valores nulos.
    }

    // Método para buscar imagens por extensão e consulta.
    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return repository.findByExtensionAndNameOrTagsLike(extension, query); // Busca imagens pela extensão e consulta.
    }
}
