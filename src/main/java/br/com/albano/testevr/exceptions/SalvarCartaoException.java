package br.com.albano.testevr.exceptions;

import br.com.albano.testevr.controllers.dtos.CartaoDTO;
import br.com.albano.testevr.services.domains.Cartao;
import lombok.Getter;

@Getter
public class SalvarCartaoException extends RuntimeException {

    private final CartaoDTO cartaoDTO;

    public SalvarCartaoException(Cartao cartao) {
        super();
        this.cartaoDTO = CartaoDTO.domainToDto(cartao);
    }

}