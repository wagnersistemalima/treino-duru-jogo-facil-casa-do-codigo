package br.zup.wagner.casadocodigo.novoCliente.repository;

import br.zup.wagner.casadocodigo.novoCliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfOuCnpj(String cpfOuCnpj);

    boolean existsByEmail(String email);
}
