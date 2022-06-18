SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS banco ;
CREATE SCHEMA IF NOT EXISTS banco DEFAULT CHARACTER SET utf8 ;
USE banco ;

DROP TABLE IF EXISTS Usuario ;

CREATE TABLE IF NOT EXISTS Usuario (
  idUsuario INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(45) NOT NULL,
  cpf VARCHAR(45) NOT NULL,
  nivelAcesso VARCHAR(45) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  cidade VARCHAR(45) NOT NULL,
  bairro VARCHAR(45) NOT NULL,
  rua VARCHAR(45) NOT NULL,
  numero VARCHAR(45) NOT NULL,
  contato VARCHAR(45) NOT NULL,
  email VARCHAR(45) NULL,
  PRIMARY KEY (idUsuario))
ENGINE = InnoDB;

INSERT INTO Usuario VALUES(1,"João Porcel","123","444.444.444-44","Cliente","SP","Presidente Epitácio",
"Bairro A","Rua A","3-35","(18)98134-3434","jp@hotmail.com");

INSERT INTO Usuario VALUES(2,"Funcionario","123","555.555.555-55","Funcionario","SP","Presidente Epitácio",
"Bairro B","Rua V","3-35","(18)98135-3132","Não Informado");

INSERT INTO Usuario VALUES(3,"Admin","123","666.666.666-66","Administrador","SP","Presidente Epitácio",
"Bairro A","Rua B","13-20","(18)98134-3434","admin@hotmail.com");

INSERT INTO Usuario VALUES(4,"Marcos","123","333.333.333-33","Cliente","SP","Presidente Prudente",
"Bairro B","Rua C","3-35","(18)98135-3537","ashe@hotmail.com");

SELECT * FROM Usuario;

DROP TABLE IF EXISTS Agencia ;

CREATE TABLE IF NOT EXISTS Agencia (
  idAgencia INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  nrAgencia VARCHAR(15) NOT NULL,
  PRIMARY KEY (idAgencia))
ENGINE = InnoDB;

INSERT INTO Agencia VALUES(1,"Agência 1","1");
INSERT INTO Agencia VALUES(1,"Agência 2","2");
INSERT INTO Agencia VALUES(1,"Agência 3","3");

SELECT * FROM Agencia;

DROP TABLE IF EXISTS Conta ;

CREATE TABLE IF NOT EXISTS Conta (
  idConta INT NOT NULL AUTO_INCREMENT,
  nrConta VARCHAR(45) NOT NULL,
  senha VARCHAR(45) NOT NULL,
  status VARCHAR(45) NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  limite DOUBLE NOT NULL,
  saldo DOUBLE NOT NULL,
  Usuario_idUsuario INT NOT NULL,
  Agencia_idAgencia INT NOT NULL,
  PRIMARY KEY (idConta),
  CONSTRAINT fk_Conta_Cliente
    FOREIGN KEY (Usuario_idUsuario)
    REFERENCES Usuario (idUsuario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Conta_Agencia1
    FOREIGN KEY (Agencia_idAgencia)
    REFERENCES Agencia (idAgencia)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX fk_Conta_Cliente_idx ON Conta (Usuario_idUsuario ASC) VISIBLE;

CREATE INDEX fk_Conta_Agencia1_idx ON Conta (Agencia_idAgencia ASC) VISIBLE;

INSERT INTO Conta VALUES(1,"1","123","Ativada","Especial",100,1000,1,1);
INSERT INTO Conta VALUES(2,"4","123","Ativada","Comum",0,0,4,1);

SELECT * FROM Conta;

DROP TABLE IF EXISTS Saque ;

CREATE TABLE IF NOT EXISTS Saque (
  idSaque INT NOT NULL AUTO_INCREMENT,
  valor DOUBLE NOT NULL,
  dataSaque DATE NOT NULL,
  Conta_idConta INT NOT NULL,
  PRIMARY KEY (idSaque),
  CONSTRAINT fk_Saque_Conta1
    FOREIGN KEY (Conta_idConta)
    REFERENCES Conta (idConta)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX fk_Saque_Conta1_idx ON Saque (Conta_idConta ASC) VISIBLE;

SELECT * FROM Saque;

DROP TABLE IF EXISTS Deposito ;

CREATE TABLE IF NOT EXISTS Deposito (
  idDeposito INT NOT NULL AUTO_INCREMENT,
  valor DOUBLE NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  dataDeposito DATE NOT NULL,
  dataAutorizacao DATE NULL,
  Conta_idConta INT NOT NULL,
  PRIMARY KEY (idDeposito),
  CONSTRAINT fk_Deposito_Conta1
    FOREIGN KEY (Conta_idConta)
    REFERENCES Conta (idConta)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX fk_Deposito_Conta1_idx ON Deposito (Conta_idConta ASC) VISIBLE;

SELECT * FROM Deposito;
DELETE FROM Deposito WHERE idDeposito = 1;

DROP TABLE IF EXISTS Transferencia ;

CREATE TABLE IF NOT EXISTS Transferencia (
  idTransferencia INT NOT NULL AUTO_INCREMENT,
  data DATE NOT NULL,
  valor DOUBLE NOT NULL,
  idConta VARCHAR(45) NOT NULL COMMENT 'Esse será a id da conta que vai receber o dinheiro\n',
  idAgencia INT NOT NULL COMMENT 'Esse será o id da Agencia da conta que está recebendo a transferencia',
  Conta_idConta INT NOT NULL,
  PRIMARY KEY (idTransferencia),
  CONSTRAINT fk_Transferencia_Conta1
    FOREIGN KEY (Conta_idConta)
    REFERENCES Conta (idConta)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX fk_Transferencia_Conta1_idx ON Transferencia (Conta_idConta ASC) VISIBLE;

SELECT * FROM Transferencia;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
