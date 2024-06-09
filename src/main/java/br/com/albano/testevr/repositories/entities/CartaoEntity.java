package br.com.albano.testevr.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cartao")
public class CartaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "numero_cartao", updatable = false, nullable = false)
    private String numero;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "data_cadastro", updatable = false, nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToOne(mappedBy = "cartao", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    private CartaoSaldoEntity cartaoSaldo;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CartaoTransacaoEntity> historicoTransacoes;

}
