package br.zup.wagner.casadocodigo.novoCliente.model;

import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import br.zup.wagner.casadocodigo.validation.CpfOuCnpj;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributos basicos

    private String email;

    private String nome;

    private String sobreNome;

    @Column(unique = true)
    private String cpfOuCnpj;

    private String endereco;

    private String complemento;

    private String cidade;

    // associação / varios clientes podem pertence a um pais/ chave estrangeira na tabela cliente

    @ManyToOne
    private Pais pais;

    // associação / varios clientes podem pertencer a um estado/ chave estrangeira na tabela cliente

    @ManyToOne
    private Estado estado;

    private String telefone;

    private String cep;

    // construtor default

    @Deprecated
    public Cliente() {
    }

    // construtor com atributos


    public Cliente(String email, String nome, String sobreNome, String cpfOuCnpj, String endereco, String complemento, String cidade, Pais pais, Estado estado, String telefone, String cep) {
        this.email = email;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpfOuCnpj = cpfOuCnpj;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pais = pais;
        this.estado = estado;
        this.telefone = telefone;
        this.cep = cep;
    }

    // getters


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public Pais getPais() {
        return pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    // equals & hashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId()) && Objects.equals(getCpfOuCnpj(), cliente.getCpfOuCnpj());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpfOuCnpj());
    }

}
