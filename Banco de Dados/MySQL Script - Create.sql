Create table gamer (
idGamer int primary key AUTO_INCREMENT,
nome varchar (60) not null,
usuario varchar (60) not null,
cpf char (30) not null,
email varchar (60) not null,
senha varchar (20) not null,
telefone char (11),
foto varchar (100)
);

-- Create table endereco (
-- idEndereco int primary key AUTO_INCREMENT,
-- logradouro varchar (60) not null,
-- numero int not null,
-- complemento varchar (20),
-- bairro varchar (30) not null,
-- cidade varchar (50) not null,
-- uf char (2) not null,
-- cep int not null
-- );

Create table posicao (
idPosicao int primary key AUTO_INCREMENT,
posicao varchar (20)
);

Create table jogo (
idJogo int primary key AUTO_INCREMENT,
nomeJogo varchar (100),		
qtdePlayers int
);

Create table equipe (
idEquipe int primary key AUTO_INCREMENT,
nomeEquipe varchar (20) not null
);

Create table statusSolicitacao (
idStatus int primary key AUTO_INCREMENT,
status varchar(10)
);

Create table infracao (
idInfracao int primary key AUTO_INCREMENT,
tipoInfracao varchar (150)
);

-- Create table enderecoGamer (
-- idEnderecoGamer int AUTO_INCREMENT,
-- idGamer int references gamer (idGamer),
-- idEndereco int references endereco (idEndereco)
-- );

Create table gamerinfo (
idGamerinfo int primary key AUTO_INCREMENT,
idGamer int references gamer (idGamer),
idJogo int references jogo (idJogo),
idPosicao int references posicao (idPosicao)
);

Create table equipeJogo (
idEquipeJogo int primary key AUTO_INCREMENT,
idEquipe int references equipe (idEquipe),
idJogo int references jogo (idJogo)
);

Create table equipeGamer (
idEquipeGamer int primary key AUTO_INCREMENT,
idEquipe int references equipe (idEquipe),
idGamer int references gamer (idGamer),
idStatus int references statusSolicitacao (idStatus)
);

create table partida (
IdPK int primary key AUTO_INCREMENT,
idPartida int not null,
idJogo int references jogo (idJogo),
idEquipe int references equipe (idEquipe),
idGamer int references gamer (idGamer),
idPosicao int references posicao (idPosicao),
idInfracao int references infracao (idInfracao),
data DATE,
hora TIME,
winner boolean null
);