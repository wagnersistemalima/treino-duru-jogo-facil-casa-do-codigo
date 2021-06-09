package br.zup.wagner.casadocodigo.novoCliente.controller;

import br.zup.wagner.casadocodigo.novoCliente.repository.ClienteRepository;
import br.zup.wagner.casadocodigo.novoCliente.model.Cliente;
import br.zup.wagner.casadocodigo.novoCliente.request.NovoClienteRequest;
import br.zup.wagner.casadocodigo.novoCliente.response.NovoClienteResponse;
import br.zup.wagner.casadocodigo.novoEstado.repository.EstadoRepository;
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

// carga = 6

@Validated
@RestController
@RequestMapping("clientes")
public class NovoClienteController {

    private Logger logger = LoggerFactory.getLogger(NovoClienteController.class); //1

    @Autowired
    private ClienteRepository clienteRepository; //1

    @Autowired
    private PaisRepository paisRepository; //1

    @Autowired
    private EstadoRepository estadoRepository; //1

    // end point cadastrar um cliente

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovoClienteRequest request) { //1

        logger.info("....Iniciando cadastro de um cliente....");

        Cliente cliente = request.toModel(paisRepository, estadoRepository); //1

        clienteRepository.save(cliente);

        logger.info("...Cliente cadastrado com sucesso...");
        return ResponseEntity.ok().body(new NovoClienteResponse(cliente));
    }
}
