package br.zup.wagner.casadocodigo.novaCategoria.model;

// entidade


import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Categoria {

    // atributos basicos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    // construtor default

    @Deprecated
    public Categoria() {
    }

    // construtor com atributos

    public Categoria(String nome) {
        this.nome = nome;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // equals & hashCode comparando pelo nome

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return nome.equals(categoria.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
