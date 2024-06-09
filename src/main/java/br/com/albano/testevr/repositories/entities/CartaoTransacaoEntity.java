package br.com.albano.testevr.repositories.entities;

import br.com.albano.testevr.repositories.enums.TipoTransacao;
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
@Table(name = "cartao_historico_transacao")
public class CartaoTransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_id", nullable = false)
    private CartaoEntity cartao;

    @Column(name = "data_transacao", nullable = false, updatable = false)
    private LocalDateTime dataTransacao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
}
