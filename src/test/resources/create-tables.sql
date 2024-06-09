CREATE TABLE cartao (
                        id VARCHAR(36) PRIMARY KEY,
                        numero_cartao VARCHAR(16) NOT NULL,
                        senha VARCHAR(255) NOT NULL,
                        data_cadastro TIMESTAMP NOT NULL,
                        active BOOLEAN NOT NULL,
                        CONSTRAINT unique_numero_cartao UNIQUE (numero_cartao)
);

CREATE TABLE cartao_saldo (
                              id VARCHAR(36) PRIMARY KEY,
                              cartao_id VARCHAR(36) NOT NULL,
                              saldo DECIMAL(15, 2) NOT NULL,
                              data_cadastro TIMESTAMP NOT NULL,
                              ultima_atualizacao TIMESTAMP NULL,
                              FOREIGN KEY (cartao_id) REFERENCES cartao(id),
                              CONSTRAINT unique_cartao_saldo_cartao_id UNIQUE (cartao_id)
);

CREATE TABLE cartao_historico_transacao (
                                            id VARCHAR(36) PRIMARY KEY,
                                            cartao_id VARCHAR(36) NOT NULL,
                                            data_transacao TIMESTAMP NOT NULL,
                                            valor DECIMAL(15, 2) NOT NULL,
                                            tipo VARCHAR(255) NOT NULL,
                                            FOREIGN KEY (cartao_id) REFERENCES cartao(id)
);