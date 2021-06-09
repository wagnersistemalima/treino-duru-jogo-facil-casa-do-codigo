package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novoAutor.model.Autor;
import br.zup.wagner.casadocodigo.novoAutor.repository.AutorRepository;
import br.zup.wagner.casadocodigo.novoAutor.request.NovoAutorRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class NovoAutorControllerTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // 1 cenario de testes

    @Test
    @DisplayName("deve cadastrar um novo autor")
    public void deveCadastrarUmNovoAutor() throws Exception {

        // cenario

        NovoAutorRequest request = new NovoAutorRequest("joao@gmail.com", "joao", "um autor legal");

        URI uri= new URI("/autores");

        // ação

        /// assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri).
                content(toJson(request)).
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));
    }

    // 2 cenario de testes

    @Test
    @DisplayName("Deve retornar uma exception quando email estiver em branco")
    public void deveRetornar400() throws  Exception {
        // cenario

        NovoAutorRequest request = new NovoAutorRequest("", "joao", "um autor legal");

        // ação

        // asssertivas

        URI uri=new URI("/autores");
        mockMvc.perform(MockMvcRequestBuilders.post(uri).
                content(toJson(request)).
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getHandler();


    }

    // 3 cenario de teste
    @Test
    @DisplayName("deve retornar exception 400 quando email nao for de formato valido")
    public void deveRetornar400EmailNaoValidado() throws Exception {

        // cenario

        NovoAutorRequest request = new NovoAutorRequest("joaogmail.com", "joao", "um autor legal");

        // ação

        // assertivas

        URI uri=new URI("/autores");
        mockMvc.perform(MockMvcRequestBuilders.post(uri).
                content(toJson(request)).
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getHandler();
    }

    // 4 cenario de teste
    @Test
    @DisplayName("deve retornar exception 400 quando descricao estiver em branco")
    public void deveRetornar400DescricaoEstiverEmBranco() throws Exception {

        // cenario

        NovoAutorRequest request = new NovoAutorRequest("joao@gmail.com", "joao", "");

        // ação

        // assertivas

        URI uri=new URI("/autores");
        mockMvc.perform(MockMvcRequestBuilders.post(uri).
                content(toJson(request)).
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getHandler();
    }

    // 5 cenario de testes

    @Test
    @DisplayName("deve subir exceção ao tentar cadastrar um autor existente")
    public void deveSubirExceptionAoTentarCadastrarUmAutorExistente() throws Exception {

        // cenario

        Autor autor = new Autor("joao@gmail.com", "pedro", "Um autor sabido");
        repository.save(autor);

        NovoAutorRequest request = new NovoAutorRequest("joao@gmail.com", "joao", "");

        // ação

        // assertivas

        URI uri=new URI("/autores");
        mockMvc.perform(MockMvcRequestBuilders.post(uri).
                content(toJson(request)).
                contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getHandler();
    }

    // metodo para desserializar objeto da requisição
    private String toJson(NovoAutorRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
}
