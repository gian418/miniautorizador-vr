package br.com.albano.testevr.services;

import br.com.albano.testevr.config.AbstractWebConfigTest;
import br.com.albano.testevr.exceptions.TransacaoFalhouException;
import br.com.albano.testevr.repositories.CartaoTransacaoRepository;
import br.com.albano.testevr.repositories.enums.TipoTransacao;
import br.com.albano.testevr.services.domains.Transacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class TransacaoServiceTest extends AbstractWebConfigTest {

    @Autowired
    TransacaoService transacaoService;

    @Autowired
    CartaoSaldoService cartaoSaldoService;

    @Autowired
    CartaoTransacaoRepository cartaoTransacaoRepository;

    @Test
    @Transactional
    @DisplayName("Deve alterar o saldo do cartão ao salvar uma nova transação")
    public void shouldChangeBalanceWhenSavingTransaction() {
        var transacao = new Transacao();
        transacao.setSenha("1234");
        transacao.setValor(BigDecimal.TEN);
        transacao.setNumeroCartao("6549873025634505");

        transacaoService.salvar(transacao);
        var saldo = cartaoSaldoService.getSaldo(transacao.getNumeroCartao());

        var saldoExpected = BigDecimal.valueOf(490).setScale(2, RoundingMode.UNNECESSARY);
        var transacaoList = cartaoTransacaoRepository.findByCartaoNumero(transacao.getNumeroCartao());

        var entradas = transacaoList.stream().filter(t -> t.getTipo() == TipoTransacao.ENTRADA).toList();
        var saidas = transacaoList.stream().filter(t -> t.getTipo() == TipoTransacao.SAIDA).toList();

        assertEquals(saldoExpected, saldo.setScale(2, RoundingMode.UNNECESSARY));
        assertEquals(2, transacaoList.size());
        assertEquals(1, entradas.size());
        assertEquals(1, saidas.size());
        assertEquals(new BigDecimal("500").setScale(2), entradas.getFirst().getValor().setScale(2));
        assertEquals(new BigDecimal("10").setScale(2), saidas.getFirst().getValor().setScale(2));
    }

    @Test
    @Transactional
    @DisplayName("Deve alterar o saldo do cartão ao salvar duas novas transações")
    public void shouldChangeBalanceWhenSavingTwoTransactions() {
        var transacao = new Transacao();
        transacao.setSenha("1234");
        transacao.setValor(BigDecimal.TEN);
        transacao.setNumeroCartao("6549873025634505");

        transacaoService.salvar(transacao);
        transacaoService.salvar(transacao);
        var saldo = cartaoSaldoService.getSaldo(transacao.getNumeroCartao());

        var saldoExpected = BigDecimal.valueOf(480).setScale(2, RoundingMode.UNNECESSARY);
        var transacaoList = cartaoTransacaoRepository.findByCartaoNumero(transacao.getNumeroCartao());

        var entradas = transacaoList.stream().filter(t -> t.getTipo() == TipoTransacao.ENTRADA).toList();
        var saidas = transacaoList.stream().filter(t -> t.getTipo() == TipoTransacao.SAIDA).toList();

        assertEquals(saldoExpected, saldo.setScale(2, RoundingMode.UNNECESSARY));
        assertEquals(3, transacaoList.size());
        assertEquals(1, entradas.size());
        assertEquals(2, saidas.size());
        assertEquals(new BigDecimal("500").setScale(2), entradas.getFirst().getValor().setScale(2));
        assertEquals(new BigDecimal("10").setScale(2), saidas.getFirst().getValor().setScale(2));
        assertEquals(new BigDecimal("10").setScale(2), saidas.getLast().getValor().setScale(2));
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar TransacaoFalhouException quando as senhas forem diferentes")
    public void shouldReturnExceptionWhenPasswordIsInvalid() {
        var transacao = new Transacao();
        transacao.setSenha("4321");
        transacao.setValor(BigDecimal.TEN);
        transacao.setNumeroCartao("6549873025634505");

        var ex = assertThrows(TransacaoFalhouException.class, () -> transacaoService.salvar(transacao));
        assertEquals("SENHA_INVALIDA", ex.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar TransacaoFalhouException quando cartão não existir")
    public void shouldReturnExceptionWhenCardNotExists() {
        var transacao = new Transacao();
        transacao.setSenha("1234");
        transacao.setValor(BigDecimal.TEN);
        transacao.setNumeroCartao("1234873025634505");

        var ex = assertThrows(TransacaoFalhouException.class, () -> transacaoService.salvar(transacao));
        assertEquals("CARTAO_INEXISTENTE", ex.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("Deve retornar TransacaoFalhouException quando saldo não for suficiente")
    public void shouldReturnExceptionWhenInsufficientBalance() {
        var transacao = new Transacao();
        transacao.setSenha("1234");
        transacao.setValor(new BigDecimal("600"));
        transacao.setNumeroCartao("6549873025634505");

        var ex = assertThrows(TransacaoFalhouException.class, () -> transacaoService.salvar(transacao));
        assertEquals("SALDO_INSUFICIENTE", ex.getMessage());
    }

}
