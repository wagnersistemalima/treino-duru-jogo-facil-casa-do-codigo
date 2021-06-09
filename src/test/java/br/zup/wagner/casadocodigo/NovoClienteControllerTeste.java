package br.zup.wagner.casadocodigo;

import br.zup.wagner.casadocodigo.novoCliente.model.Cliente;
import br.zup.wagner.casadocodigo.novoCliente.repository.ClienteRepository;
import br.zup.wagner.casadocodigo.novoCliente.request.NovoClienteRequest;
import br.zup.wagner.casadocodigo.novoEstado.model.Estado;
import br.zup.wagner.casadocodigo.novoEstado.repository.EstadoRepository;
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
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
public class NovoClienteControllerTeste {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //-------------------------------------------

    private String email = "joao@gmail.com";
    private String nome = "joao";
    private String sobreNome = "silva";
    private String cpfOuCnpj = "04394450438";
    private String endereco = "rua das flores";
    private String complemento = "perto da padaria";
    private String cidade = "Campina Grande";
    private String telefone = "83993809934";
    private String cep =  "58410505";

    private String emailRepetido = "joao@gmail.com";

    //--------------------------------------------------------

    // 1 cenario de teste/ caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar um cliente")
    public void deveRetornar200() throws  Exception{
        // cenario

        Pais pais = new Pais("Portugal");
        paisRepository.save(pais);

        Estado estado = new Estado("Paraiba", pais);
        estadoRepository.save(estado);

        NovoClienteRequest request = new NovoClienteRequest(
                email, nome, sobreNome, cpfOuCnpj, endereco, complemento,cidade,
                pais.getId(), estado.getId(), telefone, cep);

        URI uri = new URI("/clientes");

        /// ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJsonRequest(request)))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    // 2 cenario de teste/

    @Test
    @DisplayName("deve retornar 404, quando tentar cadastrar cliente com pais inexistente")
    public void deveRetornar404AoTentarCadastrarUmClienteComPaisInexistente() throws  Exception{
        // cenario

        Pais pais = new Pais("Portugal");
        paisRepository.save(pais);

        Estado estado = new Estado("Paraiba", pais);
        estadoRepository.save(estado);

        NovoClienteRequest request = new NovoClienteRequest(
                email, nome, sobreNome, cpfOuCnpj, endereco, complemento,cidade,
                5000L, estado.getId(), telefone, cep);

        URI uri = new URI("/clientes");

        /// ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJsonRequest(request)))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    // 4 cenario de teste/

    @Test
    @DisplayName("deve retornar 404, quando tentar cadastrar cliente com estado inexistente")
    public void deveRetornar404AoTentarCadastrarUmClienteComEstadoInexistente() throws  Exception{
        // cenario

        Pais pais = new Pais("Portugal");
        paisRepository.save(pais);

        Estado estado = new Estado("Paraiba", pais);
        estadoRepository.save(estado);

        NovoClienteRequest request = new NovoClienteRequest(
                email, nome, sobreNome, cpfOuCnpj, endereco, complemento,cidade,
                pais.getId(), 5000L, telefone, cep);

        URI uri = new URI("/clientes");

        /// ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJsonRequest(request)))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    // 5 cenario de teste/

    @Test
    @DisplayName("deve retornar 400, quando tentar cadastrar cliente com email ja cadastrado")
    public void deveRetornar400AoTentarCadastrarUmClienteComEmailJacadastrado() throws  Exception{
        // cenario

        Pais pais = new Pais("Portugal");
        paisRepository.save(pais);

        Estado estado = new Estado("Paraiba", pais);
        estadoRepository.save(estado);

        Cliente cliente = new Cliente(email, nome, sobreNome, cpfOuCnpj, endereco, complemento, cidade, pais, estado, telefone, cep);
        clienteRepository.save(cliente);

        NovoClienteRequest request = new NovoClienteRequest(
                emailRepetido, nome, sobreNome, "94404967039", endereco, complemento,cidade,
                pais.getId(), estado.getId(), telefone, cep);

        URI uri = new URI("/clientes");

        /// ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJsonRequest(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    // 6 cenario de teste/

    @Test
    @DisplayName("deve retornar 400, quando tentar cadastrar cliente com cpf ja cadastrado")
    public void deveRetornar400AoTentarCadastrarUmClienteComCpfJaCadastrado() throws  Exception{
        // cenario

        Pais pais = new Pais("Portugal");
        paisRepository.save(pais);

        Estado estado = new Estado("Paraiba", pais);
        estadoRepository.save(estado);

        Cliente cliente = new Cliente(email, nome, sobreNome, cpfOuCnpj, endereco, complemento, cidade, pais, estado, telefone, cep);
        clienteRepository.save(cliente);

        NovoClienteRequest request = new NovoClienteRequest(
                "maria@gmail.com", nome, sobreNome, cpfOuCnpj, endereco, complemento,cidade,
                pais.getId(), estado.getId(), telefone, cep);

        URI uri = new URI("/clientes");

        /// ação

        // assertivas

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(toJsonRequest(request)))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }



    // metodo para desserializar objeto request

    private String toJsonRequest(NovoClienteRequest request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }


}
