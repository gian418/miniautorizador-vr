CREATE TABLE cartao_saldo (
    id VARCHAR(36) PRIMARY KEY,
    cartao_id VARCHAR(36) NOT NULL,
    saldo DECIMAL(15, 2) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    ultima_atualizacao TIMESTAMP NULL,
    FOREIGN KEY (cartao_id) REFERENCES cartao(id),
    UNIQUE KEY unique_cartao_saldo_cartao_id (cartao_id)
);

CREATE TABLE cartao_historico_transacao (
    id VARCHAR(36) PRIMARY KEY,
    cartao_id VARCHAR(36) NOT NULL,
    data_transacao TIMESTAMP NOT NULL,
    valor DECIMAL(15, 2) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    INDEX idx_cartao_historico_transacao_tipo (tipo),
    FOREIGN KEY (cartao_id) REFERENCES cartao(id)
);