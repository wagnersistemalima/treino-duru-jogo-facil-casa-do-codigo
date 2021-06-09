package br.zup.wagner.casadocodigo.novoCliente.request;

import br.zup.wagner.casadocodigo.exceptions.ResourceNotFoundException;
import br.zup.wagner.casadocodigo.novoCliente.model.Cliente;
import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoEstado.repository.EstadoRepository;
import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import br.zup.wagner.casadocodigo.novoPais.repository.PaisRepository;
import br.zup.wagner.casadocodigo.validation.CpfOuCnpj;
import br.zup.wagner.casadocodigo.validation.ExistsId;
import br.zup.wagner.casadocodigo.validation.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class NovoClienteRequest {

    // atributos basicos

    @Email
    @NotBlank
    @UniqueValue(domainClass = Cliente.class, fieldName = "email")
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobreNome;


    @NotBlank
    @UniqueValue(domainClass = Cliente.class, fieldName = "cpfOuCnpj")
    private String cpfOuCnpj;

    @NotBlank
    private String endereco;

    @NotBlank
    private String complemento;

    @NotBlank
    private String cidade;

    @NotNull
    private Long idPais;

    @NotNull
    private Long idEstado;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cep;

    // construtor com atributos

    @JsonCreator
    public NovoClienteRequest(String email, String nome, String sobreNome, String cpfOuCnpj, String endereco, String complemento, String cidade, Long idPais, Long idEstado, String telefone, String cep) {
        this.email = email;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpfOuCnpj = cpfOuCnpj;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.idPais = idPais;
        this.idEstado = idEstado;
        this.telefone = telefone;
        this.cep = cep;
    }


    // getters


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

    public Long getIdPais() {
        return idPais;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }


    public Cliente toModel(PaisRepository paisRepository, EstadoRepository estadoRepository) {

        Optional<Pais> objPais = paisRepository.findById(idPais);
        Pais pais = objPais.orElseThrow(() -> new ResourceNotFoundException("pais não encontrado"));

        Optional<Estado> objEstado = estadoRepository.findById(idEstado);
        Estado estado = objEstado.orElseThrow(() -> new ResourceNotFoundException("estado não encontrado"));

        return new Cliente(email, nome, sobreNome, cpfOuCnpj, endereco, complemento, cidade, pais, estado, telefone, cep);
    }
}
