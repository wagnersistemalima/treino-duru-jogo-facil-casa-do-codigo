package br.zup.wagner.casadocodigo.novoAutor.request;

// objeto responsavel por trafegar dados da requisição para a api

import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class NovoAutorRequest {

    // atributos basicos

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max = 400)
    private String descricao;

    // construtor com atributos

    @JsonCreator
    public NovoAutorRequest(@Email @NotBlank String email,@NotBlank String nome,@NotBlank @Size(max = 400) String descricao) {
        this.email = email;
        this.nome = nome;
        this.descricao = descricao;
    }


    // getter

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "NovoAutorRequest{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    // metodo para converter o objeto em entidade

    public Autor toModel() {

        return new Autor(email, nome , descricao);
    }
}
