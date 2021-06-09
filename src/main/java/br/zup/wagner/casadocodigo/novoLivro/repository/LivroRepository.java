package br.zup.wagner.casadocodigo.novoLivro.repository;

import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByTitulo(String titulo);
}
