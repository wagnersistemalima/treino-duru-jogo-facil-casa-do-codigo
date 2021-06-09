package br.zup.wagner.casadocodigo.novoLivro.response;

import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LivroDetalhesResponse {

    // atributos basicos

    private Long id;
    private String titulo;
    private String resumo;

    private String sumario;

    private BigDecimal preco;

    private Integer quantidadePaginas;

    private String isbn;

    private LocalDate dataPublicacao;

    private String nomeCategoria;

    private String nomeAutor;

    private String descricaoAutor;

    // construtor personalizado recebendo uma entidade

    @JsonCreator
    public LivroDetalhesResponse(Livro entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.resumo = entity.getResumo();
        this.sumario = entity.getSumario();
        this.preco = entity.getPreco();
        this.quantidadePaginas = entity.getQuantidadePaginas();
        this.isbn = entity.getIsbn();
        this.dataPublicacao = entity.getDataPublicacao();
        this.nomeCategoria = entity.getCategoria().getNome();
        this.nomeAutor = entity.getAutor().getNome();
        this.descricaoAutor = entity.getAutor().getDescricao();
    }

    // getters


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public String getDescricaoAutor() {
        return descricaoAutor;
    }
}
