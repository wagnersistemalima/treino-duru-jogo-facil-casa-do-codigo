package br.zup.wagner.casadocodigo.novoEstado.model;

import br.zup.wagner.casadocodigo.novoPais.model.Pais;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributos basicos

    private String nome;

    // associação

    @ManyToOne
    private Pais pais;

    // construtor default

    @Deprecated
    public Estado() {
    }

    // construtor com argumentos


    public Estado(String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    // getters


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Pais getPais() {
        return pais;
    }

    // hashCode & equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estado estado = (Estado) o;
        return Objects.equals(getNome(), estado.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }
}
