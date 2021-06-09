package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoLivro.model.Livro;
import br.zup.wagner.casadocodigo.novoLivro.repository.LivroRepository;
import br.zup.wagner.casadocodigo.novoLivro.request.NovoLivroRequest;
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

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
public class NovoLivroControllerTest {

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


    private String email = "pedro@gmail.com";
    private String titulo2 = "O cravo";
    private String titulo = "A rosa";
    private String resumo = "Era vermelha";
    private String sumario = "sumario";
    private BigDecimal preco = new BigDecimal("30.0");
    private BigDecimal precoInvalido = new BigDecimal("15.0");
    private Integer quantidadePagina = 100;
    private Integer getQuantidadePaginaInvalida = 99;
    private String isbn = "ytrfg";
    private LocalDate dataPublicacao = LocalDate.now().plusDays(1L);  // hoje mais um dia

    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("Deve cadastrar um livro, retornando status 200 ok")
    public void deveRetornar200() throws Exception {

        // cenario

        Autor autor = new Autor("joao@gmail.com", "joao", "um autor legal");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ação");
        categoriaRepository.save(categoria);

        NovoLivroRequest request = new NovoLivroRequest(
                titulo,resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria.getId(), autor.getId());
        // ação

        URI uri = new URI("/livros");

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    // 2 cenario de testes

    @Test
    @DisplayName("Deve subir Exception ao tentar receber uma requisição com um titulo de livro existente")
    public void deveSubirExceptionAoTentarCadastrarUmLivroComTituloJacadastrado() throws Exception {

        // cenario

        Autor autor2 = new Autor("maria@gmail.com", "maria", "uma autora linda");
        autorRepository.save(autor2);

        Categoria categoria2 = new Categoria("Romance");
        categoriaRepository.save(categoria2);

        Livro livro = new Livro(titulo2, resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria2, autor2);
        livroRepository.save(livro);

        NovoLivroRequest request = new NovoLivroRequest(
                titulo2,resumo, sumario, preco, quantidadePagina, isbn, dataPublicacao, categoria2.getId(), autor2.getId());

        // ação

        URI uri = new URI("/livros");

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    // 3 cenario de teste

    @Test
    @DisplayName("Deve subir Exception ao tentar enviar requisição com dados  invalidos")
    public void deveSubirExceptionQuandoDadosInvalidos() throws Exception {

        // cenario

        Autor autor3 = new Autor("ana@gmail.com", "ana", "uma autora linda");
        autorRepository.save(autor3);

        Categoria categoria3 = new Categoria("Terror");
        categoriaRepository.save(categoria3);

        NovoLivroRequest request = new NovoLivroRequest(
                "","", "", precoInvalido, getQuantidadePaginaInvalida, "", dataPublicacao, categoria3.getId(), autor3.getId());
        // ação

        URI uri = new URI("/livros");

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    // metodo privado para converter objeto request em json
    private String toJson(NovoLivroRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

}
