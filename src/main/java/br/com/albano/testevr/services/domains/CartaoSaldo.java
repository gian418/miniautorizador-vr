package br.com.albano.testevr.services.domains;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartaoSaldo {

    private UUID id;
    private String numero;
    private String senha;
    private BigDecimal saldo;
}
