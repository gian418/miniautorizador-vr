package br.com.albano.testevr.controllers;

import br.com.albano.testevr.config.AbstractWebConfigTest;
import br.com.albano.testevr.controllers.dtos.TransacaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransacaoControllerTest extends AbstractWebConfigTest {

    private static final String TRANSACOES_URI = "/transacoes";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Deve retorna status 201 e OK na resposta quando os dados forem válidos")
    public void shouldReturnStatus201WhenDataIsValid() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6540000025634505");
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(201, status);
        assertEquals("OK", content);
    }

    @Test
    @DisplayName("Deve retorna status 422 quando o saldo for insuficiente")
    public void shouldReturnStatus422WhenInsufficientBalance() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6540000025634505");
        transacaoDTO.setValor(new BigDecimal("1000"));
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(422, status);
        assertEquals("SALDO_INSUFICIENTE", content);
    }

    @Test
    @DisplayName("Deve retorna status 422 quando a senha for inválida")
    public void shouldReturnStatus422WhenPasswordIsInvalid() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6540000025634505");
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("1235");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(422, status);
        assertEquals("SENHA_INVALIDA", content);
    }

    @Test
    @DisplayName("Deve retorna status 422 quando a o número de cartão não existir")
    public void shouldReturnStatus422WhenCardNumberNotExists() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("0549873025634505");
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(422, status);
        assertEquals("CARTAO_INEXISTENTE", content);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o número de cartão for menor que 16 digitos")
    public void shouldReturn400WhenCardNumberIsLessThan16Digits() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("054987302");
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o número de cartão não for informado")
    public void shouldReturn400WhenCardNumberIsNull() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o número de cartão possuir letras")
    public void shouldReturn400WhenCardNumberHasLetters() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6549873025634ABC");
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando a senha for menor que 4 digitos")
    public void shouldReturn400WhenPasswordIsLessThan4Digits() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6540000025634505");
        transacaoDTO.setValor(BigDecimal.TEN);
        transacaoDTO.setSenhaCartao("123");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando a senha não for informada")
    public void shouldReturn400WhenPasswordIsNull() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6540000025634505");
        transacaoDTO.setValor(BigDecimal.TEN);
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o valor não for informado")
    public void shouldReturn400WhenValueIsNull() throws Exception {
        var transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("6540000025634505");
        transacaoDTO.setSenhaCartao("1234");
        var transacaoJson = super.mapToJson(transacaoDTO);

        MvcResult result = performPost(TRANSACOES_URI, transacaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    private MvcResult performPost(String uri, String cartaoJson) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cartaoJson)
        ).andReturn();
    }
}
