package br.zup.wagner.casadocodigo.novoLivro.controller;

import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.exceptions.ResourceNotFoundException;
import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import br.zup.wagner.casadocodigo.novoLivro.repository.LivroRepository;
import br.zup.wagner.casadocodigo.novoLivro.response.LivroDetalhesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class PaginaDetalheController {

    private Logger logger = LoggerFactory.getLogger(PaginaDetalheController.class);

    @Autowired
    private LivroRepository repository;

    // end point / pagina de detalhes

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        logger.info("iniciando a pagina de detalhes de um livro");

        // validação

        Optional <Livro> obj = repository.findById(id);

        Livro livro = obj.orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        return ResponseEntity.ok().body(new LivroDetalhesResponse(livro));

    }
}
