package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import br.zup.wagner.casadocodigo.novoLivro.repository.LivroRepository;
import br.zup.wagner.casadocodigo.novoLivro.response.LivroResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@Transactional
public class BuscarTodosLivrosControllerTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ObjectMapper objectMapper;

    private String titulo = "A rosa";
    private String resumo = "Era vermelha";
    private String sumario = "sumario";
    private BigDecimal preco = new BigDecimal("30.0");
    private Integer quantidadePagina = 100;
    private String isbn = "ytrfg";
    private LocalDate dataPublicacao = LocalDate.now().plusDays(1L);  // hoje mais um dia
    //-----------------------------------------------------------------------------------------

    private String titulo2 = "O cravo";


    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve retornar 200, com a lista de livros")
    public void deveRetornarListaDeLivros() throws  Exception {
        // cenario

        Autor autor = new Autor("joao@gmail.com", "joao", "um autor legal");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ação");
        categoriaRepository.save(categoria);

        Livro livro = new Livro(titulo, resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria, autor);
        livroRepository.save(livro);

        Livro livro2 = new Livro(titulo2, resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria, autor);
        livroRepository.save(livro2);


        LivroResponse response = new LivroResponse(livro);
        LivroResponse response2 = new LivroResponse(livro2);

        List<LivroResponse> lista = new ArrayList<>();
        lista.add(response);
        lista.add(response2);

        // ação

        URI uri = new URI("/livros");

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(toJson(lista)));

        // assertivas

        Assertions.assertEquals(2, lista.size());


    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar lista vazia, quando nao tiver livros cadastrados")
    public void deveRetornarListaVazia() throws  Exception {
        // cenario

        List<LivroResponse> lista = new ArrayList<>();

        // ação

        URI uri = new URI("/livros");

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(toJson(lista)));

        // assertivas

        Assertions.assertEquals(0, lista.size());


    }

    /// metodo para desserializar a resposta

    private String toJson(List<LivroResponse> response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }
}
