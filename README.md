manageuser API
Esta é uma API de gerenciamento de usuário prevendo todas operações CRUD e algumas operações com requisitos especiais. Ela esta sob a segurança de autenticação e autorização via JWT. Todo o Projeto foi desenvolvido nas ultimas versões de suas bibliotecas e frameworks como também na ultima versão LTS do Java 11.0.5 e todo ecossistema do SpringBoot versão 2.2.1. Para rodar o projeto basta acessar a pasta raiz do projeto através de um terminal e executar o comando "mvn spring-boot:run", tudo o que a aplicação precisa para funcionar é carregado em memoria inclusive a base de dados atraves do banco H2. Para apenas testar o build do projeto basta na mesma pasta raiz executar o comando "mvn clean install", este comando irá compilar o projeto e rodar todos os testes. Por se tratar de uma API REST foi utilizado a ferramenta Swagger que trabalha muito bem na criação da documentação de toda a API, para acessar esta documentação basta acessar a url a seguir : http://localhost:8080/manageuser/v1/swagger-ui.html. O contexto da aplicação onde os serviços estão expostos é o "/manageuser/v1". Para começar a testar os serviços primeiro o usuário tem que se autenticar na url : http://localhost:8080/manageuser/v1/auth com login e senha e depois utilizar o Bearer token nas requisições. Abaixo segue os dados que a base é carregada e exemplos dos serviços (Todas as duvidas podem ser possivelmentada sanadas acessando a documentação completa pelo swagger):
INSERT INTO cargo (id, nome) VALUES
  (1, 'Gerente'),
  (2, 'Supervisor'),
  (3, 'Administrativo'),
  (4, 'Jovem Aprendiz');

INSERT INTO perfil (id, nome, role) VALUES
  (1, 'Administrador', 'ROLE_ADMINISTRADOR'),
  (2, 'Comum', 'ROLE_COMUM');

--SENHA 123456  para todos os usuários
INSERT INTO usuario (id, nome, cpf, sexo, data_nascimento, ativo, login, senha, id_cargo, id_perfil) VALUES
  (1,'Anderson Dias', '82667204018', 'MASCULINO', PARSEDATETIME('05/06/1982','dd/MM/yyyy'), TRUE,'anderson.dias','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',1,1),
  (2,'Luciana Neri', '82196866008', 'FEMININO', PARSEDATETIME('19/01/1984','dd/MM/yyyy'), TRUE,'luciana.neri','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',2,2),
  (3,'Nubia Neri', '81087107075', 'FEMININO', PARSEDATETIME('29/05/1990','dd/MM/yyyy'), TRUE,'nubia.neri','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',2,2),
  (4,'Maria Clara', '03771686024', 'FEMININO', PARSEDATETIME('27/10/2005','dd/MM/yyyy'), TRUE,'maria.clara','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',4,2),
  (5,'Greycy Kelly', '58405084037', 'OUTROS', PARSEDATETIME('10/05/1988','dd/MM/yyyy'), TRUE,'greycy.kelly','$2a$10$VTJ1FzDtAZdzg.BZdAF.kO8v582hADy3ijaAVGu0haK5GO2mxQhL2',3,2);
  
put    localhost:8080/manageuser/v1/usuarios/inativar/2
get    localhost:8080/manageuser/v1/usuarios/feminino/maior
get    localhost:8080/manageuser/v1/usuarios/cpf/0
post   localhost:8080/manageuser/v1/cargos
post   localhost:8080/manageuser/v1/perfis
post   localhost:8080/manageuser/v1/auth
post   localhost:8080/manageuser/v1/usuarios
get    localhost:8080/manageuser/v1/usuarios
put    localhost:8080/manageuser/v1/usuarios/
delete localhost:8080/manageuser/v1/usuarios/2
get    localhost:8080/manageuser/v1/usuarios/3
get    localhost:8080/manageuser/v1/usuarios/cpf/81087107075
get    localhost:8080/manageuser/v1/usuarios/perfil/2
get    localhost:8080/manageuser/v1/usuarios/cargo/2
get    localhost:8080/manageuser/v1/usuarios/status/true
get    localhost:8080/manageuser/v1/usuarios/nome/Anderson
