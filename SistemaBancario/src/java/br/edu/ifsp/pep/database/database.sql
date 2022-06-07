SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

DROP SCHEMA IF EXISTS banco ;
CREATE SCHEMA IF NOT EXISTS banco DEFAULT CHARACTER SET utf8 ;
USE banco ;

DROP TABLE IF EXISTS Usuario ;

CREATE TABLE IF NOT EXISTS Usuario (
  idUsuario INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(45) NOT NULL,
  cpf VARCHAR(45) NOT NULL,
  nivelAcesso VARCHAR(45) NOT NULL,
  bairro VARCHAR(45) NOT NULL,
  rua VARCHAR(45) NOT NULL,
  numero VARCHAR(45) NOT NULL,
  contato VARCHAR(45) NOT NULL,
  email VARCHAR(45) NULL,
  PRIMARY KEY (idUsuario))
ENGINE = InnoDB;

INSERT INTO Usuario VALUES(1,"João Porcel","123","441.441.441-41","Cliente",
"Bairro A","Rua A","3-35","(18)98134-3434","jp@hotmail.com");


INSERT INTO Usuario VALUES(2,"Funcionario","123","551.551.551-51","Funcionario",
"Bairro B","Rua V","3-35","(18)98135-3132",null);


INSERT INTO Usuario VALUES(3,"Admin","123","666.666.666-66","Administrador",
"Bairro A","Rua B","13-20","(18)98134-3434","admin@hotmail.com");

SELECT * FROM Usuario;

DROP TABLE IF EXISTS Agencia ;

CREATE TABLE IF NOT EXISTS Agencia (
  idAgencia INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  nrAgencia VARCHAR(15) NOT NULL,
  PRIMARY KEY (idAgencia))
ENGINE = InnoDB;

DROP TABLE IF EXISTS Conta ;

CREATE TABLE IF NOT EXISTS Conta (
  idConta INT NOT NULL,
  senha VARCHAR(45) NOT NULL,
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

DROP TABLE IF EXISTS Saque ;

CREATE TABLE IF NOT EXISTS Saque (
  idSaque INT NOT NULL,
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

DROP TABLE IF EXISTS Deposito ;

CREATE TABLE IF NOT EXISTS Deposito (
  idDeposito INT NOT NULL,
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

DROP TABLE IF EXISTS Transferencia ;

CREATE TABLE IF NOT EXISTS Transferencia (
  idTransferencia INT NOT NULL,
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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
