package br.com.albano.testevr.services.domains;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Transacao {

    private String numeroCartao;
    private String senha;
    private BigDecimal valor;
}
