package br.zup.wagner.casadocodigo.novoLivro.response;

import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import com.fasterxml.jackson.annotation.JsonCreator;

public class LivroResponse {

    // atributos basicos

    private Long id;
    private String titulo;

    // construtor personalizado recebendo uma entidade

    @JsonCreator
    public LivroResponse(Livro entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();

    }

    // getters


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
}
