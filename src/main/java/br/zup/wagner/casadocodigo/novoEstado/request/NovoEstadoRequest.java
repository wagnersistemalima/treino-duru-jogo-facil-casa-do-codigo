package br.zup.wagner.casadocodigo.novoEstado.request;

import br.zup.wagner.casadocodigo.exceptions.ResourceNotFoundException;
import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import br.zup.wagner.casadocodigo.novoPais.repository.PaisRepository;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class NovoEstadoRequest {

    // atributos basicos

    @NotBlank
    private String nome;

    @NotNull
    private Long idPais;

    // construtor com argumentos

    @JsonCreator
    public NovoEstadoRequest(String nome, Long idPais) {
        this.nome = nome;
        this.idPais = idPais;
    }

    // getters


    public String getNome() {
        return nome;
    }

    public Long getIdPais() {
        return idPais;
    }

    // metodo para converter a requisição em uma entidade

    public Estado toModel(PaisRepository paisRepository) {

        Optional<Pais> obj = paisRepository.findById(idPais);

        Pais pais = obj.orElseThrow(() -> new ResourceNotFoundException("pais não encontrado"));

        return new Estado(nome, pais);
    }
}
