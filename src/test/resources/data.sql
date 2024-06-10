INSERT INTO cartao (id, numero_cartao, senha, data_cadastro, active)
VALUES ('463bdaad-7171-465a-a9a4-481fc209170e'::uuid, '6549873025634505', '1234', '2024-06-09 12:03:13', true);

INSERT INTO cartao_saldo (id, cartao_id, saldo, data_cadastro, ultima_atualizacao)
VALUES ('041a3b75-4fbd-42f5-9202-68e6f1fc291e'::uuid, '463bdaad-7171-465a-a9a4-481fc209170e'::uuid, 500.00, '2024-06-09 12:03:13', '2024-06-09 12:03:13');

INSERT INTO cartao_historico_transacao (id, cartao_id, data_transacao, valor, tipo)
VALUES ('a1486d88-d0bd-4b20-9709-74ca3e0922a2'::uuid, '463bdaad-7171-465a-a9a4-481fc209170e'::uuid, '2024-06-09 12:03:13', 500.00, 'ENTRADA');

---

INSERT INTO cartao (id, numero_cartao, senha, data_cadastro, active)
VALUES ('0a58c66a-52c6-452f-b359-3f4b5a5a390c'::uuid, '6540000025634505', '1234', '2024-06-09 12:03:13', true);

INSERT INTO cartao_saldo (id, cartao_id, saldo, data_cadastro, ultima_atualizacao)
VALUES ('040ce96b-99d8-4a71-b71a-720e3eedd047'::uuid, '0a58c66a-52c6-452f-b359-3f4b5a5a390c'::uuid, 500.00, '2024-06-09 12:03:13', '2024-06-09 12:03:13');

INSERT INTO cartao_historico_transacao (id, cartao_id, data_transacao, valor, tipo)
VALUES ('15a12d79-c709-45bd-9834-8cbe6210ce51'::uuid, '0a58c66a-52c6-452f-b359-3f4b5a5a390c'::uuid, '2024-06-09 12:03:13', 500.00, 'ENTRADA');