package br.zup.wagner.casadocodigo.novoLivro.controller;

import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import br.zup.wagner.casadocodigo.novoLivro.repository.LivroRepository;
import br.zup.wagner.casadocodigo.novoLivro.request.NovoLivroRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

// carga = 7


@Validated
@RestController
@RequestMapping("livros")
public class NovoLivroController {

    private Logger logger = LoggerFactory.getLogger(NovoLivroController.class); //1

    @Autowired
    private LivroRepository livroRepository; //1

    @Autowired
    private AutorRepository autorRepository; //1

    @Autowired
    CategoriaRepository categoriaRepository; //1

    // end point/ cadastrar um novo livro

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovoLivroRequest request) { //1
        logger.info("Iniciando cadastro de um novo livro......");


        Livro possivelLivro = livroRepository.findByTitulo(request.getTitulo()); //1

        // validação

        if (possivelLivro != null) {  //1
            logger.error("Titulo ja cadastrado para este livro");
            throw new ExceptionGenericValidation("Titulo ja cadastrado para este livro");
        }

        Livro livro = request.toModel(autorRepository, categoriaRepository);



        livroRepository.save(livro);

        logger.info("Livro cadastrado com sucesso");

        return ResponseEntity.ok().build();

    }
}
