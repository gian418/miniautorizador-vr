package br.com.albano.testevr.controllers;

import br.com.albano.testevr.config.AbstractWebConfigTest;
import br.com.albano.testevr.controllers.dtos.CartaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartaoControllerTest extends AbstractWebConfigTest {

    private static final String CARTOES_URI = "/cartoes";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Deve retorna status 201 e o CartaoDTO quando dados são válidos")
    public void shouldReturnStatus201andCartaoDTOWhenDataIsValid() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634588");
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(CARTOES_URI, cartaoJson);
        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(201, status);
        assertEquals(cartaoJson, content);
    }

    @Test
    @DisplayName("Deve retornar 422 e o CartaoDTO quando o cartão já existir")
    public void shouldReturnStatus422andCartaoDTOWhenCardAlreadyExists() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634599");
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result1 = performPost(CARTOES_URI, cartaoJson);
        var status201 = result1.getResponse().getStatus();
        var content201 = result1.getResponse().getContentAsString();

        MvcResult result2 = performPost(CARTOES_URI, cartaoJson);
        var status422 = result2.getResponse().getStatus();
        var content422 = result2.getResponse().getContentAsString();

        assertEquals(201, status201);
        assertEquals(422, status422);
        assertEquals(cartaoJson, content201);
        assertEquals(cartaoJson, content422);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o número de cartão ser menos quer 16 digitos")
    public void shouldReturn400WhenCardNumberIsLessThan16Digits() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873");
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(CARTOES_URI, cartaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o número de cartão não for informado")
    public void shouldReturn400WhenCardNumberIsNull() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(CARTOES_URI, cartaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o número de cartão possuir letras")
    public void shouldReturn400WhenCardNumberHasLetters() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634ABC");
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(CARTOES_URI, cartaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando a senha for menor que 4 digitos")
    public void shouldReturn400WhenPasswordIsLessThan4Digits() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634505");
        cartaoDTO.setSenha("123");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(CARTOES_URI, cartaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar status 400 quando a senha não for informada")
    public void shouldReturn400WhenPasswordIsNull() throws Exception {
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634505");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(CARTOES_URI, cartaoJson);
        var status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    @DisplayName("Deve retornar o saldo quando o cartão existir")
    public void shouldReturnBalanceWhenCardExists() throws Exception {
        String uri = CARTOES_URI+"/6549873025634505";
        MvcResult result = performGet(uri);

        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(200, status);
        assertEquals("500.00", content);
    }

    @Test
    @DisplayName("Deve retornar status 404 quando o cartão não existir")
    public void shouldReturn404WhenCardNotExists() throws Exception {
        String uri = CARTOES_URI+"/0009873025634505";
        MvcResult result = performGet(uri);

        var status = result.getResponse().getStatus();
        var content = result.getResponse().getContentAsString();

        assertEquals(404, status);
        assertTrue(content.isEmpty());
    }

    private MvcResult performPost(String uri, String cartaoJson) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cartaoJson)
        ).andReturn();
    }

    private MvcResult performGet(String uri) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
    }
}
