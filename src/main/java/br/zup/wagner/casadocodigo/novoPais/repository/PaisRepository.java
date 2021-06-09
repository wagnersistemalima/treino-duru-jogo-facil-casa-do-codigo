package br.zup.wagner.casadocodigo.novoPais.repository;

import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long> {

    Pais findByNome(String nome);
}
