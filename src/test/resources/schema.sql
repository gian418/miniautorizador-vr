CREATE TABLE cartao (
                        id UUID PRIMARY KEY,
                        numero_cartao VARCHAR(16) NOT NULL,
                        senha VARCHAR(255) NOT NULL,
                        data_cadastro TIMESTAMP NOT NULL,
                        active BOOLEAN NOT NULL,
                        CONSTRAINT unique_numero_cartao UNIQUE (numero_cartao)
);

CREATE TABLE cartao_saldo (
                              id UUID PRIMARY KEY,
                              cartao_id UUID NOT NULL,
                              saldo DECIMAL(15, 2) NOT NULL,
                              data_cadastro TIMESTAMP NOT NULL,
                              ultima_atualizacao TIMESTAMP NULL,
                              FOREIGN KEY (cartao_id) REFERENCES cartao(id)
);

CREATE TABLE cartao_historico_transacao (
                                            id UUID PRIMARY KEY,
                                            cartao_id UUID NOT NULL,
                                            data_transacao TIMESTAMP NOT NULL,
                                            valor DECIMAL(15, 2) NOT NULL,
                                            tipo VARCHAR(255) NOT NULL,
                                            FOREIGN KEY (cartao_id) REFERENCES cartao(id)
);

