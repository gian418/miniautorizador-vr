package br.com.albano.testevr.services.impl;

import br.com.albano.testevr.repositories.CartaoRepository;
import br.com.albano.testevr.services.CartaoService;
import br.com.albano.testevr.services.domains.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoServiceImpl implements CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Override
    public Cartao salvar(Cartao cartao) {
        var cartaoEntitySalvo = cartaoRepository.save(cartao.toEntity());
        return Cartao.toDomain(cartaoEntitySalvo);
    }
}
