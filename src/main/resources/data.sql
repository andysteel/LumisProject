
INSERT INTO cargo (id, nome) VALUES
  (1, 'Gerente'),
  (2, 'Supervisor'),
  (3, 'Administrativo'),
  (4, 'Jovem Aprendiz');

INSERT INTO perfil (id, nome, role) VALUES
  (1, 'Administrador', 'ROLE_ADMINISTRADOR'),
  (2, 'Comum', 'ROLE_COMUM');

--SENHA 123456  
INSERT INTO usuario (id, nome, cpf, sexo, data_nascimento, ativo, login, senha, id_cargo, id_perfil) VALUES
  (1,'Anderson Dias', '82667204018', 'MASCULINO', PARSEDATETIME('05/06/1982','dd/MM/yyyy'), TRUE,'anderson.dias','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',1,1),
  (2,'Luciana Neri', '82196866008', 'FEMININO', PARSEDATETIME('19/01/1984','dd/MM/yyyy'), TRUE,'luciana.neri','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',2,2),
  (3,'Nubia Neri', '81087107075', 'FEMININO', PARSEDATETIME('29/05/1990','dd/MM/yyyy'), TRUE,'nubia.neri','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',2,2),
  (4,'Maria Clara', '03771686024', 'FEMININO', PARSEDATETIME('27/10/2005','dd/MM/yyyy'), TRUE,'maria.clara','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',4,2),
  (5,'Greycy Kelly', '58405084037', 'OUTROS', PARSEDATETIME('10/05/1988','dd/MM/yyyy'), TRUE,'greycy.kelly','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',3,2);
  
  