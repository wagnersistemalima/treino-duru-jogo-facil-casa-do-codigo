package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novaCategoria.model.Categoria;
import br.zup.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository;
import br.zup.wagner.casadocodigo.novaCategoria.request.NovaCategoriaRequest;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NovaCategoriaControllerTest {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // rodar depois de cada teste


    // 1 cenario de teste

    @Test
    @DisplayName("deve retornar 200 ok")
    public void deveRetornarStatusOk() throws Exception {

        // cenario

        NovaCategoriaRequest request = new NovaCategoriaRequest("Terror");

        URI uri = new URI("/categorias");

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve subir exception bad request ao tentar cadastrar uma categoria em branco")
    public void deveSubirExceptionAoTentarCadastrarCategoriaEmBranco() throws Exception {

        // cenario

        NovaCategoriaRequest request = new NovaCategoriaRequest("");

        URI uri = new URI("/categorias");

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    // 3 cenario de teste

    @Test
    @DisplayName("deve subir exception bad request ao tentar cadastrar uma categoria que já existe")
    public void deveSubirExceptionAoTentarCadastrarCategoriaExistente() throws Exception {

        // cenario

        Categoria categoria = new Categoria("Terror");
        repository.save(categoria);

        NovaCategoriaRequest request = new NovaCategoriaRequest("Terror");

        URI uri = new URI("/categorias");

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    // metodo privado para desserializar objeto request
    public String toJson (NovaCategoriaRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
}
