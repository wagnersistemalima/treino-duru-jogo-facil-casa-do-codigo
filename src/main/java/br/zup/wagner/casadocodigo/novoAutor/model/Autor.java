package br.zup.wagner.casadocodigo.novoAutor.model;

// entidade


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String nome;

    private String descricao;

    private LocalDateTime dataRegistro = LocalDateTime.now();


    // construtor default para o hibernet

    @Deprecated
    public Autor() {
    }

    // construtor com atributos

    public Autor(String email, String nome, String descricao) {
        this.email = email;
        this.nome = nome;
        this.descricao = descricao;
    }

    // getters

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getId() {
        return id;
    }

    // equals & hashCode comparando por id e email


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(getId(), autor.getId()) && Objects.equals(getEmail(), autor.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}
