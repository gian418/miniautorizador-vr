package br.com.albano.testevr.repositories;

import br.com.albano.testevr.repositories.entities.CartaoSaldoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartaoSaldoRepository extends JpaRepository<CartaoSaldoEntity, UUID> {

    @Query("SELECT cs FROM CartaoSaldoEntity cs JOIN cs.cartao c WHERE c.numero = :numeroCartao")
    Optional<CartaoSaldoEntity> findByNumeroCartao(@Param("numeroCartao") String numeroCartao);
}
