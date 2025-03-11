package io.github.dougllasfps.imageliteapi.infra.repository.specs;

// Importa a classe Specification do Spring Data JPA.
import org.springframework.data.jpa.domain.Specification;

// Define uma classe utilitária para especificações genéricas do JPA.
public class GenericSpecs {

    // Construtor privado para impedir a instanciação da classe.
    private GenericSpecs(){}

    // Método estático para retornar uma especificação de conjunção.
    public static <T> Specification<T> conjunction(){
        return (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}
