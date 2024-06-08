package br.com.albano.testevr.repositories;

import br.com.albano.testevr.repositories.entities.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {

    Optional<CartaoEntity> findByNumero(String numero);
}
