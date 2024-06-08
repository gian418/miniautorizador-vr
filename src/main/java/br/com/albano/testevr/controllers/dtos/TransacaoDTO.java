package br.com.albano.testevr.controllers.dtos;

import br.com.albano.testevr.services.domains.Transacao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {

    @NotEmpty(message = "É necessário informar o número do cartão")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter exatamente 16 caracteres")
    @Pattern(regexp = "\\d+", message = "O número do cartão deve conter apenas dígitos numéricos")
    private String numeroCartao;

    @NotEmpty(message = "É necessário informar uma senha")
    @Size(min = 4, max = 255, message = "A senha deve conter de 4 a 255 dígitos")
    @Pattern(regexp = "\\d+", message = "A senha deve conter apenas dígitos numéricos")
    private String senhaCartao;

    @NotNull(message = "É necessário informar o valor")
    private BigDecimal valor;

    public Transacao dtoToDomain() {
        var transacao = new Transacao();
        transacao.setNumeroCartao(numeroCartao);
        transacao.setValor(valor);
        transacao.setSenha(senhaCartao);
        return transacao;
    }
}
