package br.com.albano.testevr.services.impl;

import br.com.albano.testevr.exceptions.SalvarCartaoException;
import br.com.albano.testevr.repositories.CartaoRepository;
import br.com.albano.testevr.services.CartaoService;
import br.com.albano.testevr.services.domains.Cartao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartaoServiceImpl implements CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Override
    public Cartao salvar(Cartao cartao) {
        try {
            var cartaoEntitySalvo = cartaoRepository.save(cartao.toEntity());
            return Cartao.toDomain(cartaoEntitySalvo);
        } catch (Exception e) {
            log.error("Erro ao salvar o cartão", e);
            throw new SalvarCartaoException(cartao);
        }

    }
}
