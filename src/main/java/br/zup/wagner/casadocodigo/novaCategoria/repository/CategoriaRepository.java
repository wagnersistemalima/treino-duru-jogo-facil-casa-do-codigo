package br.zup.wagner.casadocodigo.novaCategoria.repository;

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNome(String nome);
}
