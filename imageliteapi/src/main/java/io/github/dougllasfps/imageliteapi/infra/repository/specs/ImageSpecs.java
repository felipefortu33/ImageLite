package io.github.dougllasfps.imageliteapi.infra.repository.specs;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;

// Define uma classe utilitária para especificações de consultas relacionadas à entidade Image.
public class ImageSpecs {

    // Construtor privado para impedir a instanciação da classe.
    private ImageSpecs() {}

    // Método estático para retornar uma especificação que verifica se a extensão da imagem é igual à fornecida.
    public static Specification<Image> extensionEqual(ImageExtension extension) {
        return (root, q, cb) -> cb.equal(root.get("extension"), extension);
    }

    // Método estático para retornar uma especificação que verifica se o nome da imagem contém o texto fornecido (ignorando maiúsculas e minúsculas).
    public static Specification<Image> nameLike(String name) {
        return (root, q, cb) -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

    // Método estático para retornar uma especificação que verifica se as tags da imagem contêm o texto fornecido (ignorando maiúsculas e minúsculas).
    public static Specification<Image> tagsLike(String tags) {
        return (root, q, cb) -> cb.like(cb.upper(root.get("tags")), "%" + tags.toUpperCase() + "%");
    }
}
