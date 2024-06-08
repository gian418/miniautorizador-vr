package br.com.albano.testevr.services.domains;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transacao {

    private String numeroCartao;
    private String senha;
    private BigDecimal valor;
}
