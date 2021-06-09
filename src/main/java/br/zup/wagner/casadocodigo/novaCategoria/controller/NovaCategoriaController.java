package br.zup.wagner.casadocodigo.novaCategoria.controller;

import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novaCategoria.request.NovaCategoriaRequest;
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

// classe responsavel por receber requisiçoes, processar e responder ao cliente / carga = 6

@Validated
@RestController
@RequestMapping("/categorias")
public class NovaCategoriaController {

    private final Logger logger = LoggerFactory.getLogger(NovaCategoriaController.class); //1

    @Autowired
    private CategoriaRepository repository; //1

    // end point cadastrar uma nova categoria

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovaCategoriaRequest request) { //1
        logger.info("Iniciando cadastro de uma categoria....");

        Categoria possivelCategoria = repository.findByNome(request.getNome()); //1

        // validação

        if(possivelCategoria != null) { //1
            throw new ExceptionGenericValidation("nome não pode ser duplicado");
        }

        Categoria categoria = request.toModel();
        repository.save(categoria);

        logger.info("categoria cadastrada com sucesso....");

        return ResponseEntity.ok().build();                    //1

    }
}
