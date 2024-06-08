package br.com.albano.testevr.services.impl;

import br.com.albano.testevr.exceptions.CartaoNaoEncontradoException;
import br.com.albano.testevr.repositories.CartaoSaldoRepository;
import br.com.albano.testevr.services.CartaoSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoSaldoServiceImpl implements CartaoSaldoService {

    @Autowired
    private CartaoSaldoRepository cartaoSaldoRepository;

    @Override
    public BigDecimal getSaldo(String numeroCartao) {
        return cartaoSaldoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(CartaoNaoEncontradoException::new)
                .getSaldo();
    }
}
