package br.com.albano.testevr.services.domains;

import br.com.albano.testevr.repositories.entities.CartaoEntity;
import lombok.Data;

@Data
public class Cartao {

    private String numero;
    private String senha;

    public CartaoEntity toEntity() {
        var entity = new CartaoEntity();
        entity.setNumero(this.numero);
        entity.setSenha(this.senha);
        return entity;
    }

    public static Cartao toDomain(CartaoEntity entity) {
        var domain = new Cartao();
        domain.setNumero(entity.getNumero());
        domain.setSenha(entity.getSenha());
        return domain;
    }
}
