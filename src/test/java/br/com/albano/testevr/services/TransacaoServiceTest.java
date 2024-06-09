package br.com.albano.testevr.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.albano.testevr.repositories.CartaoTransacaoRepository;
import br.com.albano.testevr.repositories.enums.TipoTransacao;
import br.com.albano.testevr.services.domains.Transacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {

    @Autowired
    TransacaoService transacaoService;

    @Autowired
    CartaoSaldoService cartaoSaldoService;

    @Autowired
    CartaoTransacaoRepository cartaoTransacaoRepository;

    @Test
    @Transactional
    public void test() throws InterruptedException {
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
}
