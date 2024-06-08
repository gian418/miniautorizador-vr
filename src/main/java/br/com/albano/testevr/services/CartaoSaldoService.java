package br.com.albano.testevr.services;

import java.math.BigDecimal;

public interface CartaoSaldoService {

    BigDecimal getSaldo(String numeroCartao);
}
