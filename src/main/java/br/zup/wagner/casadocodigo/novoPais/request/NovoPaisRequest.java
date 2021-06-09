package br.zup.wagner.casadocodigo.novoPais.request;

import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class NovoPaisRequest {

    // atributos basicos

    @NotBlank
    private String nome;

    // construtor com atributo

    @JsonCreator
    public NovoPaisRequest(String nome) {
        this.nome = nome;
    }

    // getters

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "NovoPaisRequest{" +
                "nome='" + nome + '\'' +
                '}';
    }

    // metodo para converter a requisição em entidade

    public Pais toModel() {

        return new Pais(nome);
    }
}
