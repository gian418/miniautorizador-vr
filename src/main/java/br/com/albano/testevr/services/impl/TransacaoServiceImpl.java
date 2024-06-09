package br.com.albano.testevr.services.impl;

import static br.com.albano.testevr.services.enums.TipoFalhaTransacao.CARTAO_INEXISTENTE;
import static br.com.albano.testevr.services.enums.TipoFalhaTransacao.SALDO_INSUFICIENTE;
import static br.com.albano.testevr.services.enums.TipoFalhaTransacao.SENHA_INVALIDA;

import br.com.albano.testevr.exceptions.*;
import br.com.albano.testevr.repositories.CartaoRepository;
import br.com.albano.testevr.repositories.CartaoSaldoRepository;
import br.com.albano.testevr.repositories.CartaoTransacaoRepository;
import br.com.albano.testevr.repositories.entities.CartaoTransacaoEntity;
import br.com.albano.testevr.repositories.enums.TipoTransacao;
import br.com.albano.testevr.services.TransacaoService;
import br.com.albano.testevr.services.domains.Transacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class TransacaoServiceImpl implements TransacaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoSaldoRepository cartaoSaldoRepository;

    @Autowired
    private CartaoTransacaoRepository cartaoTransacaoRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void salvar(Transacao transacao) {
        var cartaoEntity = cartaoRepository.findByNumero(transacao.getNumeroCartao())
                .orElseThrow(() -> new TransacaoFalhouException(CARTAO_INEXISTENTE.name()));

        validarSenha(cartaoEntity.getSenha(), transacao.getSenha());

        try {
            var cartaoSaldoEntity = cartaoSaldoRepository.findByNumeroCartao(transacao.getNumeroCartao())
                    .orElseThrow(() -> new TransacaoFalhouException(CARTAO_INEXISTENTE.name()));
            entityManager.lock(cartaoSaldoEntity, LockModeType.PESSIMISTIC_WRITE);

            var saldoFinal = validarSaldo(cartaoSaldoEntity.getSaldo(), transacao.getValor());
            cartaoSaldoEntity.setSaldo(saldoFinal);
            cartaoSaldoEntity.setUltimaAtualizacao(LocalDateTime.now());

            var transacaoEntity = new CartaoTransacaoEntity();
            transacaoEntity.setTipo(TipoTransacao.SAIDA);
            transacaoEntity.setValor(transacao.getValor());
            transacaoEntity.setCartao(cartaoEntity);
            transacaoEntity.setDataTransacao(LocalDateTime.now());

            cartaoTransacaoRepository.save(transacaoEntity);
            cartaoSaldoRepository.save(cartaoSaldoEntity);
        } catch (Exception e) {
            log.error("Transação falhou", e);
            throw new TransacaoFalhouException(e.getMessage());
        }
    }

    private BigDecimal validarSaldo(BigDecimal saldo, BigDecimal valorTransacao) {
        BigDecimal saldoFinal = saldo.subtract(valorTransacao);
        return Optional.of(saldoFinal)
                .filter(sf -> sf.compareTo(BigDecimal.ZERO) >= 0)
                .orElseThrow(() -> new TransacaoFalhouException(SALDO_INSUFICIENTE.name()));
    }

    private void validarSenha(String senha, String senhaInformada) {
        if (!Objects.equals(senha, senhaInformada)) {
            throw new TransacaoFalhouException(SENHA_INVALIDA.name());
        }
    }

}
