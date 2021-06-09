package br.zup.wagner.casadocodigo.novoAutor.controller;



import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoAutor.request.NovoAutorRequest;
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

// Classe responsavel por receber requisiçoes, processar e responder ao cliente. carga = 6

@Validated
@RestController
@RequestMapping("/autores")
public class NovoAutorController {

    private final Logger logger = LoggerFactory.getLogger(NovoAutorController.class); //1

    @Autowired
    private AutorRepository repository; //1


    // end point cadastrar um novo autor

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody  NovoAutorRequest request) { //1

        logger.info("Iniciando o cadastro de um novo autor....");

        Autor possivelAutor = repository.findByEmail(request.getEmail());

        // validação

        if(possivelAutor != null) { //1
            throw new ExceptionGenericValidation("Email já cadastrado"); //1
        }

        Autor autor = request.toModel();     //1
        repository.save(autor);

        logger.info("Autor criado com sucesso....");

        return ResponseEntity.ok().build();

    }

}
