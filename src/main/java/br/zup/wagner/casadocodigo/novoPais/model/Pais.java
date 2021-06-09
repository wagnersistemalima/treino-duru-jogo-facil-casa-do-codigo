package br.zup.wagner.casadocodigo.novoPais.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributos basicos

    @Column(unique = true)
    private String nome;

    // construtor default

    @Deprecated
    public Pais() {
    }

    // construtor com atributos

    public Pais(String nome) {
        this.nome = nome;
    }

    // getters

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    // hashCode & Equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return Objects.equals(getNome(), pais.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }
}
