INSERT INTO Transacao (cartao, valor, data_hora, estabelecimento, nsu, codigo_autorizacao, status, tipo, parcelas)
VALUES('444444444444', 500.00, '2021-01-01 18:00:00', 'petshop mundo cao', '1234567890', '147258369', 'AUTORIZADO', 'AVISTA', 1),
('444444444444', 500.00, '2021-01-01 18:00:00', 'petshop mundo cao', '1234567890', '147258369', 'NEGADO', 'PARCELADO_LOJA', 1),
('444444444444', 500.00, '2021-01-01 18:00:00', 'petshop mundo cao', '1234567890', '147258369', 'CANCELADO', 'PARCELADO_EMISSOR', 1);

INSERT INTO saldo_cartao (saldo) values('500.00');