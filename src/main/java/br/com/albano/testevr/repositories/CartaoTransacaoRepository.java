package br.com.albano.testevr.repositories;

import br.com.albano.testevr.repositories.entities.CartaoTransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartaoTransacaoRepository extends JpaRepository<CartaoTransacaoEntity, UUID> {
}
