DROP DATABASE IF EXISTS banco ;
CREATE DATABASE IF NOT EXISTS banco;

USE banco;

DROP TABLE IF EXISTS Cliente ;

CREATE TABLE IF NOT EXISTS Cliente (
  idCliente INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(45) NOT NULL,
  bairro VARCHAR(45) NOT NULL,
  rua VARCHAR(45) NOT NULL,
  contato VARCHAR(45) NOT NULL,
  email VARCHAR(45) NULL,
  PRIMARY KEY (idCliente)
  );

DROP TABLE IF EXISTS Agencia ;

CREATE TABLE IF NOT EXISTS Agencia (
  idAgencia INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  PRIMARY KEY (idAgencia)
  );

DROP TABLE IF EXISTS Conta ;

CREATE TABLE IF NOT EXISTS Conta (
  idConta INT NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  limite DOUBLE NOT NULL,
  taxa DOUBLE NOT NULL,
  saldo DOUBLE NOT NULL,
  idCliente INT NOT NULL,
  idAgencia INT NOT NULL,
  PRIMARY KEY (idConta, idCliente, idAgencia),
    FOREIGN KEY (idCliente)
    REFERENCES Cliente (idCliente),
    FOREIGN KEY (idAgencia)
    REFERENCES Agencia (idAgencia)
    );


DROP TABLE IF EXISTS Funcionario ;

CREATE TABLE IF NOT EXISTS Funcionario (
  idFuncionario INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  senha VARCHAR(45) NOT NULL,
  tipo VARCHAR(45) NOT NULL,
  PRIMARY KEY (idFuncionario)
  );

DROP TABLE IF EXISTS Saque ;

CREATE TABLE IF NOT EXISTS Saque (
  idSaque INT NOT NULL,
  valor DOUBLE NOT NULL,
  dataSaque DATE NOT NULL,
  Conta_idConta INT NOT NULL,
  Conta_idCliente INT NOT NULL,
  Conta_idAgencia INT NOT NULL,
  PRIMARY KEY (idSaque, Conta_idConta, Conta_idCliente, Conta_idAgencia),
    FOREIGN KEY (Conta_idConta , Conta_idCliente , Conta_idAgencia)
    REFERENCES Conta (idConta , idCliente , idAgencia)
    );

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
  Funcionario_idFuncionario INT NOT NULL,
  PRIMARY KEY (idDeposito, Conta_idConta, Conta_idCliente, Conta_idAgencia, Funcionario_idFuncionario),
    FOREIGN KEY (Conta_idConta , Conta_idCliente , Conta_idAgencia)
    REFERENCES Conta (idConta , idCliente , idAgencia),
    FOREIGN KEY (Funcionario_idFuncionario)
    REFERENCES Funcionario (idFuncionario)
    );

DROP TABLE IF EXISTS Transferencia ;

CREATE TABLE IF NOT EXISTS Transferencia (
  idTransferencia INT NOT NULL,
  data DATE NOT NULL,
  valor DOUBLE NOT NULL,
  idConta VARCHAR(45) NOT NULL COMMENT 'Esse será a id da conta que vai receber o dinheiro',
  idCliente INT NOT NULL COMMENT 'Esse é o id do Cliente que vai receber a transferência',
  idAgencia INT NOT NULL COMMENT 'Esse será o id da Agencia da conta que está recebendo a transferencia',
  Conta_idConta INT NOT NULL,
  Conta_idCliente INT NOT NULL,
  Conta_idAgencia INT NOT NULL,
  PRIMARY KEY (idTransferencia, Conta_idConta, Conta_idCliente, Conta_idAgencia),
    FOREIGN KEY (Conta_idConta , Conta_idCliente , Conta_idAgencia)
    REFERENCES Conta (idConta , idCliente , idAgencia)
    );