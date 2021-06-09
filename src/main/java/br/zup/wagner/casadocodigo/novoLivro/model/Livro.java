package br.zup.wagner.casadocodigo.novoLivro.model;

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novoAutor.model.Autor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributos basicos

    @Column(unique = true)
    private String titulo;

    private String resumo;

    private String sumario;

    private BigDecimal preco;

    private Integer quantidadePaginas;

    private String isbn;

    private LocalDate dataPublicacao;

    // associação chave estrangeira na tabela livro

    @OneToOne
    private Categoria categoria;

    // associação chave estrangeira na tabela autor

    @OneToOne
    private Autor autor;

    // construtor default

    @Deprecated
    public Livro() {
    }

    // construtor com atributos


    public Livro(String titulo, String resumo, String sumario, BigDecimal preco, Integer quantidadePaginas, String isbn, LocalDate dataPublicacao, Categoria categoria, Autor autor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.quantidadePaginas = quantidadePaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
        this.autor = autor;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public Autor getAutor() {
        return autor;
    }

    // HashCode & equals comparando pelo id e titulo


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(getId(), livro.getId()) && Objects.equals(getTitulo(), livro.getTitulo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitulo());
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", quantidadePaginas=" + quantidadePaginas +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", categoria=" + categoria +
                ", autor=" + autor +
                '}';
    }
}
