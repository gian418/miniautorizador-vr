CREATE TABLE cartao (
    id VARCHAR(36) PRIMARY KEY,
    numero_cartao VARCHAR(16) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,
    active TINYINT(1) NOT NULL,
    UNIQUE INDEX unique_numero_cartao (numero_cartao),
    INDEX idx_active (active)
);