--USE master;
--DROP DATABASE GooDooIt;

CREATE
DATABASE GooDooIt01;
USE
GooDooIt01;

--

CREATE TABLE Status
(

    ID        INT          NOT NULL IDENTITY(1,1),
    titulo    VARCHAR(100) NOT NULL,
    descricao VARCHAR(250) NOT NULL
    PRIMARY KEY (ID)

);

CREATE TABLE Usuario
(

    ID        INT          NOT NULL IDENTITY (10000, 1),
    nome      VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    login     VARCHAR(100) NOT NULL UNIQUE,
    senha     VARCHAR(50)  NOT NULL,
    email     VARCHAR(50)  NOT NULL
    PRIMARY KEY (ID)

);

CREATE TABLE Projeto
(

    ID          INT          NOT NULL IDENTITY (2025100, 1),
    nome        VARCHAR(100) NOT NULL,
    descricao   VARCHAR(250) NULL,
    data_inicio DATE         NOT NULL,
    data_fim    DATE         NOT NULL,
    dataCriacao DATE         NOT NULL,
    LiderID     INT          NOT NULL,
    StatusID    INT          NOT NULL
    PRIMARY KEY (ID)
    FOREIGN KEY (StatusID)  REFERENCES Status (ID),
    FOREIGN KEY (LiderID) REFERENCES Usuario (ID),


);


CREATE TABLE Usuario_Projeto
(

    UsuarioID INT,
    ProjetoID INT
    PRIMARY KEY (UsuarioID, ProjetoID)
    FOREIGN KEY (UsuarioID) REFERENCES Usuario (ID),
    FOREIGN KEY (ProjetoID) REFERENCES Projeto (ID)

);

CREATE TABLE Convite
(

    DestinatarioID INT NOT NULL,
    RemetenteID    INT NOT NULL,
    ProjetoID      INT NOT NULL,
    CONSTRAINT chk_remetente_destinario CHECK (RemetenteID <> DestinatarioID),

    PRIMARY KEY (DestinatarioID, RemetenteID, ProjetoID),
    FOREIGN KEY (DestinatarioID) REFERENCES Usuario (ID),
    FOREIGN KEY (RemetenteID) REFERENCES Usuario (ID),
    FOREIGN KEY (ProjetoID) REFERENCES Projeto (ID)

);

CREATE TABLE Tarefa
(

    ID            INT          NOT NULL IDENTITY (10000, 1),
    nome          VARCHAR(100) NOT NULL,
    descricao     VARCHAR(205) NULL,
    data_inicio   DATE         NOT NULL,
    data_fim      DATE         NOT NULL,
    dataCriacao   DATE         NOT NULL,
    prioridade    INT          NOT NULL,
    CriadorID     INT          NOT NULL,
    ResponsavelID INT NULL,
    StatusID      INT          NOT NULL,
    ProjetoID     INT          NOT NULL
    PRIMARY KEY (ID)
    FOREIGN KEY (CriadorID)        REFERENCES Usuario (ID),
    FOREIGN KEY (ResponsavelID) REFERENCES Usuario (ID),
    FOREIGN KEY (ProjetoID) REFERENCES Projeto (ID),
    FOREIGN KEY (StatusID) REFERENCES Status (ID)

);

INSERT INTO Usuario (nome, sobrenome, login, senha, email)
VALUES ('Ana', 'Costa', 'ana', '123456', 'ana@exemplo.com'),
       ('Bruno', 'Lima', 'bruno', 'senha123', 'bruno@exemplo.com'),
       ('Carla', 'Mendes', 'carla', 'abc123', 'carla@exemplo.com'),
       ('Daniel', 'Souza', 'daniel', '456789', 'daniel@exemplo.com'),
       ('Eduardo', 'Silva', 'eduardo', 'senha456', 'eduardo@exemplo.com'),
       ('Fernanda', 'Rocha', 'fernanda', 'pass123', 'fernanda@exemplo.com'),
       ('Gabriel', 'Almeida', 'gabriel', 'senha789', 'gabriel@exemplo.com'),
       ('Helena', 'Torres', 'helena', 'hel123', 'helena@exemplo.com'),
       ('Igor', 'Nunes', 'igor', 'pass456', 'igor@exemplo.com'),
       ('Juliana', 'Faria', 'juliana', 'jul123', 'juliana@exemplo.com');


INSERT INTO Status (titulo, descricao)
VALUES ('Em Andamento', 'Está em progresso'),
       ('Concluído', 'Finalizado com sucesso'),
       ('Cancelado', 'Interrompido');


