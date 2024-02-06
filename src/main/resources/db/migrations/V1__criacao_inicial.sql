CREATE TABLE `saldo_cartao`(
  `id` bigint NOT NULL AUTO_INCREMENT,
  `saldo` decimal(10,2) DEFAULT '500.00',
  PRIMARY KEY (`id`)
);

CREATE TABLE `transacao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cartao` varchar(16) DEFAULT NULL,
  `codigo_autorizacao` varchar(255) DEFAULT NULL,
  `data_hora` datetime(6) DEFAULT NULL,
  `estabelecimento` varchar(255) DEFAULT NULL,
  `nsu` varchar(255) DEFAULT NULL,
  `status` enum('AUTORIZADO','CANCELADO','NEGADO') DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `parcelas` int DEFAULT NULL,
  `tipo` enum('AVISTA','PARCELADO_EMISSOR','PARCELADO_LOJA') DEFAULT NULL,
  PRIMARY KEY (`id`)
);