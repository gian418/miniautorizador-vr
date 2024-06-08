package br.com.albano.testevr.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cartao_saldo")
public class CartaoSaldoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cartao_id", nullable = false, updatable = false)
    private CartaoEntity cartao;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

}
