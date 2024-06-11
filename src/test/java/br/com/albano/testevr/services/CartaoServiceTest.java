package br.com.albano.testevr.services;

import br.com.albano.testevr.config.AbstractWebConfigTest;
import br.com.albano.testevr.exceptions.SalvarCartaoException;
import br.com.albano.testevr.repositories.CartaoRepository;
import br.com.albano.testevr.repositories.CartaoSaldoRepository;
import br.com.albano.testevr.repositories.CartaoTransacaoRepository;
import br.com.albano.testevr.repositories.enums.TipoTransacao;
import br.com.albano.testevr.services.domains.Cartao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CartaoServiceTest extends AbstractWebConfigTest {

    @Autowired
    CartaoService cartaoService;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    private CartaoSaldoRepository cartaoSaldoRepository;

    @Autowired
    private CartaoTransacaoRepository cartaoTransacaoRepository;

    @Test
    @Transactional
    @DisplayName("Deve salvar quando senha e número do cartão são válidos")
    public void shouldSaveWhenCardIsValid() {
        var cartao = new Cartao();
        cartao.setSenha("1234");
        cartao.setNumero("6549873025634501");

        var response = cartaoService.salvar(cartao);
        var savedCartao = cartaoRepository.findByNumero(cartao.getNumero()).get();

        assertEquals(cartao.getNumero(), response.getNumero());
        assertEquals(cartao.getSenha(), response.getSenha());
        assertEquals(cartao.getSenha(), savedCartao.getSenha());
        assertEquals(cartao.getNumero(), savedCartao.getNumero());
        assertTrue(savedCartao.isActive());
    }

    @Test
    @Transactional
    @DisplayName("Deve registrar saldo inicial ao salvar um cartão")
    public void shouldSaveInitialBalanceWhenSavingCard() {
        var cartao = new Cartao();
        cartao.setSenha("1234");
        cartao.setNumero("6549873025634501");

        cartaoService.salvar(cartao);
        var savedCartaoSaldo = cartaoSaldoRepository.findByNumeroCartao(cartao.getNumero()).get();

        assertNotNull(savedCartaoSaldo);
        assertEquals(
                BigDecimal.valueOf(500).setScale(2, RoundingMode.UNNECESSARY),
                savedCartaoSaldo.getSaldo().setScale(2, RoundingMode.UNNECESSARY));
        assertEquals(cartao.getNumero(), savedCartaoSaldo.getCartao().getNumero());
    }

    @Test
    @Transactional
    @DisplayName("Deve registrar uma transação para o saldo inicial")
    public void shouldSaveTransactionForInitialBalance() {
        var cartao = new Cartao();
        cartao.setSenha("1234");
        cartao.setNumero("6549873025634501");

        cartaoService.salvar(cartao);
        var savedTransactionList = cartaoTransacaoRepository.findByCartaoNumero(cartao.getNumero());

        assertFalse(savedTransactionList.isEmpty());
        assertEquals(1, savedTransactionList.size());
        assertEquals(
                BigDecimal.valueOf(500).setScale(2, RoundingMode.UNNECESSARY),
                savedTransactionList.getFirst().getValor().setScale(2, RoundingMode.UNNECESSARY));
        assertEquals(TipoTransacao.ENTRADA, savedTransactionList.getFirst(). getTipo());
        assertEquals(cartao.getNumero(), savedTransactionList.getFirst().getCartao().getNumero());
    }

    @Test
    @DisplayName("Deve retornar SalvarCartaoException ao tentar salvar um cartao que já existe")
    public void shouldReturnExcepetionWhenTryingSaveCardAlreadyExists() {
        var cartao = new Cartao();
        cartao.setSenha("1234");
        cartao.setNumero("6549873025634502");

        cartaoService.salvar(cartao);
        assertThrows(
                SalvarCartaoException.class,
                () -> {
                    cartaoService.salvar(cartao);
                }
        );
    }

}
