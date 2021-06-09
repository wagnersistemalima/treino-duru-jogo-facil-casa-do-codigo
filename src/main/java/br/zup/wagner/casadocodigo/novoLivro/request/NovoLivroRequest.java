package br.zup.wagner.casadocodigo.novoLivro.request;


import br.zup.wagner.casadocodigo.exceptions.ExceptionGenericValidation;
import br.zup.wagner.casadocodigo.exceptions.ResourceNotFoundException;
import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class NovoLivroRequest {

    // atributos basicos

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String resumo;

    private String sumario;

    @NotNull
    @Min(20)
    private BigDecimal preco;

    @NotNull
    @Min(100)
    private Integer quantidadePaginas;

    @NotBlank
    private String isbn;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataPublicacao;

    // Associação

    @NotNull
    private Long idCategoria;

    @NotNull
    private Long idAutor;

    // construtor com atributos

    @JsonCreator
    public NovoLivroRequest(String titulo, String resumo, String sumario, BigDecimal preco, Integer quantidadePaginas, String isbn, LocalDate dataPublicacao, Long idCategoria, Long idAutor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.quantidadePaginas = quantidadePaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.idCategoria = idCategoria;
        this.idAutor = idAutor;
    }

    // gatters


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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    @Override
    public String toString() {
        return "NovoLivroRequest{" +
                "titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", quantidadePaginas=" + quantidadePaginas +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", idCategoria=" + idCategoria +
                ", idAutor=" + idAutor +
                '}';
    }

    // metodo para converter a requisição em entidade fazendo a validação do id autor e id categoria

    public Livro toModel(AutorRepository autorRepository, CategoriaRepository categoriaRepository) {

        Optional <Autor> objAutor = autorRepository.findById(idAutor);
        Optional <Categoria> objCategoria = categoriaRepository.findById(idCategoria);

        Autor autor = objAutor.orElseThrow(() -> new ResourceNotFoundException("id autor não encontrado"));
        Categoria categoria = objCategoria.orElseThrow(() -> new ResourceNotFoundException("id categoria não encontrado"));

        return new Livro(titulo, resumo, sumario, preco, quantidadePaginas, isbn, dataPublicacao, categoria, autor);
    }




}
