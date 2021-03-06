DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS cargo;
DROP TABLE IF EXISTS perfil;

CREATE TABLE cargo (
  id BIGINT(20) AUTO_INCREMENT  PRIMARY KEY,
  nome VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE perfil (
  id BIGINT(20) AUTO_INCREMENT  PRIMARY KEY,
  nome VARCHAR(250) NOT NULL,
  role VARCHAR(250) NOT NULL UNIQUE
);
 
CREATE TABLE usuario (
  id BIGINT(20) AUTO_INCREMENT  PRIMARY KEY,
  nome VARCHAR(250) NOT NULL UNIQUE,
  cpf VARCHAR(11) NOT NULL UNIQUE,
  sexo ENUM('MASCULINO','FEMININO','OUTROS') NOT NULL,
  data_nascimento DATE NOT NULL,
  ativo BOOLEAN NOT NULL,
  login VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(250) NOT NULL,
  id_cargo BIGINT(20) NOT NULL,
  id_perfil BIGINT(20) NOT NULL,
  FOREIGN KEY (id_cargo) REFERENCES cargo(id),
  FOREIGN KEY (id_perfil) REFERENCES perfil(id)
);
  
  