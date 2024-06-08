package br.com.albano.testevr.services.domains;

import br.com.albano.testevr.repositories.entities.CartaoEntity;
import br.com.albano.testevr.repositories.entities.CartaoSaldoEntity;
import br.com.albano.testevr.repositories.entities.CartaoTransacaoEntity;
import br.com.albano.testevr.repositories.enums.TipoTransacao;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cartao {

    private static BigDecimal SALDO_INICIAL = BigDecimal.valueOf(500);

    private String numero;
    private String senha;

    public CartaoEntity toEntity() {
        List<CartaoTransacaoEntity> cartaoTransacaoEntityList = new ArrayList<>();
        var cartaoEntity = buildCartaoEntity();
        var cartaoTransacaoEntity = buildCartaoTransacaoEntity(cartaoEntity);
        var cartaoSaldoEntity = buildCartaoSaldoEntity(cartaoTransacaoEntity);
        cartaoTransacaoEntityList.add(cartaoTransacaoEntity);
        cartaoEntity.setCartaoSaldo(cartaoSaldoEntity);
        cartaoEntity.setHistoricoTransacoes(cartaoTransacaoEntityList);
        return cartaoEntity;
    }

    public static Cartao toDomain(CartaoEntity entity) {
        var domain = new Cartao();
        domain.setNumero(entity.getNumero());
        domain.setSenha(entity.getSenha());
        return domain;
    }

    private CartaoEntity buildCartaoEntity() {
        var cartaoEntity = new CartaoEntity();
        cartaoEntity.setNumero(this.numero);
        cartaoEntity.setSenha(this.senha);
        return cartaoEntity;
    }

    private CartaoTransacaoEntity buildCartaoTransacaoEntity(CartaoEntity cartaoEntity) {
        var cartaoTransacaoEntity = new CartaoTransacaoEntity();
        cartaoTransacaoEntity.setValor(SALDO_INICIAL);
        cartaoTransacaoEntity.setTipo(TipoTransacao.ENTRADA);
        cartaoTransacaoEntity.setCartao(cartaoEntity);
        return cartaoTransacaoEntity;
    }

    private CartaoSaldoEntity buildCartaoSaldoEntity(CartaoTransacaoEntity cartaoTransacaoEntity) {
        var cartaoSaldoEntity = new CartaoSaldoEntity();
        cartaoSaldoEntity.setSaldo(cartaoTransacaoEntity.getValor());
        cartaoSaldoEntity.setCartao(cartaoTransacaoEntity.getCartao());
        cartaoSaldoEntity.setDataCadastro(LocalDateTime.now());
        return cartaoSaldoEntity;
    }
}
