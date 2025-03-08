CREATE DATABASE vacinacao;

USE vacinacao;

CREATE TABLE `vacina` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vacina` varchar(50) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `limite_aplicacao` int DEFAULT NULL,
  `publico_alvo` enum('CRIANÇA','ADOLESCENTE','ADULTO','GESTANTE') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vacina_UNIQUE` (`vacina`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `paciente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `sexo` enum('M','F') NOT NULL,
  `data_nascimento` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `dose` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_vacina` int NOT NULL,
  `dose` varchar(45) NOT NULL,
  `idade_recomendada_aplicacao` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id_vacina_dose` (`id_vacina`,`dose`),
  KEY `fk_vacina_id_idx` (`id_vacina`),
  CONSTRAINT `fk_vacina_id` FOREIGN KEY (`id_vacina`) REFERENCES `vacina` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `imunizacoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `id_dose` int NOT NULL,
  `data_aplicacao` date NOT NULL,
  `fabricante` varchar(45) DEFAULT NULL,
  `lote` varchar(45) DEFAULT NULL,
  `local_aplicacao` varchar(45) DEFAULT NULL,
  `profissional_aplicador` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_dose_paciente` (`id_paciente`,`id_dose`),
  KEY `fk_paciente_id_idx` (`id_paciente`),
  KEY `fk_dose_id_idx` (`id_dose`) ,
  CONSTRAINT `fk_dose_id` FOREIGN KEY (`id_dose`) REFERENCES `dose` (`id`),
  CONSTRAINT `fk_paciente_id` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


INSERT INTO vacina VALUES (1,'BCG','Proteção contra formas graves de tuberculose (meníngea e miliar)', 60,'CRIANÇA');
INSERT INTO vacina VALUES (2,'Hepatite B','Proteção contra o vírus da Hepatite B', NULL,'CRIANÇA');
INSERT INTO vacina VALUES (3,'Poliomielite 1, 2 e 3','Proteção contra o vírus da Poliomielite', NULL,'CRIANÇA');
INSERT INTO vacina VALUES (4,'Rotavírus','Proteção contra o rotavírus humano G1P[8] (ROTA)', 9, 'CRIANÇA');
INSERT INTO vacina VALUES (5,'Pentavalente (DTP, HB, Hib)','Proteção contra DTP (difteria, tétano, coqueluche), HB (hepatite b) e Hib (influenza tipo b)', 84 ,'CRIANÇA');
INSERT INTO vacina VALUES (6,'Pneumocócica 10','Proteção contra Pneumonias, Meningites, Otites e Sinusites pelos sorotipos que compõem a vacina', 60, 'CRIANÇA');
INSERT INTO vacina VALUES (7,'Meningocócica C','Proteção contra Meningite meningocócica tipo C',60,'CRIANÇA');
INSERT INTO vacina VALUES (8,'COdoseVID-19','Proteção contra o vírus SARS-CoV-2', 60 ,'CRIANÇA');
INSERT INTO vacina VALUES (9,'Febre Amarela','Proteção contra o vírus da febre amarela',NULL,'CRIANÇA');
INSERT INTO vacina VALUES (10,'Tríplice Viral','Proteção contra os vírus: sarampo, caxumba e rubéola', 72,'CRIANÇA');
INSERT INTO vacina VALUES (11,'Tetraviral','Proteção contra os vírus: sarampo, caxumba, rubéola e varicela', 84,'CRIANÇA');
INSERT INTO vacina VALUES (12,'Hepatite A','Proteção contra o vírus da Hepatite A', 60,'CRIANÇA');
INSERT INTO vacina VALUES (13,'DTP ','Proteção contra Difteria, Tétano e Pertussis', 84,'CRIANÇA');
INSERT INTO vacina VALUES (14,'HPV4','Proteção contra o Papilomavírus Humano 6, 11, 16 e 18', 180,'CRIANÇA');
INSERT INTO vacina VALUES (15,'Pneumocócica 23','Proteção contra Meningites bacterianas, Pneumonias, Sinusite e outros',NULL,'CRIANÇA');
INSERT INTO vacina VALUES (16,'Varicela','Proteção contra o vírus da varicela (catapora)', 156 ,'CRIANÇA');

INSERT INTO dose values(1,1,'Dose Única',0);
INSERT INTO dose values(2,2,'Dose Única',0);
INSERT INTO dose values(3,3,'1ª Dose',2);
INSERT INTO dose values(4,3,'2ª Dose',4);
INSERT INTO dose values(5,3,'3ª Dose',6);
INSERT INTO dose values(6,4,'1ª Dose',2);
INSERT INTO dose values(7,4,'2ª Dose',4);
INSERT INTO dose values(8,5,'1ª Dose',2);
INSERT INTO dose values(9,5,'2ª Dose',4);
INSERT INTO dose values(10,5,'3ª Dose',6);
INSERT INTO dose values(11,6,'1ª Dose',2);
INSERT INTO dose values(12,6,'2ª Dose',4);
INSERT INTO dose values(13,6,'Reforço',12);
INSERT INTO dose values(14,7,'1ª Dose',3);
INSERT INTO dose values(15,7,'2ª Dose',5);
INSERT INTO dose values(16,7,'Reforço',12);
INSERT INTO dose values(17,8,'1ª Dose',6);
INSERT INTO dose values(18,8,'2ª Dose',7);
INSERT INTO dose values(19,9,'1ª Dose',9);
INSERT INTO dose values(20,9,'Reforço',48);
INSERT INTO dose values(21,10,'1ª Dose',12);
INSERT INTO dose values(22,11,'1ª Dose',15);
INSERT INTO dose values(23,12,'Dose Única',15);
INSERT INTO dose values(24,13,'1ª Reforço',15);
INSERT INTO dose values(25,13,'2ª Reforço',48);
INSERT INTO dose values(26,14,'Dose Única',108);
INSERT INTO dose values(27,15,'1ª Dose',60);
INSERT INTO dose values(28,15,'2ª Dose',120);
INSERT INTO dose values(29,16,'2ª Dose',48);
