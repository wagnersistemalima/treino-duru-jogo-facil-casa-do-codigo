package br.zup.wagner.casadocodigo.novaCategoria.request;

// objeto responsavel por trafegar dados da requisição

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class NovaCategoriaRequest {

    // atributos basicos

    @NotBlank
    private String nome;

    // construtor

    @JsonCreator
    public NovaCategoriaRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    // getters

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "NovaCategoriaRequest{" +
                "nome='" + nome + '\'' +
                '}';
    }

    // metodo para converter a requisição em entidade

    public Categoria toModel() {

        return  new Categoria(nome);
    }
}
