package br.zup.wagner.casadocodigo.novoLivro.controller;

import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import br.zup.wagner.casadocodigo.novoLivro.repository.LivroRepository;
import br.zup.wagner.casadocodigo.novoLivro.response.LivroResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// carga = 4

@RestController
@RequestMapping("livros")
public class BuscarTodosLivrosController {

    private Logger logger = LoggerFactory.getLogger(BuscarTodosLivrosController.class); //1

    @Autowired
    private LivroRepository repository; //1

    // endPoint buscar todos os livros cadastrados

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<?> findAll() {

        logger.info("Iniciando a busca de todos os livros");

        List<Livro> lista = repository.findAll(); // 1

        return ResponseEntity.ok().body(lista.stream().map(livro -> new LivroResponse(livro)).collect(Collectors.toList())); //1

    }
}
