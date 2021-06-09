package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novoPais.repository.PaisRepository;
import br.zup.wagner.casadocodigo.novoPais.request.NovoPaisRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

import java.net.URI;

@SpringBootTest                             // carregar o contexto de integração / todas as classes
@AutoConfigureMockMvc
@AutoConfigureDataJpa                     // usar o banco de teste
@ActiveProfiles("test")
@Transactional
public class NovoPaisControllerTeste {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaisRepository paisRepository;

    // 1 cenario de teste

    @Test
    @DisplayName("deve cadastrar um pais retornando 200")
    public void deveCadastrarUmPais() throws Exception {

        // cenario

        NovoPaisRequest request = new NovoPaisRequest("Portugal");

        URI uri = new URI("/pais");

        Assertions.assertNull(paisRepository.findByNome("Portugal"));  // nao existe

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        // ação

        // assertivas

        Assertions.assertNotNull(paisRepository.findByNome("Portugal")); // quando existe

    }

    // 2 cenario de testes

    @Test
    @DisplayName("nao deve cadastrar, deve subir exception")
    public void deveSubirExceptionQuandoPaisVinherEmBranco() throws Exception {

        // cenario

        NovoPaisRequest request = new NovoPaisRequest("");

        URI uri = new URI("/pais");


        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

        // ação

        // assertivas


    }

    // 3 cenario de testes

    @Test
    @DisplayName("nao deve cadastrar, deve subir exception quando ja existe cadastrado pais")
    public void deveSubirExceptionAoTentarCadastrarPaisExistente() throws Exception {

        // cenario

        NovoPaisRequest request = new NovoPaisRequest("Brasil");

        paisRepository.save(request.toModel());

        URI uri = new URI("/pais");


        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

        // ação

        // assertivas

    }


    // metodo privado PARA TRANSFORMAR O OBJETO EM JSON
    private String toJson(NovoPaisRequest request) throws JsonProcessingException {
        return  objectMapper.writeValueAsString(request);
    }


}
