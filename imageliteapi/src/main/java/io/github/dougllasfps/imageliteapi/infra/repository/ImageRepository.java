package io.github.dougllasfps.imageliteapi.infra.repository;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.github.dougllasfps.imageliteapi.infra.repository.specs.GenericSpecs.conjunction;
import static io.github.dougllasfps.imageliteapi.infra.repository.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

// Define uma interface de repositório para a entidade Image, estendendo JpaRepository e JpaSpecificationExecutor.
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    // Método padrão para buscar imagens por extensão e consulta.
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        // Inicia uma especificação com conjunção.
        Specification<Image> spec = where(conjunction());

        // Adiciona uma especificação para a extensão se a extensão não for nula.
        if (extension != null) {
            spec = spec.and(extensionEqual(extension));
        }

        // Adiciona especificações para o nome ou tags se a consulta não for vazia.
        if (StringUtils.hasText(query)) {
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        // Retorna a lista de imagens que atendem às especificações.
        return findAll(spec);
    }
}
