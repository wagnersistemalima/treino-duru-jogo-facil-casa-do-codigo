package br.zup.wagner.casadocodigo.novoCliente.response;

import br.zup.wagner.casadocodigo.novoCliente.model.Cliente;
import com.fasterxml.jackson.annotation.JsonCreator;

public class NovoClienteResponse {

    // atributo basico

    private Long id;

    // construtor personalizado

    @JsonCreator
    public NovoClienteResponse(Cliente entity) {
        this.id = entity.getId();
    }

    // getters

    public Long getId() {
        return id;
    }
}
