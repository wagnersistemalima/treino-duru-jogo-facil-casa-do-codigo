package br.zup.wagner.casadocodigo.novoEstado.repository;

import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Estado findByNome(String nome);

    Boolean existsByNomeAndPais_Id(String nome, Long id); // procurar pelo nome do estado e pelo id pais
}
