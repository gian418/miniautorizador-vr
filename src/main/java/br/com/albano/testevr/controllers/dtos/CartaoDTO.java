package br.com.albano.testevr.controllers.dtos;

import br.com.albano.testevr.services.domains.Cartao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartaoDTO {

    @NotEmpty(message = "É necessário informar o número do cartão")
    @Size(min = 16, max = 16, message = "O número do cartão deve ter exatamente 16 caracteres")
    @Pattern(regexp = "\\d+", message = "O número do cartão deve conter apenas dígitos numéricos")
    private String numeroCartao;

    @NotEmpty(message = "É necessário informar uma senha")
    @Size(min = 4, max = 255, message = "A senha deve conter de 4 a 255 dígitos")
    @Pattern(regexp = "\\d+", message = "O número do cartão deve conter apenas dígitos numéricos")
    private String senha;

    public Cartao dtoToDomain() {
        var domain = new Cartao();
        domain.setNumero(numeroCartao);
        domain.setSenha(senha);
        return domain;
    }

    public static CartaoDTO domainToDto(Cartao domain) {
        var dto = new CartaoDTO();
        dto.setNumeroCartao(domain.getNumero());
        dto.setSenha(domain.getSenha());
        return dto;
    }
}
