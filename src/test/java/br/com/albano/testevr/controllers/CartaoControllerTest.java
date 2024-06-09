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

public class CartaoControllerTest extends AbstractWebConfigTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Deve retorna status 201 e o CartaoDTO quando dados são válidos")
    public void test1() throws Exception {
        String uri = "/cartoes";
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634588");
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result = performPost(uri, cartaoJson);
        var status201 = result.getResponse().getStatus();
        var content201 = result.getResponse().getContentAsString();

        assertEquals(201, status201);
        assertEquals(content201, cartaoJson);
    }

    @Test
    @DisplayName("Deve retornar 422 e o CartaoDTO quando o cartão já existir")
    public void test() throws Exception {
        String uri = "/cartoes";
        var cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634599");
        cartaoDTO.setSenha("1234");
        var cartaoJson = super.mapToJson(cartaoDTO);

        MvcResult result1 = performPost(uri, cartaoJson);
        var status201 = result1.getResponse().getStatus();
        var content201 = result1.getResponse().getContentAsString();

        MvcResult result2 = performPost(uri, cartaoJson);
        var status422 = result2.getResponse().getStatus();
        var content422 = result2.getResponse().getContentAsString();

        assertEquals(201, status201);
        assertEquals(422, status422);
        assertEquals(content201, cartaoJson);
        assertEquals(content422, cartaoJson);
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
