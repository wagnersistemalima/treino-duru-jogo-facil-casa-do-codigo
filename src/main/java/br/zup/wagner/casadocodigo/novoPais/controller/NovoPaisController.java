package br.zup.wagner.casadocodigo.novoPais.controller;

import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import br.zup.wagner.casadocodigo.novoPais.repository.PaisRepository;
import br.zup.wagner.casadocodigo.novoPais.request.NovoPaisRequest;
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

@Validated
@RestController
@RequestMapping("pais")
public class NovoPaisController {

    private Logger logger = LoggerFactory.getLogger(NovoPaisController.class);

    @Autowired
    private PaisRepository repository;

    // end point / cadastrar um novo pais

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid  @RequestBody NovoPaisRequest request) {
        logger.info("Iniciando cadastro de um Pais....");

        Pais possivelPais = repository.findByNome(request.getNome());

        // validação

        if(possivelPais != null) {
            throw new ExceptionGenericValidation("Pais já cadastrado");
        }

        Pais pais = request.toModel();
        repository.save(pais);

        return ResponseEntity.ok().build();
    }
}