INSERT INTO Projeto (nome, descricao, data_inicio, data_fim, dataCriacao, StatusID, LiderID)
VALUES ('Sistema de Tarefas', 'Controle de tarefas e quadros', '2025-01-01', '2025-12-31', GETDATE(), 1, 10000),
       ('App Financeiro', 'Aplicativo para gestão financeira', '2025-02-01', '2025-11-30', GETDATE(), 1, 10001),
       ('Loja Virtual', 'Plataforma de e-commerce', '2025-03-01', '2025-12-15', GETDATE(), 2, 10002),
       ('Sistema Escolar', 'Gerenciamento acadêmico', '2025-01-15', '2025-10-20', GETDATE(), 1, 10003),
       ('Chat Online', 'Ferramenta de comunicação', '2025-04-01', '2025-09-30', GETDATE(), 3, 10004),
       ('Blog Pessoal', 'Plataforma de blogs', '2025-05-01', '2025-08-31', GETDATE(), 1, 10005),
       ('Rede Social', 'Projeto de rede social', '2025-06-01', '2025-12-01', GETDATE(), 2, 10006),
       ('Sistema RH', 'Recursos Humanos', '2025-03-01', '2025-11-01', GETDATE(), 1, 10007),
       ('Agenda Médica', 'Sistema para clínicas', '2025-02-15', '2025-10-15', GETDATE(), 3, 10008),
       ('Controle de Estoque', 'Gestão de produtos', '2025-01-10', '2025-09-10', GETDATE(), 1, 10009);


INSERT INTO Usuario_Projeto (UsuarioID, ProjetoID)
VALUES (10000, 2025100),
       (10001, 2025100),
       (10002, 2025101),
       (10003, 2025101),
       (10004, 2025102),
       (10005, 2025102),
       (10006, 2025103),
       (10007, 2025103),
       (10008, 2025104),
       (10009, 2025104);


INSERT INTO Tarefa (nome, descricao, data_inicio, data_fim, dataCriacao, prioridade, StatusID, CriadorID, ResponsavelID,
                    ProjetoID)
VALUES ('Criar Banco', 'Definir estrutura SQL', '2025-01-01', '2025-01-05', GETDATE(), 1, 1, 10000, 10001, 2025101),
       ('Desenvolver API', 'Construir backend', '2025-01-06', '2025-01-15', GETDATE(), 2, 1, 10000, 10001, 2025101),
       ('Layout Front', 'Criar protótipos', '2025-01-10', '2025-01-20', GETDATE(), 3, 1, 10001, 10000, 2025101),
       ('Documentação', 'Manual do sistema', '2025-01-15', '2025-01-25', GETDATE(), 2, 1, 10001, 10000, 2025101),
       ('Testes Unitários', 'Cobertura de código', '2025-01-20', '2025-01-30', GETDATE(), 3, 1, 10000, 10001, 2025102),
       ('Deploy', 'Publicar sistema', '2025-01-30', '2025-02-05', GETDATE(), 1, 1, 10000, 10001, 2025101),
       ('Integração Contínua', 'Configurar CI/CD', '2025-02-01', '2025-02-10', GETDATE(), 2, 1, 10000, 10001, 2025102),
       ('Revisar Código', 'Analisar Pull Requests', '2025-02-10', '2025-02-15', GETDATE(), 1, 1, 10001, 10000, 2025101),
       ('Criar Login', 'Autenticação de usuários', '2025-02-15', '2025-02-20', GETDATE(), 1, 1, 10000, 10001, 2025101),
       ('Perfil Usuário', 'Editar dados pessoais', '2025-02-20', '2025-02-25', GETDATE(), 2, 1, 10000, 10001, 2025101);


INSERT INTO Convite (RemetenteID, ProjetoID, DestinatarioID)
VALUES (10002, 2025100, 10001),
       (10001, 2025101, 10002),
       (10009, 2025102, 10003),
       (10002, 2025103, 10004),
       (10006, 2025104, 10005),
       (10001, 2025105, 10006),
       (10003, 2025106, 10007),
       (10009, 2025107, 10008),
       (10000, 2025108, 10009),
       (10006, 2025109, 10000);


SELECT *
FROM Status

SELECT *
FROM Usuario

SELECT *
FROM Projeto

SELECT *
FROM Usuario_Projeto

SELECT *
FROM Usuario_Notificacao

SELECT *
FROM Tarefa

SELECT *
FROM Convite


