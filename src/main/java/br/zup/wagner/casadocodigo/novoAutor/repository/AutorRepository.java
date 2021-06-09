package br.zup.wagner.casadocodigo.novoAutor.repository;

import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {


    Autor findByEmail(String email);
}
