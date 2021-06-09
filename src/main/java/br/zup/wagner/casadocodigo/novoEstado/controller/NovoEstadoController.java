package br.zup.wagner.casadocodigo.novoEstado.controller;

import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoEstado.repository.EstadoRepository;
import br.zup.wagner.casadocodigo.novoEstado.request.NovoEstadoRequest;
import br.zup.wagner.casadocodigo.novoPais.repository.PaisRepository;
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
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/estados")
public class NovoEstadoController {

    private Logger logger = LoggerFactory.getLogger(NovoEstadoController.class);

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PaisRepository paisRepository;


    //ende point / cadastrar estados

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid NovoEstadoRequest request) {
        logger.info("Iniciando cadastro de estados...");

        Estado possivelEstado = estadoRepository.findByNome(request.getNome());

        // validação

        if(estadoRepository.existsByNomeAndPais_Id(request.getNome(), request.getIdPais())) {
            logger.error("Caio dentro do if, estado existe neste pais");

            throw new ExceptionGenericValidation("Estado ja cadastrado");
        }

        Estado estado = request.toModel(paisRepository);

        estadoRepository.save(estado);

        logger.info("Estado cadastrado com sucesso...");

        return ResponseEntity.ok().build();
    }
}
