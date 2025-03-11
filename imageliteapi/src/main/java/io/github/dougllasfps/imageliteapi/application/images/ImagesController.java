package io.github.dougllasfps.imageliteapi.application.images;

// Importa as classes necessárias.
import io.github.dougllasfps.imageliteapi.domain.entity.Image;
import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import io.github.dougllasfps.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

// Anota a classe como um controlador REST e usa o Lombok para gerar o construtor com todas as dependências obrigatórias.
@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    // Declaração das dependências que serão injetadas.
    private final ImageService service;
    private final ImageMapper mapper;

    // Método para salvar uma imagem recebida via multipart/form-data.
    @PostMapping
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file, // Arquivo de imagem.
            @RequestParam("name") String name, // Nome da imagem.
            @RequestParam("tags") List<String> tags // Tags da imagem.
    ) throws IOException {

        log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());

        // Mapeia o arquivo de imagem para a entidade Image.
        Image image = mapper.mapToImage(file, name, tags);
        // Salva a imagem no repositório.
        Image savedImage = service.save(image);
        // Constrói a URL da imagem salva.
        URI imageUri = buildImageURL(savedImage);

        // Retorna uma resposta HTTP 201 Created com a URL da imagem.
        return ResponseEntity.created(imageUri).build();
    }

    // Método para obter uma imagem pelo ID.
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){
        var possibleImage = service.getById(id);
        if(possibleImage.isEmpty()){
            return ResponseEntity.notFound().build(); // Retorna 404 se a imagem não for encontrada.
        }

        var image = possibleImage.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType()); // Define o tipo de conteúdo.
        headers.setContentLength(image.getSize()); // Define o tamanho do conteúdo.
        // inline; filename="image.PNG"
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() +  "\"", image.getFileName());

        // Retorna a imagem com os cabeçalhos apropriados.
        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    // Método para buscar imagens por extensão e consulta.
    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
            @RequestParam(value = "query",required = false) String query) throws InterruptedException {

        Thread.sleep(3000L); // Simula uma espera.

        var result = service.search(ImageExtension.ofName(extension), query);

        var images = result.stream().map(image -> {
            var url = buildImageURL(image);
            return mapper.imageToDTO(image, url.toString());
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images); // Retorna a lista de imagens encontradas.
    }

    // Método privado para construir a URL da imagem.
    private URI buildImageURL(Image image){
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(imagePath)
                .build().toUri();
    }
}
