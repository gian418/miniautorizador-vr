package br.com.albano.testevr.services;

import br.com.albano.testevr.config.AbstractWebConfigTest;
import br.com.albano.testevr.exceptions.CartaoNaoEncontradoException;
import br.com.albano.testevr.repositories.CartaoRepository;
import br.com.albano.testevr.services.domains.Cartao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartaoSaldoServiceTest extends AbstractWebConfigTest {

    @Autowired
    CartaoSaldoService cartaoSaldoService;

    @Autowired
    CartaoRepository cartaoRepository;

    @Test
    @Transactional
    @DisplayName("Deve ser possível obter o saldo de um cartão")
    public void shouldBePossibleToObtainCardBalance() {
        var cartao = new Cartao();
        cartao.setSenha("1234");
        cartao.setNumero("6549873025634503");
        cartaoRepository.save(cartao.toEntity());

        var saldo = cartaoSaldoService.getSaldo("6549873025634503");
        var saldoExpected = BigDecimal.valueOf(500).setScale(2, RoundingMode.UNNECESSARY);
        Assertions.assertEquals(saldoExpected, saldo.setScale(2, RoundingMode.UNNECESSARY));
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar o erro CartaoNaoEncontradoException caso o cartão não exista")
    public void shouldExceptionIfCardDoesNotExist() {
        Assertions.assertThrows(
                CartaoNaoEncontradoException.class,
                () -> cartaoSaldoService.getSaldo("6549873025634508")
        );

    }
}
