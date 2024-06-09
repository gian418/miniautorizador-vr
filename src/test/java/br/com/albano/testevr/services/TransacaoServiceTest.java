package br.com.albano.testevr.services;

import br.com.albano.testevr.services.domains.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {

    @Autowired
    TransacaoService transacaoService;

    @Autowired
    CartaoSaldoService cartaoSaldoService;

    @Test
    @Transactional
    public void test() throws InterruptedException {
        var transacao = new Transacao();
        transacao.setSenha("1234");
        transacao.setValor(BigDecimal.TEN);
        transacao.setNumeroCartao("6549873025634505");

        transacaoService.salvar(transacao);
        var saldo = cartaoSaldoService.getSaldo("6549873025634505");

        var saldoExpected = BigDecimal.valueOf(490).setScale(2, RoundingMode.UNNECESSARY);
        Assertions.assertEquals(saldoExpected, saldo.setScale(2, RoundingMode.UNNECESSARY));
    }
}
