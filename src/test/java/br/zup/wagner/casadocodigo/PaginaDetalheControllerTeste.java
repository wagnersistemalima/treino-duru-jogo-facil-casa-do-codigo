package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import br.zup.wagner.casadocodigo.novoLivro.repository.LivroRepository;
import br.zup.wagner.casadocodigo.novoLivro.response.LivroDetalhesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.servlet.mvc.method.annotation.UriComponentsBuilderMethodArgumentResolver;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
public class PaginaDetalheControllerTeste {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String titulo = "A rosa";
    private String resumo = "Era vermelha";
    private String sumario = "sumario";
    private BigDecimal preco = new BigDecimal("30.0");
    private Integer quantidadePagina = 100;
    private String isbn = "ytrfg";
    private LocalDate dataPublicacao = LocalDate.now().plusDays(1L);  // hoje mais um dia
    //-----------------------------------------------------------------------------------------

    // 1 cenario de testes / caminho feliz

    @Test
    @DisplayName("deve retornar os detalhes de um livro")
    public void deveRetornar200DetalhesLivro() throws  Exception {

        // cenario
        Autor autor = new Autor("joao@gmail.com", "joao", "um autor legal");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ação");
        categoriaRepository.save(categoria);

        Livro livro = new Livro(titulo, resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria, autor);
        livroRepository.save(livro);

        LivroDetalhesResponse response = new LivroDetalhesResponse(livro);

        URI uri = UriComponentsBuilder.fromUriString("/livros/{id}").buildAndExpand(livro.getId()).toUri();

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(toJson(response)));

    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve subir 404 exception quando nao encontrar o livro")
    public void deveRetornar404QuandoNaoEncontrarLivro() throws  Exception {

        // cenario
        Autor autor = new Autor("joao@gmail.com", "joao", "um autor legal");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ação");
        categoriaRepository.save(categoria);

        Livro livro = new Livro(titulo, resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria, autor);
        livroRepository.save(livro);


        URI uri = UriComponentsBuilder.fromUriString("/livros/{id}").buildAndExpand(2L).toUri();

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }



    // metodo para desserializar objetos
    private String toJson(LivroDetalhesResponse response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }


}
