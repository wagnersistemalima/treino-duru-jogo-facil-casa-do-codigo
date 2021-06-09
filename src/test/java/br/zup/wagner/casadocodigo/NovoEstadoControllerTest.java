package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoEstado.repository.EstadoRepository;
import br.zup.wagner.casadocodigo.novoEstado.request.NovoEstadoRequest;
import br.zup.wagner.casadocodigo.novoPais.model.Pais;
import br.zup.wagner.casadocodigo.novoPais.repository.PaisRepository;
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

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@Transactional
public class NovoEstadoControllerTest {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    // 1 cenario de testes / caminho feliz

    @Test
    @DisplayName("deve retornar 200 ok")
    public void deveRetornar200() throws Exception{

        // cenario

        Pais pais = new Pais("Portugal");
        paisRepository.save(pais);

        NovoEstadoRequest request = new NovoEstadoRequest("Paraiba", pais.getId());

        URI uri = new URI("/estados");

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(200));



    }

    // 2º cenario de testes

    @Test
    @DisplayName("deve subir exception quando id pais não existir")
    public void deveSubirExceptionQuandoIdPaisNaoExistir() throws Exception{

        // cenario

        Pais pais = new Pais("Brasil");
        paisRepository.save(pais);

        NovoEstadoRequest request = new NovoEstadoRequest("Pernabuco", 5L);

        URI uri = new URI("/estados");

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    // 3ª cenario de testes

    @Test
    @DisplayName("deve subir exception quando estado ja existir cadastrado para o pais informado")
    public void deveSubirExceptionQuandoEstadoJaEstiverCadastrado() throws Exception{

        // cenario

        Pais pais = new Pais("China");
        paisRepository.save(pais);

        Estado estado = new Estado("Bhaia",pais);
        estadoRepository.save(estado);

        NovoEstadoRequest request = new NovoEstadoRequest("Bhaia", pais.getId());

        URI uri = new URI("/estados");

        // ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    // metodo privado para desserializar objeto request
    public String toJson (NovoEstadoRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }
}
