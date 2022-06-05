DROP DATABASE IF EXISTS banco ;
CREATE DATABASE IF NOT EXISTS banco;
USE banco ;

DROP TABLE IF EXISTS Cliente ;

CREATE TABLE IF NOT EXISTS Cliente (
  idCliente INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  bairro VARCHAR(45) NOT NULL,
  rua VARCHAR(45) NOT NULL,
  contato VARCHAR(45) NOT NULL,
  email VARCHAR(45) NULL,
  PRIMARY KEY (idCliente))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Agencia
-- -----------------------------------------------------
DROP TABLE IF EXISTS Agencia ;

CREATE TABLE IF NOT EXISTS Agencia (
  idAgencia INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  nrAgencia VARCHAR(15) NOT NULL,
  PRIMARY KEY (idAgencia))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Conta
-- -----------------------------------------------------
DROP TABLE IF EXISTS Conta ;

CREATE TABLE IF NOT EXISTS Conta (
  idConta INT NOT NULL,
  senha VARCHAR(45) NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  limite DOUBLE NOT NULL,
  saldo DOUBLE NOT NULL,
  Cliente_idCliente INT NOT NULL,
  Agencia_idAgencia INT NOT NULL,
  PRIMARY KEY (idConta),
  INDEX fk_Conta_Cliente_idx (Cliente_idCliente ASC) VISIBLE,
  INDEX fk_Conta_Agencia1_idx (Agencia_idAgencia ASC) VISIBLE,
  CONSTRAINT fk_Conta_Cliente
    FOREIGN KEY (Cliente_idCliente)
    REFERENCES Cliente (idCliente)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Conta_Agencia1
    FOREIGN KEY (Agencia_idAgencia)
    REFERENCES Agencia (idAgencia)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Funcionario
-- -----------------------------------------------------
DROP TABLE IF EXISTS Funcionario ;

CREATE TABLE IF NOT EXISTS Funcionario (
  idFuncionario INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(45) NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  PRIMARY KEY (idFuncionario))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Saque
-- -----------------------------------------------------
DROP TABLE IF EXISTS Saque ;

CREATE TABLE IF NOT EXISTS Saque (
  idSaque INT NOT NULL,
  valor DOUBLE NOT NULL,
  dataSaque DATE NOT NULL,
  Conta_idConta INT NOT NULL,
  Conta_idCliente INT NOT NULL,
  Conta_idAgencia INT NOT NULL,
  PRIMARY KEY (idSaque),
  INDEX fk_Saque_Conta1_idx (Conta_idConta ASC, Conta_idCliente ASC, Conta_idAgencia ASC) VISIBLE,
  CONSTRAINT fk_Saque_Conta1
    FOREIGN KEY (Conta_idConta , Conta_idCliente , Conta_idAgencia)
    REFERENCES Conta (idConta , Cliente_idCliente , Agencia_idAgencia)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Deposito
-- -----------------------------------------------------
DROP TABLE IF EXISTS Deposito ;

CREATE TABLE IF NOT EXISTS Deposito (
  idDeposito INT NOT NULL,
  valor DOUBLE NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  dataDeposito DATE NOT NULL,
  dataAutorizacao DATE NULL,
  Conta_idConta INT NOT NULL,
  Conta_idCliente INT NOT NULL,
  Conta_idAgencia INT NOT NULL,
  Funcionario_idFuncionario INT NULL,
  PRIMARY KEY (idDeposito),
  INDEX fk_Deposito_Conta1_idx (Conta_idConta ASC, Conta_idCliente ASC, Conta_idAgencia ASC) VISIBLE,
  INDEX fk_Deposito_Funcionario1_idx (Funcionario_idFuncionario ASC) VISIBLE,
  CONSTRAINT fk_Deposito_Conta1
    FOREIGN KEY (Conta_idConta , Conta_idCliente , Conta_idAgencia)
    REFERENCES Conta (idConta , Cliente_idCliente , Agencia_idAgencia)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Deposito_Funcionario1
    FOREIGN KEY (Funcionario_idFuncionario)
    REFERENCES Funcionario (idFuncionario)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Transferencia
-- -----------------------------------------------------
DROP TABLE IF EXISTS Transferencia ;

CREATE TABLE IF NOT EXISTS Transferencia (
  idTransferencia INT NOT NULL,
  data DATE NOT NULL,
  valor DOUBLE NOT NULL,
  idConta VARCHAR(45) NOT NULL COMMENT 'Esse será a id da conta que vai receber o dinheiro\n',
  idCliente INT NOT NULL COMMENT 'Esse é o id do Cliente que vai receber a transferência',
  idAgencia INT NOT NULL COMMENT 'Esse será o id da Agencia da conta que está recebendo a transferencia',
  Conta_idConta INT NOT NULL,
  Conta_idCliente INT NOT NULL,
  Conta_idAgencia INT NOT NULL,
  PRIMARY KEY (idTransferencia),
  INDEX fk_Transferencia_Conta1_idx (Conta_idConta ASC, Conta_idCliente ASC, Conta_idAgencia ASC) VISIBLE,
  CONSTRAINT fk_Transferencia_Conta1
    FOREIGN KEY (Conta_idConta , Conta_idCliente , Conta_idAgencia)
    REFERENCES Conta (idConta , Cliente_idCliente , Agencia_idAgencia)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


 